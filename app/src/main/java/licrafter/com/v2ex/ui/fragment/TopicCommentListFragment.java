package licrafter.com.v2ex.ui.fragment;/**
 * Created by Administrator on 2016/3/28.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

import butterknife.Bind;
import licrafter.com.v2ex.R;
import licrafter.com.v2ex.base.BaseFragment;
import licrafter.com.v2ex.event.CommentEvent;
import licrafter.com.v2ex.listener.OnScrollBottomListener;
import licrafter.com.v2ex.model.TabContent;
import licrafter.com.v2ex.model.TopicComment;
import licrafter.com.v2ex.mvp.presenters.TopicDetailPresenter;
import licrafter.com.v2ex.mvp.views.MvpView;
import licrafter.com.v2ex.ui.activity.TopicDetailActivity;
import licrafter.com.v2ex.ui.adapter.CommonRecyclerAdapter;
import licrafter.com.v2ex.ui.widget.RichTextView;
import licrafter.com.v2ex.util.CustomUtil;
import licrafter.com.v2ex.util.RxBus;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;

/**
 * author: lijinxiang
 * date: 2016/3/28
 **/
public class TopicCommentListFragment extends BaseFragment implements MvpView {

    @Bind(R.id.commentRecyclerView)
    RecyclerView mCommentRecyclerView;
    @Bind(R.id.swipe_layout)
    SwipeRefreshLayout mRefreshLayout;

    private String mTopicTitle = "";
    private String mTopicId = "";
    private CommentAdapter mAdapter;
    private TopicDetailPresenter mPresenter;
    private Subscription commentScription;

    private int pageIndex = 1;
    private int totalPage;

    public static TopicCommentListFragment getInstance(String topicTitle, String topicId) {
        TopicCommentListFragment fragment = new TopicCommentListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("topicTitle", topicTitle);
        bundle.putString("topicId", topicId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTopicTitle = getArguments().getString("topicTitle");
            mTopicId = getArguments().getString("topicId");
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_topic_comment_list;
    }

    @Override
    protected void attachView() {
        mPresenter = new TopicDetailPresenter();
        mPresenter.attachView(this);
    }

    @Override
    protected void initViews(View view) {
        ((TopicDetailActivity) getActivity()).setAppBarShadow(true);
        ((TopicDetailActivity) getActivity()).getSupportActionBar().setTitle(mTopicTitle);

        CustomUtil.initStyle(mRefreshLayout);
        mAdapter = new CommentAdapter(getContext(), R.layout.item_comment);
        mCommentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mCommentRecyclerView.setHasFixedSize(true);
        mCommentRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void setListeners() {
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageIndex = 1;
                mPresenter.getCommentsList(mTopicId, pageIndex, true);
            }
        });
        mCommentRecyclerView.addOnScrollListener(new OnScrollBottomListener() {
            @Override
            public void onBottom() {
                super.onBottom();
                if (pageIndex <= totalPage) {
                    mPresenter.getCommentsList(mTopicId, pageIndex, false);
                }
            }
        });
        commentScription = RxBus.getDefault().toObserverable(CommentEvent.class)
                .subscribe(new Action1<CommentEvent>() {
                    @Override
                    public void call(CommentEvent commentEvent) {

                    }
                });
    }

    @Override
    protected void loadData() {
        mRefreshLayout.setRefreshing(true);
        mPresenter.getCommentsList(mTopicId, 1, true);
    }

    @Override
    protected void detachView() {
        mPresenter.detachView();
    }

    public void parseComments(TopicComment topicComment) {
        mRefreshLayout.setRefreshing(false);
        loadMoreUIHandler(topicComment);
        mAdapter.setData(topicComment.getComments());
    }

    @Override
    public void onFailure(String e) {
        if (mRefreshLayout.isRefreshing()) {
            mRefreshLayout.setRefreshing(false);
        }
        mAdapter.setErrorInfo(e);
    }

    public void onLoadMoreSuccess(TopicComment topicComment) {
        loadMoreUIHandler(topicComment);
        mAdapter.addData(topicComment.getComments());
    }

    private void loadMoreUIHandler(TopicComment topicComment) {
        if (mRefreshLayout.isRefreshing()) {
            mRefreshLayout.setRefreshing(false);
        }
        pageIndex = topicComment.getPage() + 1;
        totalPage = topicComment.getTotalPage();
        if (pageIndex > totalPage) {
            mAdapter.hasNextPage(false);
        } else {
            mAdapter.hasNextPage(true);
        }
    }

    class CommentAdapter extends CommonRecyclerAdapter<TopicComment.Comment> {

        protected CommentAdapter(Context context, int itemLayoutId) {
            super(context, itemLayoutId);
        }

        @Override
        protected void getItemViewHolder(ItemViewHolder viewHolder) {

        }

        @Override
        protected void bindData(ItemViewHolder viewHolder, int position) {
            TopicComment.Comment comment = mDatas.get(position);
            Glide.with(mContext).load(comment.getAvatar()).into((RoundedImageView) viewHolder.getView(R.id.iv_avatar));
            viewHolder.getTextView(R.id.tv_username).setText(comment.getUserName());
            viewHolder.getTextView(R.id.tv_create_time).setText(comment.getCreateTime());
            ((RichTextView) viewHolder.getView(R.id.tv_content)).setRichText(comment.getContent());
            viewHolder.getTextView(R.id.tv_rank).setText(comment.getRank());

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (!commentScription.isUnsubscribed()){
            commentScription.unsubscribe();
        }
    }
}
