package licrafter.com.v2ex.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import licrafter.com.v2ex.R;
import licrafter.com.v2ex.api.Server;
import licrafter.com.v2ex.model.Response.TopicResponse;
import licrafter.com.v2ex.ui.activity.BaseActivity;
import licrafter.com.v2ex.ui.adapter.HeaderViewRecyclerAdapter;
import licrafter.com.v2ex.ui.adapter.TopicAdapter;
import licrafter.com.v2ex.ui.util.Constant;
import licrafter.com.v2ex.ui.util.CustomUtil;
import licrafter.com.v2ex.ui.util.JsoupUtil;
import licrafter.com.v2ex.ui.widget.RichTextView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by shell on 15-11-12.
 */
public class TopicActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {


    @Bind(R.id.swipe_layout)
    SwipeRefreshLayout mSwipeLayout;
    @Bind(R.id.rv_content)
    RecyclerView mRecyclerView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private ImageView iv_avatar;
    private TextView tv_username, tv_create_time, tv_replies, tv_title;
    private RichTextView tv_content;

    private String mAvatar;
    private String mTitle;
    private String mUserName;
    private String mTopicId;
    private TopicResponse mData;
    private TopicAdapter mAdapter;
    private HeaderViewRecyclerAdapter mHeaderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        if (intent.hasExtra(Constant.EXTRA.TOPICID) && intent.hasExtra(Constant.EXTRA.USERNAME)
                && intent.hasExtra(Constant.EXTRA.TOPIC_TITLE) && intent.hasExtra(Constant.EXTRA.AVATAR)) {
            mAvatar = intent.getStringExtra(Constant.EXTRA.AVATAR);
            mTitle = intent.getStringExtra(Constant.EXTRA.TOPIC_TITLE);
            mUserName = intent.getStringExtra(Constant.EXTRA.USERNAME);
            mTopicId = intent.getStringExtra(Constant.EXTRA.TOPICID);
        }
        setupView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void setupView() {
        toolbar.setTitle("帖子详情");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mSwipeLayout.setOnRefreshListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(false);
        View headerView = LayoutInflater.from(this).inflate(R.layout.header_topic, mRecyclerView, false);
        iv_avatar = (ImageView) headerView.findViewById(R.id.iv_avatar);
        tv_username = (TextView) headerView.findViewById(R.id.tv_username);
        tv_create_time = (TextView) headerView.findViewById(R.id.tv_create_time);
        tv_replies = (TextView) headerView.findViewById(R.id.tv_replies);
        tv_title = (TextView) headerView.findViewById(R.id.tv_title);
        tv_content = (RichTextView) headerView.findViewById(R.id.tv_content);
        mAdapter = new TopicAdapter(this);
        mHeaderAdapter = new HeaderViewRecyclerAdapter(mAdapter);
        mHeaderAdapter.addHeaderView(headerView);
        mRecyclerView.setAdapter(mHeaderAdapter);
        setUpHeaderView();
        getData(1);
    }

    private void getData(int page) {
        mSwipeLayout.setRefreshing(true);
        Callback<Response> callback = new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                mSwipeLayout.setRefreshing(false);
                try {
                    String body = CustomUtil.streamFormToString(response.getBody().in());
                    mData = JsoupUtil.parseTopicRes(TopicActivity.this, body);
                    if (mData != null) {
                        updateView(mData);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                mSwipeLayout.setRefreshing(false);
                Toast.makeText(TopicActivity.this,"网络错误o(╯□╰)o",Toast.LENGTH_SHORT);
            }
        };
        Server.v2EX(this).getTopicDetails(mTopicId, page, callback);
    }

    private void setUpHeaderView() {
        Picasso.with(this).load(mAvatar).into(iv_avatar);
        tv_username.setText(mUserName);
        tv_title.setText(mTitle);
        tv_content.setRichText("");
    }

    private void updateView(TopicResponse res) {
        tv_content.setRichText(res.getDetail().content);
        mAdapter.setData(res.getComments());
    }

    @Override
    public void onRefresh() {

    }
}
