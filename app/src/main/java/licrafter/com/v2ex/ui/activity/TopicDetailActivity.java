package licrafter.com.v2ex.ui.activity;/**
 * Created by Administrator on 2016/3/26.
 */

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import licrafter.com.v2ex.R;
import licrafter.com.v2ex.base.BaseToolbarActivity;
import licrafter.com.v2ex.model.Topic;
import licrafter.com.v2ex.ui.fragment.TopicCommentListFragment;
import licrafter.com.v2ex.ui.fragment.TopicDetailFragment;
import licrafter.com.v2ex.util.FragmentUtil;

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
                } else {
                    mDetailFooterLayout.setVisibility(View.VISIBLE);
                }
            }
        });
        mFooterCommentView.setOnClickListener(onClickListener);
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
            }
        }
    };
}
