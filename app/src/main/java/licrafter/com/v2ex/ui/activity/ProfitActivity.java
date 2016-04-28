package licrafter.com.v2ex.ui.activity;/**
 * Created by Administrator on 2016/4/19.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

import butterknife.Bind;
import licrafter.com.v2ex.R;
import licrafter.com.v2ex.base.BaseToolbarActivity;
import licrafter.com.v2ex.model.ProfitTopic;
import licrafter.com.v2ex.model.JSONProfit;
import licrafter.com.v2ex.mvp.presenters.ProfitPresenter;
import licrafter.com.v2ex.mvp.views.MvpView;
import licrafter.com.v2ex.ui.adapter.CommonRecyclerAdapter;

/**
 * author: lijinxiang
 * date: 2016/4/19
 **/
public class ProfitActivity extends BaseToolbarActivity implements MvpView {

    @Bind(R.id.profitListView)
    RecyclerView mProfitListView;
    @Bind(R.id.iv_avatar)
    RoundedImageView mAvatar;
    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbar;
    @Bind(R.id.tv_tagline)
    TextView mTagLine;

    private ProfitAdapter mProfitAdapter;
    private ProfitPresenter mPresenter;
    private String mUserName;
    private JSONProfit mProfit;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_profit;
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

        mCollapsingToolbar.setTitle(mUserName);
        mProfitAdapter = new ProfitAdapter(this, R.layout.item_profit_topic, R.layout.item_profit_info);
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

    }

    @Override
    protected void detachView() {
        mPresenter.detachView();
    }

    @Override
    public void onFailure(String e) {
    }

    public void parseProfitHeader(JSONProfit jsonProfit) {
        mProfit = jsonProfit;
        Glide.with(this).load("http:" + mProfit.getAvatar_large()).into(mAvatar);
        mTagLine.setText(mProfit.getTagline());
        mProfitAdapter.notifyDataSetChanged();
    }

    public void parseProfitTopic(ArrayList<ProfitTopic> profitTopics) {
        mProfitAdapter.setData(profitTopics);
    }

    class ProfitAdapter extends CommonRecyclerAdapter<ProfitTopic> {


        protected ProfitAdapter(Context context, int itemLayoutId, int headLayoutId) {
            super(context, itemLayoutId, headLayoutId);
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
