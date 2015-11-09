package licrafter.com.v2ex.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import licrafter.com.v2ex.fragment.TabFragment;
import licrafter.com.v2ex.model.Table;
import licrafter.com.v2ex.util.Constant;

/**
 * Created by lijinxiang on 11/5/15.
 */
public class MainPagerAdapter extends FragmentStatePagerAdapter {

    private List<Table> nodes;
    private List<Fragment> fragments;

    public MainPagerAdapter(FragmentManager fm, List<Table> nodes){
        super(fm);
        this.nodes = nodes;
        fragments = new ArrayList<>();
        initFragment();
    }
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return nodes.get(position).getTabName();
    }

    private void initFragment(){

        for (int i=0;i<nodes.size();i++){
            Fragment fragment = new TabFragment();
            Bundle bundle = new Bundle();
            bundle.putString(Constant.EXTRA.TAB_TITLE,nodes.get(i).getTabValue());
            fragment.setArguments(bundle);
            fragments.add(fragment);
        }
    }
}
