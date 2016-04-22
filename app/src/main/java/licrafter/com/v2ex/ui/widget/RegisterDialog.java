package licrafter.com.v2ex.ui.widget;/**
 * Created by Administrator on 2016/4/21.
 */

import android.app.Dialog;
import android.content.Context;
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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import licrafter.com.v2ex.BaseApplication;
import licrafter.com.v2ex.R;
import licrafter.com.v2ex.event.UserEvent;
import licrafter.com.v2ex.model.LoginResult;
import licrafter.com.v2ex.model.response.RegFormInfo;
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
    RoundedImageView codeImage;
    @Bind(R.id.regBtn)
    TextView registerBtn;
    @Bind(R.id.regFrameLayout)
    FrameLayout regFrameLayout;
    @Bind(R.id.v_code_background)
    View mCodeBackground;

    private boolean isNameEmpty = true;
    private boolean isPswEmpty = true;
    private boolean isEmailEmpty = true;
    private boolean isCodeEmpty = true;
    private LoginPresenter presenter;
    private OnRegisterListener listener;
    private RegFormInfo formInfo;

    public RegisterDialog(Context context) {
        super(context);
        View rootView = LayoutInflater.from(context).inflate(R.layout.dialog_register, null);
        setContentView(rootView);
        ButterKnife.bind(this);
        codeImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(lp);

        presenter = new LoginPresenter();
        inputName.addTextChangedListener(new InputTextWatcher(inputName));
        inputPwd.addTextChangedListener(new InputTextWatcher(inputPwd));
        inputEmail.addTextChangedListener(new InputTextWatcher(inputEmail));
        inputCode.addTextChangedListener(new InputTextWatcher(inputCode));
        registerBtn.setOnClickListener(this);
    }

    private void refreshRegButtonStatus() {
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
            case R.id.regBtn:
                showRegProgressbar(true);
                formInfo.setNameValue(inputName.getText().toString());
                formInfo.setPswValue(inputPwd.getText().toString());
                formInfo.setEmailValue(inputEmail.getText().toString());
                formInfo.setCodeValue(inputCode.getText().toString());
                presenter.register(formInfo);
                showRegProgressbar(true);
                break;
        }
    }

    @Override
    public void onFailure(String e) {
        showRegProgressbar(false);
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

    public void parseRegCode(RegFormInfo formInfo) {
        this.formInfo = formInfo;
        presenter.getCodeImage(formInfo.getOnce());
    }

    public void parseCodeImage(File file) {
        hideCodeProgressbar();
        Glide.with(getContext()).load(file).diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(codeImage);
    }

    public void parseRegError(String error) {
        showRegProgressbar(false);
        if (error != null) {
            Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
            presenter.getCodeImage(formInfo.getOnce());
        } else {
            Toast.makeText(getContext(), "注册成功", Toast.LENGTH_SHORT).show();
            LoginDialog loginDialog = new LoginDialog(getContext());
            dismiss();
            loginDialog.show();
        }
    }

    private void showRegProgressbar(boolean show) {
        regProgressbar.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
        registerBtn.setVisibility(show ? View.INVISIBLE : View.VISIBLE);
    }

    private void hideCodeProgressbar() {
        mCodeBackground.setVisibility(View.INVISIBLE);
        codeProgerssbar.setVisibility(View.INVISIBLE);
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
                    isCodeEmpty = value;
                    break;
                case R.id.input_email:
                    isEmailEmpty = value;
                    break;
                case R.id.input_name:
                    isNameEmpty = value;
                    break;
                case R.id.input_pwd:
                    isPswEmpty = value;
                    break;
            }
            refreshRegButtonStatus();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    public interface OnRegisterListener {
        public void onRegisterSuccess();

        public void onRegisterFailed();
    }

}
