package licrafter.com.v2ex.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import licrafter.com.v2ex.R;
import licrafter.com.v2ex.adapter.AnimationRecyclerViewAdapter.AnimationViewHolder;
import licrafter.com.v2ex.adapter.CommonAdapter;
import licrafter.com.v2ex.api.Server;
import licrafter.com.v2ex.model.TableContent;
import licrafter.com.v2ex.util.Constant;
import licrafter.com.v2ex.util.CustomUtil;
import licrafter.com.v2ex.util.JsoupUtil;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by lijinxiang on 11/5/15.
 */
public class TabFragment extends BaseFragment implements CommonAdapter.OnLoadmoreListener {

    @Bind(R.id.rv_content)
    RecyclerView mListView;
    @Bind(R.id.swipe_layout)
    SwipeRefreshLayout mSwipeLayout;

    private CommonAdapter mAdapter;
    private String title;
    private TableContent mData;
    private Handler mHandler = new Handler();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_topic, container, false);
        ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        if (bundle.containsKey(Constant.EXTRA.TAB_TITLE)) {
            title = bundle.getString(Constant.EXTRA.TAB_TITLE);
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mListView.setHasFixedSize(false);
        //getTabTopics();
        setAdapter();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(loadRunnable);
    }

    private void getTabTopics() {
        Callback<Response> callback = new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                try {
                    String body = CustomUtil.streamFormToString(response.getBody().in());
                    mData = JsoupUtil.parse(body);
                    String str = "";
                    for (TableContent.Topic topic : mData.getTopics()) {
                        android.util.Log.d("ljx", topic.avatar);
                        str += topic.avatar + "*" + topic.createTime + "*" + topic.lastedReviewer
                                + "*" + topic.nodeId + "*" + topic.nodeName + "*" + topic.replies
                                + "*" + topic.title + "*" + topic.topicId + "*" + topic.userId + "\n\n";
                    }
                    setAdapter();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                android.util.Log.d("ljx", error.toString());
            }
        };
        Server.v2EX(getActivity()).getTabTopics(title, callback);
    }

    private void setAdapter() {
        List<TableContent.Topic> list = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            list.add(new TableContent.Topic());
        }
        list.add(null);
        mData = new TableContent();
        mData.setTopics(list);
        mListView.setAdapter(mAdapter = new CommonAdapter<TableContent.Topic>(getActivity(), mListView, mData.getTopics(), R.layout.item_topic_card) {
            @Override
            public void convert(AnimationViewHolder holder, TableContent.Topic item) {
                RoundedImageView iv_avatar = holder.getView(R.id.iv_avatar);
                Picasso.with(getActivity()).load("http://h.hiphotos.baidu.com/image/pic/item/4ec2d5628535e5ddf0a6ffde74c6a7efce1b622c.jpg").into(iv_avatar);
            }
        });
        mAdapter.setOnLoadmoreListener(this);
    }

    Runnable loadRunnable = new Runnable() {
        @Override
        public void run() {
            //为了展示出来正在加载的效果，才这么做....
            //因为tab数据只有一页
            mData.getTopics().remove(mData.getTopics().size() - 1);
            mAdapter.notifyItemRemoved(mData.getTopics().size());
            Toast.makeText(getActivity(), "已经加载到最后一条", Toast.LENGTH_SHORT).show();
            mAdapter.setLoaded();
        }
    };

    @Override
    public void onLoadMore() {
        mHandler.postDelayed(loadRunnable, 2000);
    }
}
