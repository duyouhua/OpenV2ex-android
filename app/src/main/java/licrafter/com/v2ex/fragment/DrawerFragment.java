package licrafter.com.v2ex.fragment;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import licrafter.com.v2ex.R;

/**
 * Created by lijinxiang on 11/5/15.
 */
public class DrawerFragment extends BaseFragment {

    private static final String PREF_USER_LEARN_DRAWER = "navigation_drawer_learned";

    @Bind(R.id.profile_image)CircleImageView iv_profit;

    private ActionBarDrawerToggle mDrawerToggle;
    private View mFragmentContainerView;
    private DrawerLayout mDrawerLayout;
    private boolean mUserLearnDrawer;
    private boolean mFromSavedInstanceState;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigation_draw, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserLearnDrawer = mSharedPrefUtil.getBooleanSP(PREF_USER_LEARN_DRAWER, false);
        if (savedInstanceState != null) {
            mFromSavedInstanceState = true;
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Picasso.with(getActivity()).load("https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=504471062,2759042061&fm=58").into(iv_profit);
    }

    public ActionBarDrawerToggle getActionBarDrawerToggle() {
        return mDrawerToggle;
    }

    public void setActionBarDrawerToggle(ActionBarDrawerToggle toggle) {
        this.mDrawerToggle = toggle;
    }

    public void setup(int fragmentId, DrawerLayout drawerLayout, Toolbar toolbar) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!isAdded()) return;
                getActivity().invalidateOptionsMenu();
                if (!isAdded()) return;
                if (!mUserLearnDrawer) {
                    mUserLearnDrawer = true;
                    mSharedPrefUtil.setBooleanSP(PREF_USER_LEARN_DRAWER, true);
                }
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        if (!mUserLearnDrawer && !mFromSavedInstanceState)
            mDrawerLayout.openDrawer(mFragmentContainerView);

        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    public void openDrawer() {
        mDrawerLayout.openDrawer(mFragmentContainerView);
    }

    public void closeDrawer() {
        mDrawerLayout.closeDrawer(mFragmentContainerView);
    }

    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }


}
