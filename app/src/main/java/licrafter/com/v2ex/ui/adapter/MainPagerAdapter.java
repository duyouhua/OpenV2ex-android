package licrafter.com.v2ex.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.List;

import licrafter.com.v2ex.model.Tab;
import licrafter.com.v2ex.ui.fragment.TopicListFragment;

/**
 * Created by lijinxiang on 11/5/15.
 */
public class MainPagerAdapter extends FragmentPagerAdapter {

    private List<Tab> mTabs;

    public MainPagerAdapter(FragmentManager fm, List<Tab> tabs) {
        super(fm);
        this.mTabs = tabs;
    }

    @Override
    public Fragment getItem(int position) {
        return TopicListFragment.getInstance(mTabs.get(position).getTabValue());
    }

    @Override
    public int getCount() {
        return mTabs.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabs.get(position).getTabName();
    }

}
