package licrafter.com.v2ex.ui.activity;/**
 * Created by Administrator on 2016/3/23.
 */

import android.os.Bundle;

import licrafter.com.v2ex.R;
import licrafter.com.v2ex.base.BaseToolbarActivity;

/**
 * author: lijinxiang
 * date: 2016/3/23
 **/
public class SettingActivity extends BaseToolbarActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void attachVeiw() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        getSupportActionBar().setTitle(getString(R.string.setting));
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
}
