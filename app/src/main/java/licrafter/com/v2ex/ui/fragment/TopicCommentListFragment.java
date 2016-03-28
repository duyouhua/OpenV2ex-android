package licrafter.com.v2ex.ui.fragment;/**
 * Created by Administrator on 2016/3/28.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;

import butterknife.Bind;
import licrafter.com.v2ex.R;
import licrafter.com.v2ex.base.BaseFragment;
import licrafter.com.v2ex.model.TopicComment;
import licrafter.com.v2ex.mvp.presenters.TopicDetailPresenter;
import licrafter.com.v2ex.mvp.views.MvpView;
import licrafter.com.v2ex.ui.activity.TopicDetailActivity;
import licrafter.com.v2ex.ui.adapter.CommonRecyclerAdapter;

/**
 * author: lijinxiang
 * date: 2016/3/28
 **/
public class TopicCommentListFragment extends BaseFragment implements MvpView {

    @Bind(R.id.commentRecyclerView)
    RecyclerView mCommentRecyclerView;
    @Bind(R.id.commentInput)
    EditText mcommentInput;
    @Bind(R.id.sendBtn)
    ImageButton sendBtn;

    private String mTopicTitle = "";
    private String mTopicId = "";
    private CommentAdapter mAdapter;
    private TopicDetailPresenter mPresenter;

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

        mAdapter = new CommentAdapter(getContext(), R.layout.item_comment);
        mCommentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mCommentRecyclerView.setHasFixedSize(true);
        mCommentRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void setListeners() {

    }

    @Override
    protected void loadData() {
        mPresenter.getCommentsList(mTopicId, 1);
    }

    @Override
    protected void detachView() {
        mPresenter.detachView();
    }

    public void parseComments(ArrayList<TopicComment> comments) {
        mAdapter.addData(comments);
    }

    @Override
    public void onFailure(Throwable e) {

    }

    class CommentAdapter extends CommonRecyclerAdapter<TopicComment> {

        protected CommentAdapter(Context context, int itemLayoutId) {
            super(context, itemLayoutId);
        }

        @Override
        protected void getItemViewHolder(ItemViewHolder viewHolder) {

        }

        @Override
        protected void bindData(ItemViewHolder viewHolder, int position) {

        }
    }
}
