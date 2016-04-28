package licrafter.com.v2ex.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.Bind;
import licrafter.com.v2ex.BaseApplication;
import licrafter.com.v2ex.R;
import licrafter.com.v2ex.event.LogoutEvent;
import licrafter.com.v2ex.event.UserEvent;
import licrafter.com.v2ex.ui.activity.WebViewActivity;
import licrafter.com.v2ex.ui.widget.LoginDialog;
import licrafter.com.v2ex.util.RxBus;
import licrafter.com.v2ex.util.SharedPreferenceUtils;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by Administrator on 2016/1/31.
 */
public abstract class BaseDrawerLayoutActivity extends BaseToolbarActivity {

    public final String TAG = this.getClass().getName();

    @Bind(R.id.drawerLayout)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.navigationView)
    NavigationView mNavigationView;
    private ActionBarDrawerToggle mActionBarDrawerToggle;

    private View mHeaderView;
    private TextView mUserInfoTextView;
    private ImageView mUserAvatarImageView;
    protected MenuItem mOld = null;
    private Subscription mUserSubscription;
    private Subscription mLogoutSubscription;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDialog = new LoginDialog(this);
        this.mNavigationView.setNavigationItemSelectedListener(itemSelectedListener);
        this.mDrawerLayout.setDrawerListener(new EasyDrawerListener());
        Menu menu = mNavigationView.getMenu();
        mOld = menu.findItem(R.id.topicDrawerMenuItem);
        this.mActionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                this.mDrawerLayout,
                R.string.action_menu,
                R.string.app_name
        );
        mHeaderView = mNavigationView.getHeaderView(0);
        mUserInfoTextView = (TextView) mHeaderView.findViewById(R.id.userNameTextView);
        mUserAvatarImageView = (ImageView) mHeaderView.findViewById(R.id.userAvatarImageView);
        mUserInfoTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!BaseApplication.isLogin()) {
                    mDialog.show();
                } else {
                    Intent intent = new Intent(BaseDrawerLayoutActivity.this, WebViewActivity.class);
                    intent.putExtra("url", ("https://www.v2ex.com/member/" + SharedPreferenceUtils.getString("user_name", "")));
                    startActivity(intent);
                }
            }
        });
        if (BaseApplication.isLogin()) {
            setUserInfo(null);
        }
        onEvent();
    }

    private void onEvent() {
        mUserSubscription = RxBus.getDefault().toObserverable(UserEvent.class).subscribe(new Action1<UserEvent>() {
            @Override
            public void call(UserEvent userEvent) {
                setUserInfo(userEvent);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });
        mLogoutSubscription = RxBus.getDefault().toObserverable(LogoutEvent.class).subscribe(new Action1<LogoutEvent>() {
            @Override
            public void call(LogoutEvent logoutEvent) {
                setUserInfo(null);
            }
        });
    }

    protected void setUserInfo(UserEvent userEvent) {
        if (userEvent == null) {
            Glide.with(BaseDrawerLayoutActivity.this).load(SharedPreferenceUtils.getString("user_avatar", ""))
                    .into(mUserAvatarImageView);
            mUserInfoTextView.setText(SharedPreferenceUtils.getString("user_name", getString(R.string.please_login)));
        } else {
            Glide.with(BaseDrawerLayoutActivity.this).load(userEvent.getAvatar())
                    .into(mUserAvatarImageView);
            mUserInfoTextView.setText(userEvent.getName());
        }
    }

    protected NavigationView.OnNavigationItemSelectedListener itemSelectedListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            return menuItemChecked(item);
        }
    };

    protected abstract void getMenuHeader(View header);

    /**
     * @param now Now you choose the item
     */
    protected abstract void onMenuItemOnClick(MenuItem now);

    /**
     * set menu item check status
     *
     * @return true to display the item as the selected item
     */
    protected boolean menuItemChecked(MenuItem item) {
//        android.util.Log.d(TAG,"点击了item"+mOld.getItemId());

        /**
         * 如果选择了之前被选中的item
         */
        if (mOld != null && mOld.getItemId() == item.getItemId()) {
            return false;
        }


        /**
         * 被选中的item如果是secondaryGroup这个group里面的item
         * 就是设置和关于这两个item，都是启动一个新的activity
         * 所以不用设置选中状态
         */
        if (item.getGroupId() != R.id.secondaryGroup && item.getItemId() != R.id.drawerSubTitle) {
            if (mOld != null) {
                mOld.setChecked(false);
            }
            mOld = item;
            item.setChecked(true);
        }
        this.mDrawerLayout.closeDrawer(this.mNavigationView);
        this.onMenuItemOnClick(item);
        return true;
    }

    /**
     * Take care of calling onBackPressed() for pre-Eclair platforms.
     *
     * @param keyCode keyCode
     * @param event   event
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && this.mDrawerLayout.isDrawerOpen(this.mNavigationView)) {
            this.mDrawerLayout.closeDrawer(this.mNavigationView);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * @param item The menu item that was selected.
     * @return boolean Return false to allow normal menu processing to
     * proceed, true to consume it here.
     * @see #onCreateOptionsMenu
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.mDrawerLayout.openDrawer(GravityCompat.START);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    /**
     * When using ActionBarDrawerToggle, all DrawerLayout listener methods should be forwarded
     * if the ActionBarDrawerToggle is not used as the DrawerLayout listener directly.
     */
    private class EasyDrawerListener implements DrawerLayout.DrawerListener {
        @Override
        public void onDrawerOpened(View drawerView) {
            BaseDrawerLayoutActivity.this.mActionBarDrawerToggle.onDrawerOpened(drawerView);
            if (BaseDrawerLayoutActivity.this.mActionBarHelper != null) {
                BaseDrawerLayoutActivity.this.mActionBarHelper.onDrawerOpened();
            }
        }

        @Override
        public void onDrawerClosed(View drawerView) {
            BaseDrawerLayoutActivity.this.mActionBarDrawerToggle.onDrawerClosed(drawerView);
            BaseDrawerLayoutActivity.this.mActionBarHelper.onDrawerClosed();
        }

        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {
            BaseDrawerLayoutActivity.this.mActionBarDrawerToggle.onDrawerSlide(drawerView, slideOffset);
        }

        @Override
        public void onDrawerStateChanged(int newState) {
            BaseDrawerLayoutActivity.this.mActionBarDrawerToggle.onDrawerStateChanged(newState);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Sync the toggle state after onRestoreInstanceState has occurred.
        mActionBarDrawerToggle.syncState();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!mUserSubscription.isUnsubscribed()) {
            mUserSubscription.unsubscribe();
        }
        if (!mLogoutSubscription.isUnsubscribed()) {
            mLogoutSubscription.unsubscribe();
        }
    }
}
