package licrafter.com.v2ex.ui.fragment;/**
 * Created by Administrator on 2016/3/26.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.Bind;
import licrafter.com.v2ex.R;
import licrafter.com.v2ex.base.BaseFragment;
import licrafter.com.v2ex.model.Topic;
import licrafter.com.v2ex.model.TopicDetail;
import licrafter.com.v2ex.mvp.presenters.TopicDetailPresenter;
import licrafter.com.v2ex.mvp.views.MvpView;
import licrafter.com.v2ex.ui.activity.TopicDetailActivity;
import licrafter.com.v2ex.ui.widget.RichTextView;

/**
 * author: lijinxiang
 * date: 2016/3/26
 **/
public class TopicDetailFragment extends BaseFragment implements MvpView {

    private Topic topic;
    @Bind(R.id.iv_avatar)
    RoundedImageView mAvatarView;
    @Bind(R.id.tv_username)
    TextView mUserNameView;
    @Bind(R.id.tv_create_time)
    TextView mCreatTimeView;
    @Bind(R.id.tv_title)
    TextView mTitleView;
    @Bind(R.id.tv_content)
    RichTextView mRichTextView;
    @Bind(R.id.detail_ScrollView)
    NestedScrollView mDetailScrollView;

    private TopicDetailPresenter mPresenter;
    private TopicDetail topicDetail;

    public static TopicDetailFragment newInstance(Topic topic) {
        TopicDetailFragment fragment = new TopicDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("topic", topic);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            topic = (Topic) getArguments().getSerializable("topic");
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_topic_detail;
    }

    @Override
    protected void attachView() {
        mPresenter = new TopicDetailPresenter();
        mPresenter.attachView(this);
    }

    @Override
    protected void initViews(View view) {
        ((TopicDetailActivity) getActivity()).setAppBarShadow(false);
        ((TopicDetailActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.topic_detail));
        if (topic != null) {
            mTitleView.setText(topic.getTitle());
            Glide.with(this).load(topic.getAvatar()).into(mAvatarView);
            mUserNameView.setText(topic.getUserId());
        }
    }

    @Override
    protected void setListeners() {

    }

    @Override
    protected void loadData() {
        if (topicDetail == null) {
            mPresenter.getTopicDetail(topic.getTopicId());
        } else {
            parseTopicDetail(topicDetail);
        }
    }

    @Override
    protected void detachView() {
        mPresenter.detachView();
    }

    public void parseTopicDetail(TopicDetail topicDetail) {
        this.topicDetail = topicDetail;
        mRichTextView.setRichText(topicDetail.getContent());
        mCreatTimeView.setText("发布于 " + topicDetail.getCreateTime() + " " + topicDetail.getClickCount());
    }

    @Override
    public void onFailure(Throwable e) {

    }
}
