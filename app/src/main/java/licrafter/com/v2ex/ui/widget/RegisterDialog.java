package licrafter.com.v2ex.ui.widget;/**
 * Created by Administrator on 2016/4/21.
 */

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.design.widget.TextInputEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.stream.StreamModelLoader;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

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
 * author: lijinxiang
 * date: 2016/4/21
 **/
public class RegisterDialog extends Dialog implements View.OnClickListener, MvpView {

    @Bind(R.id.input_name)
    TextInputEditText inputName;
    @Bind(R.id.input_pwd)
    TextInputEditText inputPwd;
    @Bind(R.id.input_email)
    TextInputEditText inputEmail;
    @Bind(R.id.input_code)
    TextInputEditText inputCode;
    @Bind(R.id.regProgressbar)
    ProgressBar regProgressbar;
    @Bind(R.id.codeProgressbar)
    ProgressBar codeProgerssbar;
    @Bind(R.id.codeImage)
    ImageView codeImage;
    @Bind(R.id.regBtn)
    TextView registerBtn;
    @Bind(R.id.regFrameLayout)
    FrameLayout regFrameLayout;

    private boolean isNameEmpty = true;
    private boolean isPswEmpty = true;
    private boolean isEmailEmpty = true;
    private boolean isCodeEmpty = true;
    private LoginPresenter presenter;
    private OnRegisterListener listener;
    private String once;

    public RegisterDialog(Context context) {
        super(context);
        View rootView = LayoutInflater.from(context).inflate(R.layout.dialog_register, null);
        setContentView(rootView);
        ButterKnife.bind(this);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(lp);

//        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//        Display display = wm.getDefaultDisplay();
//        DisplayMetrics metrics = new DisplayMetrics();
//        display.getMetrics(metrics);
//        Double width = metrics.widthPixels*.9;
//        Double height = width*.9;
//        Window win = getWindow();
//        win.setLayout(width.intValue(), height.intValue());

        presenter = new LoginPresenter();
        inputName.addTextChangedListener(new InputTextWatcher(inputName));
        inputPwd.addTextChangedListener(new InputTextWatcher(inputPwd));
        inputEmail.addTextChangedListener(new InputTextWatcher(inputEmail));
        inputCode.addTextChangedListener(new InputTextWatcher(inputCode));
        registerBtn.setOnClickListener(this);
    }

    private void regEnable() {
        if (!isNameEmpty && !isPswEmpty && !isEmailEmpty && !isCodeEmpty) {
            registerBtn.setTextColor(getContext().getResources().getColor(R.color.teal500));
            registerBtn.setClickable(true);
        } else {
            registerBtn.setTextColor(getContext().getResources().getColor(R.color.teal100));
            registerBtn.setClickable(false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginBtn:
                if (!isFormClean()) {
                    showDialog(true);
                    presenter.login(inputName.getText().toString(), inputPwd.getText().toString());
                } else {
                    Toast.makeText(getContext(), "请输入用户名和密码", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onFailure(String e) {
        showDialog(false);
        if (listener != null) {
            listener.onRegisterFailed();
        }
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        presenter.attachView(this);
        presenter.getRegisterCode();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        presenter.detachView();
    }

    public void setOnRegisterListener(OnRegisterListener listener) {
        this.listener = listener;
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
            if (listener != null) {
                listener.onRegisterSuccess();
            }
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

    public void parseRegCode(String once) {
        codeProgerssbar.setVisibility(View.INVISIBLE);
        this.once = once;
        presenter.getCodeImage(once);
    }

    public void parseCodeImage(File file) {
        Glide.with(getContext()).load(file).into(codeImage);
    }

    public interface OnRegisterListener {
        public void onRegisterSuccess();

        public void onRegisterFailed();
    }

    private void showDialog(boolean show) {
        regProgressbar.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
        registerBtn.setVisibility(show ? View.INVISIBLE : View.VISIBLE);
    }

    private class InputTextWatcher implements TextWatcher {

        private TextInputEditText editText;

        public InputTextWatcher(TextInputEditText editText) {
            this.editText = editText;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            boolean value = editText.getText().length() == 0;
            switch (editText.getId()) {
                case R.id.input_code:
                    setStatus(isCodeEmpty, value);
                    break;
                case R.id.input_email:
                    setStatus(isEmailEmpty, value);
                    break;
                case R.id.input_name:
                    setStatus(isNameEmpty, value);
                    break;
                case R.id.input_pwd:
                    setStatus(isPswEmpty, value);
                    break;
            }
            regEnable();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }

        private void setStatus(boolean key, boolean value) {
            key = value;
        }
    }

}
