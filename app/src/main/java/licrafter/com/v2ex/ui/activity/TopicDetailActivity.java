package licrafter.com.v2ex.ui.activity;/**
 * Created by Administrator on 2016/3/26.
 */

import android.os.Bundle;

import licrafter.com.v2ex.R;
import licrafter.com.v2ex.base.BaseToolbarActivity;
import licrafter.com.v2ex.model.Topic;
import licrafter.com.v2ex.ui.fragment.TopicDetailFragment;
import licrafter.com.v2ex.util.FragmentUtil;

/**
 * author: lijinxiang
 * date: 2016/3/26
 **/
public class TopicDetailActivity extends BaseToolbarActivity {

    private Topic topic;

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
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void detachView() {

    }

}
