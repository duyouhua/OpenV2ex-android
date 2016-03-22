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
import licrafter.com.v2ex.R;
import licrafter.com.v2ex.ui.activity.LoginActivity;
import licrafter.com.v2ex.util.Constant;
import licrafter.com.v2ex.util.SharedPreferenceUtils;

/**
 * Created by Administrator on 2016/1/31.
 */
public abstract class BaseDrawerLayoutActivity extends BaseToolbarActivity {

    public final String TAG = this.getClass().getName();

    @Bind(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @Bind(R.id.navigationView)
    NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    private View headerView;

    protected MenuItem old = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.navigationView.setNavigationItemSelectedListener(itemSelectedListener);
        this.drawerLayout.setDrawerListener(new EasyDrawerListener());
        Menu menu = navigationView.getMenu();
        old = menu.findItem(R.id.topicDrawerMenuItem);
        this.actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                this.drawerLayout,
                R.string.action_menu,
                R.string.app_name
        );
        headerView = navigationView.getHeaderView(0);
        TextView userInfoTextView = (TextView) headerView.findViewById(R.id.userNameTextView);
        userInfoTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BaseDrawerLayoutActivity.this, LoginActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (SharedPreferenceUtils.contains("user_name") && SharedPreferenceUtils.contains("user_avatar")) {
            Glide.with(this).load(SharedPreferenceUtils.getString("user_avatar", ""))
                    .centerCrop().into((ImageView) headerView.findViewById(R.id.userAvatarImageView));
            ((TextView) headerView.findViewById(R.id.userNameTextView)).setText(SharedPreferenceUtils.getString("user_name", "请登录"));
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
//        android.util.Log.d(TAG,"点击了item"+old.getItemId());

        /**
         * 如果选择了之前被选中的item
         */
        if (old != null && old.getItemId() == item.getItemId()) {
            return false;
        }


        /**
         * 被选中的item如果是secondaryGroup这个group里面的item
         * 就是设置和关于这两个item，都是启动一个新的activity
         * 所以不用设置选中状态
         */
        if (item.getGroupId() != R.id.secondaryGroup && item.getItemId() != R.id.drawerSubTitle) {
            if (old != null) {
                old.setChecked(false);
            }
            old = item;
            item.setChecked(true);
        }
        this.drawerLayout.closeDrawer(this.navigationView);
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
        if (keyCode == KeyEvent.KEYCODE_BACK && this.drawerLayout.isDrawerOpen(this.navigationView)) {
            this.drawerLayout.closeDrawer(this.navigationView);
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
            this.drawerLayout.openDrawer(GravityCompat.START);
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
            BaseDrawerLayoutActivity.this.actionBarDrawerToggle.onDrawerOpened(drawerView);
            if (BaseDrawerLayoutActivity.this.actionBarHelper != null) {
                BaseDrawerLayoutActivity.this.actionBarHelper.onDrawerOpened();
            }
        }

        @Override
        public void onDrawerClosed(View drawerView) {
            BaseDrawerLayoutActivity.this.actionBarDrawerToggle.onDrawerClosed(drawerView);
            BaseDrawerLayoutActivity.this.actionBarHelper.onDrawerClosed();
        }

        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {
            BaseDrawerLayoutActivity.this.actionBarDrawerToggle.onDrawerSlide(drawerView, slideOffset);
        }

        @Override
        public void onDrawerStateChanged(int newState) {
            BaseDrawerLayoutActivity.this.actionBarDrawerToggle.onDrawerStateChanged(newState);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Sync the toggle state after onRestoreInstanceState has occurred.
        actionBarDrawerToggle.syncState();
    }

}
