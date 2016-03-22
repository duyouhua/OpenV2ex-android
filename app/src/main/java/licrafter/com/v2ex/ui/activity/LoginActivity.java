package licrafter.com.v2ex.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import licrafter.com.v2ex.R;
import licrafter.com.v2ex.base.BaseToolbarActivity;
import licrafter.com.v2ex.model.LoginResult;
import licrafter.com.v2ex.mvp.presenters.LoginPresenter;
import licrafter.com.v2ex.mvp.views.LoginView;
import licrafter.com.v2ex.mvp.views.MvpView;
import licrafter.com.v2ex.util.CustomUtil;
import licrafter.com.v2ex.ui.widget.CustomProgressbarDialog;
import licrafter.com.v2ex.util.SharedPreferenceUtils;

/**
 * Created by shell on 15-11-15.
 */
public class LoginActivity extends BaseToolbarActivity implements View.OnClickListener, LoginView {

    @Bind(R.id.input_name)
    EditText input_name;
    @Bind(R.id.input_pwd)
    EditText input_pwd;
    @Bind(R.id.btn_submit)
    Button btn_submit;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private CustomProgressbarDialog dialog;
    private LoginPresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void attachVeiw() {
        mPresenter = new LoginPresenter();
        mPresenter.attachView(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        input_name.setText("lijinxiang");
        input_pwd.setText("149162536max");
        dialog = CustomUtil.getCustomProgressDialog("登陆中...");
        toolbar.setTitle("登陆");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initListener() {
        btn_submit.setOnClickListener(this);
    }

    @Override
    protected void detachView() {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                if (!isFormClean()) {
                    mPresenter.login(input_name.getText().toString(), input_pwd.getText().toString());
                } else {
                    Toast.makeText(this, "请输入用户名和密码", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public void onLoginSuccess(LoginResult result){
        SharedPreferenceUtils.save("user_name",result.getUserId());
        SharedPreferenceUtils.save("user_avatar",result.getUserAvatar());
    }

    private boolean isFormClean() {
        if (!TextUtils.isEmpty(input_name.getText()) && !TextUtils.isEmpty(input_pwd.getText())) {
            return false;
        }
        return true;
    }

    @Override
    public void onFailure(Throwable e) {

    }
}
