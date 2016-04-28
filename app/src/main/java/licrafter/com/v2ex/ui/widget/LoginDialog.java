package licrafter.com.v2ex.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
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
    TextInputLayout mPswInputLayout;
    @Bind(R.id.usernameInputLayout)
    TextInputLayout mUsernameInputLayuot;
    @Bind(R.id.input_name)
    EditText mInputName;
    @Bind(R.id.input_pwd)
    EditText mInputPwd;
    @Bind(R.id.loginBtn)
    TextView mLoginBtn;
    @Bind(R.id.loginProgressbar)
    ProgressBar mLoginProgressbar;
    @Bind(R.id.registerBtn)
    TextView mRegisterBtn;
    @Bind(R.id.loginFrameLayout)
    FrameLayout mLoginFrameLayout;

    private boolean mIsNameEmpty = true;
    private boolean mIsPswEmpty = true;
    private LoginPresenter mPresenter;
    private OnLoginListener mListener;

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
        mPresenter = new LoginPresenter();
        mInputName.setOnClickListener(this);
        mInputPwd.setOnClickListener(this);
        mInputName.addTextChangedListener(nameTextWatcher);
        mInputPwd.addTextChangedListener(pswTextWatcher);
        mLoginBtn.setOnClickListener(this);
        mRegisterBtn.setOnClickListener(this);
    }


    TextWatcher nameTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (mInputName.getText().length() == 0) {
                mIsNameEmpty = true;
            } else {
                mIsNameEmpty = false;
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
            if (mInputPwd.getText().length() == 0) {
                mIsPswEmpty = true;
            } else {
                mIsPswEmpty = false;
            }
            loginEnable();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private void loginEnable() {
        if (!mIsNameEmpty && !mIsPswEmpty) {
            mLoginBtn.setTextColor(getContext().getResources().getColor(R.color.teal500));
            mLoginBtn.setClickable(true);
        } else {
            mLoginBtn.setTextColor(getContext().getResources().getColor(R.color.teal100));
            mLoginBtn.setClickable(false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginBtn:
                if (!isFormClean()) {
                    showDialog(true);
                    mPresenter.login(mInputName.getText().toString(), mInputPwd.getText().toString());
                } else {
                    Toast.makeText(getContext(), "请输入用户名和密码", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.registerBtn:
                dismiss();
                RegisterDialog registerDialog = new RegisterDialog(getContext());
                registerDialog.show();
                break;
        }
    }

    @Override
    public void onFailure(String e) {
        showDialog(false);
        if (mListener != null) {
            mListener.onLoginFailed();
        }
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        mPresenter.attachView(this);
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mPresenter.detachView();
    }

    public void setOnLoginListener(OnLoginListener listener) {
        this.mListener = listener;
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
            if (mListener != null) {
                mListener.onLoginSuccess();
            }
            dismiss();
        } else {
            BaseApplication.setLogin(false);
            Toast.makeText(getContext(), result.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private boolean isFormClean() {
        if (!TextUtils.isEmpty(mInputName.getText()) && !TextUtils.isEmpty(mInputPwd.getText())) {
            return false;
        }
        return true;
    }

    public interface OnLoginListener {
        public void onLoginSuccess();

        public void onLoginFailed();
    }

    private void showDialog(boolean show) {
        mLoginProgressbar.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
        mLoginBtn.setVisibility(show ? View.INVISIBLE : View.VISIBLE);
    }

}
