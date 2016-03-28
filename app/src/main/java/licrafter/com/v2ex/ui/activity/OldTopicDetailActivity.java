package licrafter.com.v2ex.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import butterknife.Bind;
import butterknife.ButterKnife;
import licrafter.com.v2ex.R;
import licrafter.com.v2ex.model.Node;
import licrafter.com.v2ex.model.old.SeriableTopic;
import licrafter.com.v2ex.ui.adapter.HeaderViewRecyclerAdapter;
import licrafter.com.v2ex.ui.adapter.TopicContentAdapter;
import licrafter.com.v2ex.util.Constant;
import licrafter.com.v2ex.ui.widget.RichTextView;

/**
 * Created by shell on 15-11-12.
 */
public class OldTopicDetailActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {


    @Bind(R.id.swipe_layout)
    SwipeRefreshLayout mSwipeLayout;
    @Bind(R.id.rv_content)
    RecyclerView mRecyclerView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private ImageView iv_avatar;
    private TextView tv_username, tv_create_time, tv_replies, tv_title;
    private RichTextView tv_content;
    private RelativeLayout rl_profit;
    private SeriableTopic topic;
    private TopicContentAdapter mAdapter;
    private HeaderViewRecyclerAdapter mHeaderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);
        ButterKnife.bind(this);
        if (getIntent().hasExtra(Constant.EXTRA.TOPIC)) {
            topic = (SeriableTopic) getIntent().getSerializableExtra(Constant.EXTRA.TOPIC);
        }
        setupView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
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
        mSwipeLayout.setProgressViewOffset(false, 0, 24);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(false);
        View headerView = LayoutInflater.from(this).inflate(R.layout.header_topic, mRecyclerView, false);
        iv_avatar = (ImageView) headerView.findViewById(R.id.iv_avatar);
        tv_username = (TextView) headerView.findViewById(R.id.tv_username);
        tv_create_time = (TextView) headerView.findViewById(R.id.tv_create_time);
        rl_profit = (RelativeLayout) headerView.findViewById(R.id.rl_profit);
        tv_replies = (TextView) headerView.findViewById(R.id.tv_replies);
        tv_title = (TextView) headerView.findViewById(R.id.tv_title);
        tv_content = (RichTextView) headerView.findViewById(R.id.tv_content);
        rl_profit.setOnClickListener(this);
        mAdapter = new TopicContentAdapter(this);
        mHeaderAdapter = new HeaderViewRecyclerAdapter(mAdapter);
        mHeaderAdapter.addHeaderView(headerView);
        mRecyclerView.setAdapter(mHeaderAdapter);
        setUpHeaderView();
        getData(1);
    }

    private void getData(int page) {
//        mSwipeLayout.setRefreshing(true);
//        Callback<Response> callback = new Callback<Response>() {
//            @Override
//            public void success(Response response, Response response2) {
//                mSwipeLayout.setRefreshing(false);
//                try {
//                    String body = CustomUtil.streamFormToString(response.getBody().in());
//                    mData = JsoupUtil.parseTopicRes(OldTopicDetailActivity.this, body);
//                    if (mData != null) {
//                        updateView(mData);
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                mSwipeLayout.setRefreshing(false);
//                Toast.makeText(OldTopicDetailActivity.this, "网络错误o(╯□╰)o", Toast.LENGTH_SHORT).show();
//            }
//        };
//        V2exService.v2EX(this).getTopicDetails(topic.getTopicId(), page, callback);
    }

    private void setUpHeaderView() {
        //Picasso.with(this).load(topic.getAvatar()).into(iv_avatar);
        tv_username.setText(topic.getUserName());
        setTopicTitle();
        tv_content.setRichText("");
    }

//    private void updateView(TopicResponse res) {
//        tv_replies.setText("全部 " + res.getDetail().repliesCount);
//        tv_create_time.setText(res.getDetail().createTime);
//        if (res.getDetail().content != null) {
//            tv_content.setRichText(res.getDetail().content);
//        }
//        mAdapter.setData(res.getComments());
//    }

    private void setTopicTitle() {
        String title = topic.getTitle() + " > " + topic.getNodeName();
        SpannableString builder = new SpannableString(title);
        ClickableSpan span = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
               // Intent intent = new Intent(OldTopicDetailActivity.this, OldTopicDetailActivity.class);
                Node node = new Node();
                node.setName(topic.getNodeId());
                node.setTitle(topic.getNodeName());
//                intent.putExtra(Constant.EXTRA.NODE, node);
//                startActivity(intent);
            }
        };
        builder.setSpan(span, title.indexOf(">"), title.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_title.setText(builder);
        tv_title.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public void onRefresh() {
        getData(1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_profit:
                Intent intent = new Intent(this, ProfitActivity.class);
                intent.putExtra(Constant.EXTRA.PROFIT_TYPE,Constant.EXTRA.PRO_ME);
                intent.putExtra("title",topic.getUserName());
                startActivity(intent);
                break;
        }
    }
}
