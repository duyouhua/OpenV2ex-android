package licrafter.com.v2ex.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import licrafter.com.v2ex.R;
import licrafter.com.v2ex.model.old.JSONProfit;
import licrafter.com.v2ex.ui.adapter.HeaderViewRecyclerAdapter;
import licrafter.com.v2ex.ui.adapter.MyTopicsAdapter;

/**
 * Created by shell on 15-11-15.
 */
public class MyProfitFragment extends OldBaseFragment {

    @Bind(R.id.rv_content)
    RecyclerView mRecyclerView;
    private HeaderViewRecyclerAdapter mHeaderVidewAdapter;
    private MyTopicsAdapter mAdapter;

    private RoundedImageView iv_avatar;
    private TextView tv_username, tv_num1, tv_num2, tv_num3, tv_twitter, tv_home, tv_mylocation, tv_tagline;
    private String userName;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myprofit, container, false);
        ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        if (bundle.containsKey("username")) {
            userName = bundle.getString("username");
            getProfitByUserName(userName);
            getSentTopicsByName(userName);
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(false);
        mAdapter = new MyTopicsAdapter(getActivity());
        mHeaderVidewAdapter = new HeaderViewRecyclerAdapter(mAdapter);
        View header = LayoutInflater.from(getActivity()).inflate(R.layout.header_myprofit, mRecyclerView, false);
        mHeaderVidewAdapter.addHeaderView(header);
        mRecyclerView.setAdapter(mHeaderVidewAdapter);
        setupHeader(header);
    }

    private void setupHeader(View header) {
        iv_avatar = (RoundedImageView) header.findViewById(R.id.iv_avatar);
        tv_username = (TextView) header.findViewById(R.id.tv_username);
        tv_num1 = (TextView) header.findViewById(R.id.tv_num1);
        tv_num2 = (TextView) header.findViewById(R.id.tv_num2);
        tv_num3 = (TextView) header.findViewById(R.id.tv_num3);
        tv_twitter = (TextView) header.findViewById(R.id.tv_twitter);
        tv_home = (TextView) header.findViewById(R.id.tv_home);
        tv_mylocation = (TextView) header.findViewById(R.id.tv_mylocation);
        tv_tagline = (TextView) header.findViewById(R.id.tv_tagline);
        tv_username.setText(userName);
        //登陆功能卡住了o(╯□╰)o,这是假数据
        tv_num1.setText("4");
        tv_num2.setText("17");
        tv_num3.setText("8");
    }

    public void getProfitByUserName(String username) {
//        Callback<JSONProfit> callback = new Callback<JSONProfit>() {
//            @Override
//            public void success(JSONProfit jsonProfit, Response response) {
//                if (jsonProfit != null && jsonProfit.getStatus().equals("found")) {
//                    updateHeader(jsonProfit);
//                }
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                android.util.Log.d("ljx", error.toString());
//            }
//        };
//        V2exService.v2exApi(getActivity()).getProfitByUserName(username, callback);
    }

    private void getSentTopicsByName(String username) {
//        Callback<List<JsonTopic>> callback = new Callback<List<JsonTopic>>() {
//            @Override
//            public void success(List<JsonTopic> jsonTopicList, Response response) {
//                if (jsonTopicList != null)
//                    mAdapter.resetData(jsonTopicList);
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                android.util.Log.d("ljx", error.toString());
//            }
//        };
//        V2exService.v2exApi(getActivity()).getTopicsByUserName(username, callback);
    }

    private void updateHeader(JSONProfit profit) {
        if (profit.getAvatar_large() != null) {
            //Picasso.with(getActivity()).load("https:" + profit.getAvatar_normal()).into(iv_avatar);
        }
        if (profit.getTwitter().length() > 0) {
            tv_twitter.setText(profit.getTwitter());
        } else tv_twitter.setText("没有设置该选项");
        if (profit.getWebsite().length() > 0) {
            tv_home.setText(profit.getWebsite());
        } else tv_home.setText("没有设置该选项");
        if (profit.getLocation().length() > 0) {
            tv_mylocation.setText(profit.getLocation());
        } else tv_mylocation.setText("没有设置该选项");

        if (profit.getTagline().length() > 0) {
            tv_tagline.setText(profit.getTagline());
        }
    }

}
