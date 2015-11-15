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
import licrafter.com.v2ex.ui.adapter.HeaderViewRecyclerAdapter;
import licrafter.com.v2ex.ui.adapter.MyTopicsAdapter;

/**
 * Created by shell on 15-11-15.
 */
public class MyProfitFragment extends BaseFragment {

    @Bind(R.id.rv_content)
    RecyclerView mRecyclerView;
    private HeaderViewRecyclerAdapter mHeaderVidewAdapter;
    private MyTopicsAdapter mAdapter;

    private RoundedImageView iv_avatar;
    private TextView tv_username, tv_num1, tv_num2, tv_num3, tv_twitter, tv_home, tv_mylocation;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myprofit, container, false);
        ButterKnife.bind(this, view);
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
        iv_avatar =(RoundedImageView) header.findViewById(R.id.iv_avatar);
        tv_username = (TextView) header.findViewById(R.id.tv_username);
        tv_num1 = (TextView) header.findViewById(R.id.tv_num1);
        tv_num2 = (TextView) header.findViewById(R.id.tv_num2);
        tv_num3 = (TextView) header.findViewById(R.id.tv_num3);
        tv_twitter = (TextView) header.findViewById(R.id.tv_twitter);
        tv_home = (TextView) header.findViewById(R.id.tv_home);
        tv_mylocation = (TextView) header.findViewById(R.id.tv_mylocation);
    }

}
