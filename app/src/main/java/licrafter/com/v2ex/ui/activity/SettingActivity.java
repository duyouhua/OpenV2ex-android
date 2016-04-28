package licrafter.com.v2ex.ui.activity;/**
 * Created by Administrator on 2016/3/23.
 */

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import butterknife.Bind;
import licrafter.com.v2ex.R;
import licrafter.com.v2ex.api.service.AuthService;
import licrafter.com.v2ex.base.BaseToolbarActivity;
import licrafter.com.v2ex.event.LogoutEvent;
import licrafter.com.v2ex.util.RxBus;
import licrafter.com.v2ex.util.SharedPreferenceUtils;

/**
 * author: lijinxiang
 * date: 2016/3/23
 **/
public class SettingActivity extends BaseToolbarActivity {

    @Bind(R.id.logoutBtn)
    Button mLogoutBtn;

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
        mLogoutBtn.setOnClickListener(onClickListener);
    }

    @Override
    protected void detachView() {

    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.logoutBtn:
                    logOut();
                    break;
            }
        }
    };

    private void logOut() {
        AuthService.getInstance().clearCookies();
        SharedPreferenceUtils.clearAll();
        RxBus.getDefault().post(new LogoutEvent());
        Toast.makeText(this, getString(R.string.logout_success), Toast.LENGTH_LONG).show();
        finish();
    }
}
