package licrafter.com.v2ex.ui.activity;/**
 * Created by Administrator on 2016/3/26.
 */

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import licrafter.com.v2ex.R;
import licrafter.com.v2ex.base.BaseToolbarActivity;
import licrafter.com.v2ex.event.CommentEvent;
import licrafter.com.v2ex.event.FavoriteEvent;
import licrafter.com.v2ex.event.ImageClickEvent;
import licrafter.com.v2ex.model.Topic;
import licrafter.com.v2ex.ui.adapter.ImagePagerAdapter;
import licrafter.com.v2ex.ui.fragment.TopicCommentListFragment;
import licrafter.com.v2ex.ui.fragment.TopicDetailFragment;
import licrafter.com.v2ex.ui.widget.gestureView.views.interfaces.GestureView;
import licrafter.com.v2ex.util.FragmentUtil;
import licrafter.com.v2ex.util.RxBus;
import rx.Subscription;
import rx.functions.Action1;

/**
 * author: lijinxiang
 * date: 2016/3/26
 **/
public class TopicDetailActivity extends BaseToolbarActivity {

    private Topic topic;
    @Bind(R.id.topic_detail_footer)
    RelativeLayout mDetailFooterLayout;
    @Bind(R.id.footer_comment)
    TextView mFooterCommentView;
    @Bind(R.id.footer_shoucang)
    TextView mFooterShoucang;
    @Bind(R.id.commentActionbtn)
    FloatingActionButton commentActionBtn;
    @Bind(R.id.transition_pager)
    ViewPager viewPager;
    @Bind(R.id.transition_full_background)
    View transition_background;

    private ImagePagerAdapter pagerAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_topic_detail;
    }

    @Override
    protected void attachVeiw() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        topic = (Topic) getIntent().getSerializableExtra("topic");
        FragmentUtil.replace(getSupportFragmentManager()
                , R.id.container, TopicDetailFragment.newInstance(topic), false, "TopicDetailFragment");
        mFooterCommentView.setText(String.valueOf(topic.getReplies()));

        pagerAdapter = new ImagePagerAdapter(viewPager, settingsSetupListener);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.margin_normal));
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initListener() {
        getSupportFragmentManager().addOnBackStackChangedListener(new android.support.v4.app.FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                int fragmentCount = getSupportFragmentManager().getFragments().size();
                if (getSupportFragmentManager().getFragments().get(fragmentCount - 1) instanceof TopicCommentListFragment) {
                    mDetailFooterLayout.setVisibility(View.GONE);
                    commentActionBtn.setVisibility(View.VISIBLE);
                } else {
                    mDetailFooterLayout.setVisibility(View.VISIBLE);
                    commentActionBtn.setVisibility(View.GONE);
                }
            }
        });
        mFooterCommentView.setOnClickListener(onClickListener);
        mFooterShoucang.setOnClickListener(onClickListener);
        commentActionBtn.setOnClickListener(onClickListener);
    }

    @Override
    protected void detachView() {

    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.footer_comment:
                    FragmentUtil.replace(getSupportFragmentManager()
                            , R.id.container, TopicCommentListFragment.getInstance(topic.getTitle()
                                    , topic.getTopicId()), true, "TopicCommentListFragment");
                    break;
                case R.id.footer_shoucang:
                    RxBus.getDefault().post(new FavoriteEvent());
                    break;
                case R.id.commentActionbtn:
                    RxBus.getDefault().post(new CommentEvent());
                    break;
            }
        }
    };

    public void setShoucangStatus(boolean isFavorite) {
        mFooterShoucang.setText(isFavorite ? "取消收藏" : "加入收藏");
        Drawable scDrawable = ContextCompat.getDrawable(this, R.mipmap.ic_shoucang);
        scDrawable.setBounds(0, 0, scDrawable.getIntrinsicHeight(), scDrawable.getIntrinsicWidth());
        Drawable unScDrawable = ContextCompat.getDrawable(this, R.mipmap.ic_unshoucang);
        unScDrawable.setBounds(0, 0, unScDrawable.getIntrinsicHeight(), unScDrawable.getIntrinsicWidth());
        mFooterShoucang.setCompoundDrawables(null, isFavorite ? unScDrawable : scDrawable, null, null);
    }

    private Subscription imageClickSubscription = RxBus.getDefault().toObserverable(ImageClickEvent.class)
            .subscribe(new Action1<ImageClickEvent>() {
                @Override
                public void call(ImageClickEvent imageClickEvent) {
                    pagerAdapter.setCurrentItem(imageClickEvent.getUrl());
                    setImagePageVisible(View.VISIBLE);
                }
            });

    public void onImageLoadSuccess(ArrayList<String> urls) {
        pagerAdapter.setData(urls);
    }

    private ImagePagerAdapter.GestureSettingsSetupListener settingsSetupListener = new ImagePagerAdapter.GestureSettingsSetupListener() {
        @Override
        public void onSetupGestureView(GestureView view) {

        }
    };

    @Override
    public void onBackPressed() {
        if (viewPager.getVisibility() == View.VISIBLE) {
            setImagePageVisible(View.GONE);
        } else {
            super.onBackPressed();
        }
    }

    public void setImagePageVisible(int visible) {
        viewPager.setVisibility(visible);
        transition_background.setVisibility(visible);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!imageClickSubscription.isUnsubscribed()) {
            imageClickSubscription.unsubscribe();
        }
    }
}
