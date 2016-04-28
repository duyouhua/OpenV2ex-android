package licrafter.com.v2ex.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
    TabLayout mCategoryTabLayout;
    @Bind(R.id.categoryViewPager)
    ViewPager mCategoryViewPager;

    private List<Tab> mTabs;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_category;
    }

    @Override
    protected void attachView() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static CategoryFragment newInstance() {
        CategoryFragment fragment = new CategoryFragment();
        return fragment;
    }

    @Override
    public void initViews(View view) {
        mTabs = Constant.getmTables();
        mCategoryTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mCategoryTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mCategoryTabLayout.setTabTextColors(getResources().getColor(R.color.grey800), getResources().getColor(R.color.white));
        ViewCompat.setElevation(mCategoryTabLayout, getResources().getDimension(R.dimen.appbar_elevation));
        mCategoryViewPager.setAdapter(new MainPagerAdapter(getChildFragmentManager(), mTabs));
        mCategoryTabLayout.setupWithViewPager(mCategoryViewPager);
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
