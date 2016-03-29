package licrafter.com.v2ex.ui.fragment;/**
 * Created by Administrator on 2016/3/26.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.DragEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.Bind;
import licrafter.com.v2ex.BaseApplication;
import licrafter.com.v2ex.R;
import licrafter.com.v2ex.base.BaseFragment;
import licrafter.com.v2ex.event.FavoriteEvent;
import licrafter.com.v2ex.model.Topic;
import licrafter.com.v2ex.model.TopicDetail;
import licrafter.com.v2ex.mvp.presenters.TopicDetailPresenter;
import licrafter.com.v2ex.mvp.views.MvpView;
import licrafter.com.v2ex.ui.activity.LoginActivity;
import licrafter.com.v2ex.ui.activity.TopicDetailActivity;
import licrafter.com.v2ex.ui.widget.RichTextView;
import licrafter.com.v2ex.util.CustomUtil;
import licrafter.com.v2ex.util.RxBus;
import rx.Subscription;
import rx.functions.Action1;

/**
 * author: lijinxiang
 * date: 2016/3/26
 **/
public class TopicDetailFragment extends BaseFragment implements MvpView {

    private Topic topic;
    @Bind(R.id.swipe_layout)
    SwipeRefreshLayout mRefreshLayout;
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
    private Subscription mFavoriteSubscription;

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
        mFavoriteSubscription = RxBus.getDefault().toObserverable(FavoriteEvent.class)
                .subscribe(new Action1<FavoriteEvent>() {
                    @Override
                    public void call(FavoriteEvent favoriteEvent) {
                        if (BaseApplication.isLogin() && !topicDetail.getCsrfToken().equals("false")) {
                            mPresenter.favoriteTopic(topic.getTopicId(), topicDetail.getCsrfToken());
                        } else {
                            Toast.makeText(getActivity(), getString(R.string.please_login), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getActivity(), LoginActivity.class));
                        }
                    }
                });
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
        ((TopicDetailActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.topic_detail));
        CustomUtil.initStyle(mRefreshLayout);
        mRefreshLayout.setProgressViewOffset(false, 0, 25);
        mRefreshLayout.setEnabled(false);
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
            mRefreshLayout.setRefreshing(true);
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
        mRefreshLayout.setRefreshing(false);
        this.topicDetail = topicDetail;
        mRichTextView.setRichText(topicDetail.getContent());
        mCreatTimeView.setText("发布于 " + topicDetail.getCreateTime() + " " + topicDetail.getClickCount());
    }

    @Override
    public void onFailure(String e) {
        if (mRefreshLayout.isRefreshing()) {
            mRefreshLayout.setRefreshing(false);
        }
        mRichTextView.setRichText(e);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (!mFavoriteSubscription.isUnsubscribed()) {
            mFavoriteSubscription.unsubscribe();
        }
    }
}
