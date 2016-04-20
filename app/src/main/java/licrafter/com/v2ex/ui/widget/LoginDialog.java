package licrafter.com.v2ex.ui.widget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import licrafter.com.v2ex.BaseApplication;
import licrafter.com.v2ex.R;
import licrafter.com.v2ex.event.UserEvent;
import licrafter.com.v2ex.model.LoginResult;
import licrafter.com.v2ex.mvp.presenters.LoginPresenter;
import licrafter.com.v2ex.mvp.views.MvpView;
import licrafter.com.v2ex.util.RxBus;
import licrafter.com.v2ex.util.SharedPreferenceUtils;

/**
 * author: shell
 * date 16/4/20 下午4:01
 **/
public class LoginDialog extends Dialog implements View.OnClickListener, MvpView {

    @Bind(R.id.pswInputLayout)
    TextInputLayout pswInputLayout;
    @Bind(R.id.usernameInputLayout)
    TextInputLayout usernameInputLayuot;
    @Bind(R.id.input_name)
    EditText inputName;
    @Bind(R.id.input_pwd)
    EditText inputPwd;
    @Bind(R.id.loginBtn)
    TextView loginBtn;
    @Bind(R.id.loginProgressbar)
    ProgressBar loginProgressbar;
    @Bind(R.id.registerBtn)
    TextView registerBtn;
    @Bind(R.id.loginFrameLayout)
    FrameLayout loginFrameLayout;

    private boolean isNameEmpty = true;
    private boolean isPswEmpty = true;
    private String username;
    private String psw;
    private LoginPresenter presenter;

    public LoginDialog(Context context) {
        super(context);
        View rootView = LayoutInflater.from(context).inflate(R.layout.dialog_login, null);
        setContentView(rootView);
        ButterKnife.bind(this);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(lp);
        presenter = new LoginPresenter();
        presenter.attachView(this);
        inputName.setOnClickListener(this);
        inputPwd.setOnClickListener(this);
        inputName.addTextChangedListener(nameTextWatcher);
        inputPwd.addTextChangedListener(pswTextWatcher);
        loginBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
    }


    TextWatcher nameTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (count == 0) {
                isNameEmpty = true;
            } else {
                isNameEmpty = false;
            }
            loginEnable();
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    TextWatcher pswTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (count == 0) {
                isPswEmpty = true;
            } else {
                isPswEmpty = false;
            }
            loginEnable();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private void loginEnable() {
        if (!isNameEmpty && !isPswEmpty) {
            loginBtn.setTextColor(getContext().getResources().getColor(R.color.teal500));
            loginBtn.setClickable(true);
        } else {
            loginBtn.setTextColor(getContext().getResources().getColor(R.color.teal100));
            loginBtn.setClickable(false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginBtn:
                android.util.Log.d("ljx", "click");
                if (!isFormClean()) {
                    showDialog(true);
                    presenter.login(inputName.getText().toString(), inputName.getText().toString());
                } else {
                    Toast.makeText(getContext(), "请输入用户名和密码", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onFailure(String e) {
        showDialog(false);
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        presenter.detachView();
    }

    public void onLoginSuccess(LoginResult result) {
        showDialog(false);
        if (result.getMessage() == null) {
            BaseApplication.setLogin(true);
            SharedPreferenceUtils.save("user_name", result.getUserId());
            SharedPreferenceUtils.save("user_avatar", result.getUserAvatar());
            RxBus.getDefault().post(new UserEvent(result.getUserId(), result.getUserAvatar()));
            Toast.makeText(getContext(), "欢迎  " + result.getUserId() + "  登录客户端", Toast.LENGTH_LONG).show();
            SharedPreferenceUtils.save("user_name", result.getUserId());
            SharedPreferenceUtils.save("user_avatar", result.getUserAvatar());
            loginSuccess();
            dismiss();
        } else {
            BaseApplication.setLogin(false);
            Toast.makeText(getContext(), result.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private boolean isFormClean() {
        if (!TextUtils.isEmpty(inputName.getText()) && !TextUtils.isEmpty(inputPwd.getText())) {
            return false;
        }
        return true;
    }

    private void loginSuccess() {

    }

    private void showDialog(boolean show) {
        loginProgressbar.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
        loginBtn.setVisibility(show ? View.INVISIBLE : View.VISIBLE);
    }

}
