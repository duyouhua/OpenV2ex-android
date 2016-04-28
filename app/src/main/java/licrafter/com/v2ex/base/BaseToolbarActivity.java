package licrafter.com.v2ex.base;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import butterknife.Bind;
import licrafter.com.v2ex.R;

/**
 * Created by Administrator on 2016/1/31.
 */
public abstract class BaseToolbarActivity extends BaseAppCompatActivity {

    @Bind(R.id.toolbar)
    protected Toolbar mToolbar;
    @Bind(R.id.appBarLayout)
    protected AppBarLayout mAppBarLayout;

    protected ActionBarHelper mActionBarHelper;

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        this.initToolbarHelper();
    }

    protected void initToolbarHelper() {
        if (this.mToolbar == null || this.mAppBarLayout == null) {
            return;
        }
        this.setSupportActionBar(mToolbar);
        this.mActionBarHelper = new ActionBarHelper();
        this.mActionBarHelper.init();
        this.mActionBarHelper.setDrawerTitle(getString(R.string.action_menu));

//        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
//            mAppBarLayout.setElevation(6.0f);
//        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
            return true;
        } else
            return super.onOptionsItemSelected(item);
    }


    public void setAppBarShadow(boolean isShown) {
        final int elevation = isShown
                ? getResources().getDimensionPixelSize(R.dimen.appbar_elevation) : 0;
        ViewCompat.setElevation(mAppBarLayout, elevation);
    }

    public class ActionBarHelper {
        private ActionBar mActionBar;
        private CharSequence mDrawerTitle;
        private CharSequence mTitle;

        public ActionBarHelper() {
            this.mActionBar = getSupportActionBar();
        }

        public void init() {
            if (mActionBar == null) return;
            this.mActionBar.setDisplayHomeAsUpEnabled(true);
            this.mActionBar.setDisplayShowHomeEnabled(false);
            this.mTitle = mDrawerTitle = getTitle();
        }

        public void onDrawerClosed() {
            if (mActionBar == null) return;
            this.mActionBar.setTitle(this.mTitle);
        }

        public void onDrawerOpened() {
            if (mActionBar == null) return;
            this.mActionBar.setTitle(this.mDrawerTitle);
        }

        public void setTitle(CharSequence title) {
            this.mTitle = title;
        }

        public void setDrawerTitle(CharSequence title) {
            this.mDrawerTitle = title;
        }
    }
}
