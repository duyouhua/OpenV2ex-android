package licrafter.com.v2ex.ui.activity;/**
 * Created by Administrator on 2016/4/15.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.Bind;
import licrafter.com.v2ex.R;
import licrafter.com.v2ex.base.BaseAppCompatActivity;
import licrafter.com.v2ex.model.ProfitTopic;
import licrafter.com.v2ex.model.old.JSONProfit;
import licrafter.com.v2ex.mvp.presenters.ProfitPresenter;
import licrafter.com.v2ex.mvp.views.MvpView;
import licrafter.com.v2ex.ui.adapter.CommonRecyclerAdapter;
import licrafter.com.v2ex.util.CustomUtil;

/**
 * author: lijinxiang
 * date: 2016/4/15
 **/
public class ProfitActivity extends BaseAppCompatActivity implements MvpView {

    @Bind(R.id.profitListView)
    RecyclerView mProfitListView;
    @Bind(R.id.swipe_layout)
    SwipeRefreshLayout mSwipeLayout;

    private ProfitAdapter mProfitAdapter;
    private ProfitPresenter mPresenter;
    private String mUserName;
    private JSONProfit mProfit;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_profit;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {

    }

    @Override
    protected void attachVeiw() {
        mPresenter = new ProfitPresenter();
        mPresenter.attachView(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        if (getIntent().hasExtra("user_name")) {
            mUserName = getIntent().getStringExtra("user_name");
        }

        CustomUtil.initStyle(mSwipeLayout);
        mSwipeLayout.setRefreshing(true);
        mProfitAdapter = new ProfitAdapter(this, R.layout.item_profit_topic, R.layout.header_myprofit);
        mProfitListView.setLayoutManager(new LinearLayoutManager(this));
        mProfitListView.setHasFixedSize(false);
        mProfitListView.setAdapter(mProfitAdapter);
    }

    @Override
    protected void loadData() {
        mPresenter.getProfitHeader(mUserName);
        mPresenter.getTopicsByUserId(mUserName);
    }

    @Override
    protected void initListener() {
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getProfitHeader(mUserName);
                mPresenter.getTopicsByUserId(mUserName);
            }
        });
    }

    @Override
    protected void detachView() {
        mPresenter.detachView();
    }

    @Override
    public void onFailure(String e) {
        mSwipeLayout.setRefreshing(false);
    }

    public void parseProfitHeader(JSONProfit jsonProfit) {
        mSwipeLayout.setRefreshing(false);
        mProfit = jsonProfit;
        mProfitAdapter.notifyDataSetChanged();
    }

    public void parseProfitTopic(ArrayList<ProfitTopic> profitTopics) {
        mProfitAdapter.setData(profitTopics);
    }

    class ProfitAdapter extends CommonRecyclerAdapter<ProfitTopic> {


        protected ProfitAdapter(Context context, int itemLayoutId, int headerLayoutId) {
            super(context, itemLayoutId, headerLayoutId);
        }

        @Override
        protected void getItemViewHolder(ItemViewHolder viewHolder) {

        }

        @Override
        protected void bindData(ItemViewHolder viewHolder, int position) {
            ProfitTopic topic = mDatas.get(position);
            viewHolder.getTextView(R.id.tv_title).setText(topic.getTitle());
            viewHolder.getTextView(R.id.tv_other_info).setText(topic.getOtherInfo());
        }

        @Override
        protected void bindHeader(HeaderViewHolder viewHolder, int position) {
            super.bindHeader(viewHolder, position);
            if (mProfit != null) {
                Glide.with(mContext).load("http:" + mProfit.getAvatar_large()).into(viewHolder.getImageView(R.id.iv_avatar));
                viewHolder.getTextView(R.id.tv_username).setText(mProfit.getUsername());
                viewHolder.getTextView(R.id.tv_tagline).setText(mProfit.getTagline());
                if (!TextUtils.isEmpty(mProfit.getTwitter())) {
                    viewHolder.getTextView(R.id.tv_twitter).setText(mProfit.getTwitter());
                }
                if (!TextUtils.isEmpty(mProfit.getWebsite())) {
                    viewHolder.getTextView(R.id.tv_home).setText(mProfit.getWebsite());
                }
                if (!TextUtils.isEmpty(mProfit.getLocation())) {
                    viewHolder.getTextView(R.id.tv_mylocation).setText(mProfit.getLocation());
                }
            }
        }
    }
}
