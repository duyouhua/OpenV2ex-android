package licrafter.com.v2ex.ui.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.List;
import butterknife.Bind;
import licrafter.com.v2ex.R;
import licrafter.com.v2ex.base.BaseFragment;
import licrafter.com.v2ex.model.Tab;
import licrafter.com.v2ex.ui.adapter.MainPagerAdapter;
import licrafter.com.v2ex.util.Constant;

/**
 * author: lijinxiang
 * date: 2016/3/17
 **/
public class CategoryFragment extends BaseFragment {

    @Bind(R.id.categoryTabLayout)
    TabLayout categoryTabLayout;
    @Bind(R.id.categoryViewPager)
    ViewPager categoryViewPager;

    private List<Tab> tabs;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_category;
    }

    @Override
    protected void attachView() {

    }

    public static CategoryFragment newInstance() {
        CategoryFragment fragment = new CategoryFragment();
        return fragment;
    }

    @Override
    public void initViews(View view) {
        tabs = Constant.getTables();
        categoryTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        categoryTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        categoryTabLayout.setTabTextColors(getResources().getColor(R.color.grey800), getResources().getColor(R.color.white));
        ViewCompat.setElevation(categoryTabLayout, getResources().getDimension(R.dimen.appbar_elevation));
        categoryViewPager.setAdapter(new MainPagerAdapter(getChildFragmentManager(), tabs));
        categoryTabLayout.setupWithViewPager(categoryViewPager);
    }

    @Override
    public void setListeners() {

    }

    @Override
    public void loadData() {

    }

    @Override
    protected void detachView() {

    }
}
