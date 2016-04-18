package licrafter.com.v2ex.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bumptech.glide.Glide;

import butterknife.Bind;
import licrafter.com.v2ex.R;
import licrafter.com.v2ex.base.BaseFragment;
import licrafter.com.v2ex.listener.OnScrollBottomListener;
import licrafter.com.v2ex.model.TabContent;
import licrafter.com.v2ex.model.Topic;
import licrafter.com.v2ex.mvp.presenters.TopicListPresenter;
import licrafter.com.v2ex.mvp.views.TopicListView;
import licrafter.com.v2ex.ui.activity.TopicDetailActivity;
import licrafter.com.v2ex.ui.adapter.CommonRecyclerAdapter;
import licrafter.com.v2ex.util.CustomUtil;

/**
 * author: lijinxiang
 * date: 2016/3/18
 **/
public class TopicListFragment extends BaseFragment implements TopicListView {

    @Bind(R.id.rv_content)
    RecyclerView mListView;
    @Bind(R.id.swipe_layout)
    SwipeRefreshLayout mSwipeLayout;

    private String mTabTitle;
    private TopicListPresenter mPresenter;
    private TopicListAdapter mAdapter;

    private int mPageIndex = 1;
    private int mTotalPage;
    private TabContent mTabContent;

    public static TopicListFragment getInstance(String tabTitle) {
        Bundle bundle = new Bundle();
        if (tabTitle != null) {
            bundle.putString("mTabTitle", tabTitle);
        }
        TopicListFragment fragment = new TopicListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_topic_list;
    }

    @Override
    protected void attachView() {
        mPresenter = new TopicListPresenter();
        mPresenter.attachView(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey("mTabTitle")) {
            mTabTitle = getArguments().getString("mTabTitle");
        }
    }

    @Override
    protected void initViews(View view) {
        mAdapter = new TopicListAdapter(getContext(), R.layout.item_topic_card);
        CustomUtil.initStyle(mSwipeLayout);
        mListView.setLayoutManager(new LinearLayoutManager(getContext()));
        mListView.setHasFixedSize(true);
        mListView.setAdapter(mAdapter);
    }

    @Override
    protected void setListeners() {
        mListView.addOnScrollListener(new OnScrollBottomListener() {
            @Override
            public void onBottom() {
                super.onBottom();
                if (mPageIndex <= mTotalPage) {
                    mPresenter.getTopicList(mTabTitle, mPageIndex, false);
                }
            }
        });
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPageIndex = 1;
                mPresenter.getTopicList(mTabTitle, mPageIndex, true);
            }
        });
    }

    @Override
    protected void loadData() {
        if (mTabContent == null) {
            mSwipeLayout.setRefreshing(true);
            mPresenter.getTopicList(mTabTitle, mPageIndex, true);
        } else {
            mAdapter.setData(mTabContent.getTopics());
            loadMoreUIHandler(mTabContent);
        }
    }

    @Override
    protected void detachView() {
        mPresenter.detachView();
    }

    @Override
    public void onGetTopicSuccess(TabContent content) {
        mTabContent = content;
        loadMoreUIHandler(content);
        mAdapter.setData(content.getTopics());
    }

    @Override
    public void onFailure(String e) {
        if (mSwipeLayout.isRefreshing()) {
            mSwipeLayout.setRefreshing(false);
        }
        mAdapter.setErrorInfo(e);
    }

    public void onLoadMoreSuccess(TabContent content) {
        mTabContent.setPage(content.getPage());
        mTabContent.getTopics().addAll(content.getTopics());
        loadMoreUIHandler(content);
        mAdapter.addData(content.getTopics());
    }

    private void loadMoreUIHandler(TabContent content) {
        if (mSwipeLayout.isRefreshing()) {
            mSwipeLayout.setRefreshing(false);
        }
        mPageIndex = content.getPage() + 1;
        mTotalPage = content.getTotalPages();
        if (mPageIndex > mTotalPage) {
            mAdapter.hasNextPage(false);
        } else {
            mAdapter.hasNextPage(true);
        }
    }

    public class TopicListAdapter extends CommonRecyclerAdapter<Topic> {

        protected TopicListAdapter(Context context, int itemLayoutId) {
            super(context, itemLayoutId);
        }

        @Override
        protected void getItemViewHolder(final CommonRecyclerAdapter.ItemViewHolder viewHolder) {
            viewHolder.getView(R.id.rootView).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), TopicDetailActivity.class);
                    intent.putExtra("topic", mDatas.get(viewHolder.getAdapterPosition()));
                    startActivity(intent);
                }
            });
        }

        @Override
        protected void bindData(CommonRecyclerAdapter.ItemViewHolder holder, int position) {
            Topic topic = mDatas.get(position);
            Glide.with(getContext()).load(topic.getAvatar()).into(holder.getImageView(R.id.iv_avatar));
            holder.getTextView(R.id.tv_title).setText(topic.getTitle());
            holder.getTextView(R.id.tv_node).setText(topic.getNodeName());
            holder.getTextView(R.id.tv_author).setText(topic.getUserId());
            if (topic.getCreateTime().equals("")) {
                holder.getTextView(R.id.tv_create_time).setVisibility(View.GONE);
            } else {
                holder.getTextView(R.id.tv_create_time).setVisibility(View.VISIBLE);
                holder.getTextView(R.id.tv_create_time).setText(topic.getCreateTime());
            }
            holder.getTextView(R.id.tv_replies).setText(String.valueOf(topic.getReplies()));
        }
    }
}
