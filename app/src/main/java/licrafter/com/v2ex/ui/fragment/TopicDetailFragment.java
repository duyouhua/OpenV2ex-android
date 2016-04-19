package licrafter.com.v2ex.ui.fragment;/**
 * Created by Administrator on 2016/3/26.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.RelativeLayout;
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
import licrafter.com.v2ex.ui.activity.ProfitActivity;
import licrafter.com.v2ex.ui.activity.TopicDetailActivity;
import licrafter.com.v2ex.ui.widget.LJWebView;
import licrafter.com.v2ex.util.RxBus;
import rx.Subscription;
import rx.functions.Action1;

/**
 * author: lijinxiang
 * date: 2016/3/26
 **/
public class TopicDetailFragment extends BaseFragment implements MvpView {

    private Topic topic;
    private static final int REQ_LOGIN = 0x001;

    @Bind(R.id.iv_avatar)
    RoundedImageView mAvatarView;
    @Bind(R.id.tv_username)
    TextView mUserNameView;
    @Bind(R.id.tv_create_time)
    TextView mCreatTimeView;
    @Bind(R.id.tv_title)
    TextView mTitleView;
    @Bind(R.id.tv_content)
    LJWebView mcontentWebView;
    @Bind(R.id.detail_ScrollView)
    NestedScrollView mDetailScrollView;
    @Bind(R.id.rl_profit)
    RelativeLayout mProfitRelative;

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
        if (topic != null) {
            mTitleView.setText(topic.getTitle());
            Glide.with(this).load(topic.getAvatar()).into(mAvatarView);
            mUserNameView.setText(topic.getUserId());
        }
    }

    @Override
    protected void setListeners() {
        mFavoriteSubscription = RxBus.getDefault().toObserverable(FavoriteEvent.class)
                .subscribe(new Action1<FavoriteEvent>() {
                    @Override
                    public void call(FavoriteEvent favoriteEvent) {
                        if (BaseApplication.isLogin() && !topicDetail.getCsrfToken().equals("false")) {
                            showLoadingDialog();
                            if (topicDetail.isFravorite()) {
                                mPresenter.unFavoriteTopic(topic.getTopicId(), topicDetail.getCsrfToken());
                            } else {
                                mPresenter.favoriteTopic(topic.getTopicId(), topicDetail.getCsrfToken());
                            }
                        } else {
                            Toast.makeText(getActivity(), getString(R.string.please_login), Toast.LENGTH_SHORT).show();
                            startActivityForResult(new Intent(getActivity(), LoginActivity.class), REQ_LOGIN);
                        }
                    }
                });
        mProfitRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProfitActivity.class);
                intent.putExtra("user_name", topic.getUserId());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void loadData() {
        if (topicDetail == null) {
            showLoadingDialog();
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
        hideLoadingDialog();
        this.topicDetail = topicDetail;
        ((TopicDetailActivity) getActivity()).onImageLoadSuccess(topicDetail.getImgUrls());
        mCreatTimeView.setText("发布于 " + topicDetail.getCreateTime() + " " + topicDetail.getClickCount());
        ((TopicDetailActivity) getActivity()).setShoucangStatus(topicDetail.isFravorite());
        mcontentWebView.loadHtml(topicDetail.getContent());
    }

    @Override
    public void onFailure(String e) {
        hideLoadingDialog();
        mcontentWebView.loadData(e, "text/html; charset=UTF-8", null);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (!mFavoriteSubscription.isUnsubscribed()) {
            mFavoriteSubscription.unsubscribe();
        }
    }

    public void parseFavorite(String token, boolean isFavorite) {
        hideLoadingDialog();
        Toast.makeText(getActivity(), "操作成功", Toast.LENGTH_SHORT).show();
        topicDetail.setCsrfToken(token);
        topicDetail.setFravorite(isFavorite);
        ((TopicDetailActivity) getActivity()).setShoucangStatus(isFavorite);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == REQ_LOGIN) {
            showLoadingDialog();
            mPresenter.getTopicDetail(topic.getTopicId());
        }
    }

}
