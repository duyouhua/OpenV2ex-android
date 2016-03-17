package licrafter.com.v2ex.ui.fragment;

import android.content.Intent;
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
import licrafter.com.v2ex.model.Node;
import licrafter.com.v2ex.model.SeriableTopic;
import licrafter.com.v2ex.ui.activity.NodeActivity;
import licrafter.com.v2ex.ui.activity.TopicActivity;
import licrafter.com.v2ex.ui.adapter.AnimationRecyclerViewAdapter.AnimationViewHolder;
import licrafter.com.v2ex.ui.adapter.TabContentAdapter;
import licrafter.com.v2ex.api.Server;
import licrafter.com.v2ex.model.TabContent;
import licrafter.com.v2ex.model.Topic;
import licrafter.com.v2ex.util.Constant;
import licrafter.com.v2ex.util.CustomUtil;
import licrafter.com.v2ex.util.JsoupUtil;

/**
 * Created by lijinxiang on 11/5/15.
 */
public class TabFragment extends BaseFragment
        implements TabContentAdapter.OnLoadmoreListener, SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.rv_content)
    RecyclerView mListView;
    @Bind(R.id.swipe_layout)
    SwipeRefreshLayout mSwipeLayout;

    private TabContentAdapter mAdapter;
    private String title;
    private Node node;
    private TabContent mData;
    private Handler mHandler = new Handler();
    private boolean isCached;                   //是否已经缓存到数据库

    @Override
    public View onCreateView(LayoutInflater inflater
            , ViewGroup container, Bundle savedInstanceState) {
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
        mSwipeLayout.setProgressViewOffset(false, 0, 25);
        init();
        if (isCached && !isRequestNode()) {
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
        } else if (bundle.containsKey(Constant.EXTRA.NODE)) {
            //如果传进来node,则请求节点话题列表
            node = (Node) bundle.getSerializable(Constant.EXTRA.NODE);
        }
    }

    private void getTabTopics(final String action, final int page) {

    }

    private void setAdapter() {
        mListView.setAdapter(mAdapter = new TabContentAdapter(getActivity()
                , mListView, mData.getTopics(), R.layout.item_topic_card) {
            @Override
            public void convert(AnimationViewHolder holder, final Topic item) {
                RoundedImageView iv_avatar = holder.getView(R.id.iv_avatar);
                TextView topicTitle = holder.getView(R.id.tv_title);
                final TextView node = holder.getView(R.id.tv_node);
                TextView author = holder.getView(R.id.tv_author);
                TextView createTime = holder.getView(R.id.tv_create_time);
                TextView replies = holder.getView(R.id.tv_replies);
                if (item.isRead()) {
                    topicTitle.setTextColor(getResources().getColor(R.color.grey500));
                    replies.setBackground(getResources().getDrawable(R.drawable.bg_oval_grey));
                } else {
                    topicTitle.setTextColor(getResources().getColor(R.color.black));
                    replies.setBackground(getResources().getDrawable(R.drawable.bg_oval_blue));
                }
                //如果是请求的节点话题列表,节点框隐藏
                if (isRequestNode()) {
                    node.setVisibility(View.GONE);
                } else {
                    node.setVisibility(View.VISIBLE);
                }
                topicTitle.setText(item.getTitle());
                node.setText(item.getNodeName());
                author.setText(item.getUserId());
                createTime.setText(item.getCreateTime());
                replies.setText(item.getReplies() + "");
                Picasso.with(getActivity()).load(item.getAvatar()).into(iv_avatar);
                holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), TopicActivity.class);
                        SeriableTopic sTopic = new SeriableTopic(item);
                        intent.putExtra(Constant.EXTRA.TOPIC, sTopic);
                        startActivity(intent);
                        item.setRead(true);
                        notifyDataSetChanged();
                    }
                });
                node.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, NodeActivity.class);
                        Node node = new Node();
                        node.setName(item.getNodeId());
                        node.setTitle(item.getNodeName());
                        intent.putExtra(Constant.EXTRA.NODE, node);
                        mContext.startActivity(intent);
                    }
                });
            }
        });
        mAdapter.setOnLoadmoreListener(this);
    }

    Runnable loadRunnable = new Runnable() {
        @Override
        public void run() {
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
     * 是否为请求节点数据
     */
    private boolean isRequestNode() {
        return node != null;
    }

    @Override
    public void onRefresh() {
        getTabTopics(Constant.NETWORK.REFUSE, 1);
    }

}
