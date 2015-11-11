package licrafter.com.v2ex.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import licrafter.com.v2ex.R;
import licrafter.com.v2ex.adapter.AnimationRecyclerViewAdapter.AnimationViewHolder;
import licrafter.com.v2ex.adapter.TabContentAdapter;
import licrafter.com.v2ex.api.Server;
import licrafter.com.v2ex.db.TabContentDao;
import licrafter.com.v2ex.model.TabContent;
import licrafter.com.v2ex.model.Topic;
import licrafter.com.v2ex.util.Constant;
import licrafter.com.v2ex.util.CustomUtil;
import licrafter.com.v2ex.util.JsoupUtil;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by lijinxiang on 11/5/15.
 */
public class TabFragment extends BaseFragment implements TabContentAdapter.OnLoadmoreListener, SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.rv_content)
    RecyclerView mListView;
    @Bind(R.id.swipe_layout)
    SwipeRefreshLayout mSwipeLayout;

    private TabContentAdapter mAdapter;
    private String title;
    private TabContent mData;
    private Handler mHandler = new Handler();
    private boolean isCached;                   //是否已经缓存到数据库

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_topic, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mListView.setHasFixedSize(false);
        mSwipeLayout.setOnRefreshListener(this);
        init();
        if (isCached) {
            getTopicsFormCache();
        } else {
            getTabTopics(Constant.NETWORK.FIRST_LOADING, 1);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(loadRunnable);
    }

    private void init() {
        Bundle bundle = getArguments();
        if (bundle.containsKey(Constant.EXTRA.TAB_TITLE)) {
            title = bundle.getString(Constant.EXTRA.TAB_TITLE);
        }
        isCached = new TabContentDao(getActivity()).isTabExists(title);
    }

    private void getTabTopics(final String action, final int page) {
        Callback<Response> callback = new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                try {
                    String body = CustomUtil.streamFormToString(response.getBody().in());
                    mData = JsoupUtil.parse(title, body);
                    if (action.equals(Constant.NETWORK.FIRST_LOADING)) {
                        setAdapter();
                        cacheTab(mData);
                    } else if (action.equals(Constant.NETWORK.LOAD_MORE)) {
                        mAdapter.addAll(mData.getTopics());
                        mAdapter.setLoaded();
                    } else if (action.equals(Constant.NETWORK.REFUSE)) {
                        mAdapter.resetData(mData.getTopics());
                        mSwipeLayout.setRefreshing(false);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                android.util.Log.d("ljx", error.toString());
            }
        };
        if (!title.equals("recent")) {
            Server.v2EX(getActivity()).getTabTopics(title, callback);
        } else {
            Server.v2EX(getActivity()).getRecentTopics(title, page, callback);
        }
    }

    private void setAdapter() {
        mListView.setAdapter(mAdapter = new TabContentAdapter(getActivity(), mListView, mData.getTopics(), R.layout.item_topic_card) {
            @Override
            public void convert(AnimationViewHolder holder, Topic item) {
                RoundedImageView iv_avatar = holder.getView(R.id.iv_avatar);
                TextView topicTitle = holder.getView(R.id.tv_title);
                TextView node = holder.getView(R.id.tv_node);
                TextView author = holder.getView(R.id.tv_author);
                TextView createTime = holder.getView(R.id.tv_create_time);
                TextView replies = holder.getView(R.id.tv_replies);
                topicTitle.setText(item.getTitle());
                node.setText(item.getNodeName());
                author.setText(item.getUserId());
                createTime.setText(item.getCreateTime());
                replies.setText(item.getReplies() + "");
                Picasso.with(getActivity()).load(item.getAvatar()).into(iv_avatar);
            }
        });
        mAdapter.setOnLoadmoreListener(this);
    }

    Runnable loadRunnable = new Runnable() {
        @Override
        public void run() {
            //为了展示出来正在加载的效果，才这么做....
            //因为tab数据只有一页
            mAdapter.removeLastData();
            Toast.makeText(getActivity(), "已经加载到最后一条", Toast.LENGTH_SHORT).show();
            mAdapter.setLoaded();
        }
    };

    @Override
    public void onLoadMore() {
        mAdapter.addData(null);
        if (mData.getTotalPages() <= 1 || mData.getPage() >= mData.getTotalPages()) {
            mHandler.postDelayed(loadRunnable, 2000);
        } else {
            getTabTopics(Constant.NETWORK.LOAD_MORE, mData.getPage() + 1);
        }
    }

    /**
     * 从数据库读取缓存
     */
    private void getTopicsFormCache() {
        mData = new TabContentDao(getActivity()).getTabContent(title);
        setAdapter();
    }

    /**
     * 保存到数据库
     *
     * @param content
     */
    private void cacheTab(TabContent content) {
        new TabContentDao(getActivity()).addTabContent(content);
    }

    @Override
    public void onRefresh() {
        getTabTopics(Constant.NETWORK.REFUSE, 1);
    }
}
