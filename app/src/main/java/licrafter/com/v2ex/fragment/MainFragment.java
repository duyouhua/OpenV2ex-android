package licrafter.com.v2ex.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;
import licrafter.com.v2ex.MainActivity;
import licrafter.com.v2ex.R;
import licrafter.com.v2ex.adapter.MainPagerAdapter;
import licrafter.com.v2ex.model.Tab;
import licrafter.com.v2ex.util.Constant;
import licrafter.com.v2ex.widget.PagerSlidingTabStrip;

/**
 * Created by lijinxiang on 11/5/15.
 */
public class MainFragment extends BaseFragment{

    @Bind(R.id.tabs)
    PagerSlidingTabStrip mTabStrip;
    @Bind(R.id.vp_content)
    ViewPager mVpContent;

    private List<Tab> nodes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nodes = Constant.getTables();
        mVpContent.setOffscreenPageLimit(10);
        mVpContent.setAdapter(new MainPagerAdapter(getActivity().getSupportFragmentManager(), nodes));
        mTabStrip.setViewPager(mVpContent);
        mTabStrip.setTextColor(getResources().getColor(R.color.grey400));
    }

    public Fragment newInstance(MainActivity cativity){
        Fragment fragment = new MainFragment();
        return fragment;
    }

}
