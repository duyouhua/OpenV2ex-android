package licrafter.com.v2ex;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import licrafter.com.v2ex.base.BaseDrawerLayoutActivity;
import licrafter.com.v2ex.ui.activity.SettingActivity;
import licrafter.com.v2ex.ui.activity.TopicEditActivity;
import licrafter.com.v2ex.ui.fragment.CategoryFragment;
import licrafter.com.v2ex.ui.fragment.NodeListFragment;
import licrafter.com.v2ex.util.FragmentUtil;

/**
 * author: lijinxiang
 * date: 2016/3/17
 **/
public class MainActivity extends BaseDrawerLayoutActivity {


    private Handler handler;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void attachVeiw() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        handler = new Handler();
        switchFragment(CategoryFragment.newInstance());
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void detachView() {

    }

    @Override
    protected void onMenuItemOnClick(MenuItem now) {
        switch (now.getItemId()) {
            case R.id.topicDrawerMenuItem:
                switchFragment(CategoryFragment.newInstance());
                actionBarHelper.setTitle(getString(R.string.app_name));
                break;
            case R.id.nodeDrawerMenuItem:
                switchFragment(NodeListFragment.newInstance());
                actionBarHelper.setTitle(getString(R.string.node_list));
                break;
            case R.id.settingDrawerMenuItem:
                startActivity(new Intent(this, SettingActivity.class));
            default:
                break;
        }
    }

    @Override
    protected void getMenuHeader(View header) {
        if (header != null) {

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, TopicEditActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void switchFragment(final Fragment fragment) {
        //延时200ms跳转fragment，减缓drawerlayout卡顿现象
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean show = fragment instanceof CategoryFragment;
                setAppBarShadow(!show);
                FragmentUtil.replaceWithAnim(getSupportFragmentManager(), R.id.containerFrameLayout
                        , fragment, false, "");
                handler.removeCallbacks(this);
            }
        }, 200);

    }
}
