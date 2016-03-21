package licrafter.com.v2ex.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import licrafter.com.v2ex.model.Tab;
import licrafter.com.v2ex.ui.fragment.TopicListFragment;

/**
 * Created by lijinxiang on 11/5/15.
 */
public class MainPagerAdapter extends FragmentStatePagerAdapter {

    private List<Tab> tabs;

    public MainPagerAdapter(FragmentManager fm, List<Tab> tabs) {
        super(fm);
        this.tabs = tabs;
    }

    @Override
    public Fragment getItem(int position) {
        return TopicListFragment.getInstance(tabs.get(position).getTabValue(), null);
    }

    @Override
    public int getCount() {
        return tabs.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs.get(position).getTabName();
    }

}
