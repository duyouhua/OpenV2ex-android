package licrafter.com.v2ex.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import licrafter.com.v2ex.R;
import licrafter.com.v2ex.api.Server;
import licrafter.com.v2ex.model.request.LoginBody;
import licrafter.com.v2ex.util.CustomUtil;
import licrafter.com.v2ex.util.JsoupUtil;
import licrafter.com.v2ex.ui.widget.CustomProgressbarDialog;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by shell on 15-11-15.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.input_name)
    EditText input_name;
    @Bind(R.id.input_pwd)
    EditText input_pwd;
    @Bind(R.id.btn_submit)
    TextView btn_submit;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private CustomProgressbarDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        input_name.setText("1057645164@qq.com");
        input_pwd.setText("149162536max");
        dialog = CustomUtil.getCustomProgressDialog("登陆中...");
        toolbar.setTitle("登陆");
        setSupportActionBar(toolbar);
        btn_submit.setOnClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                if (!isFormClean()) {
                    requestOnce(input_name.getText().toString(), input_pwd.getText().toString());
                } else {
                    Toast.makeText(this, "请输入用户名和密码", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void requestOnce(final String name, final String pwd) {
        dialog.show(getSupportFragmentManager(), "login");
        Callback<Response> callback = new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                try {
                    String body = CustomUtil.streamFormToString(response.getBody().in());
                    String once = JsoupUtil.parseOnce(body);
                    //登陆
                    login(name, pwd, once);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                android.util.Log.d("ljx", error.toString());
                Toast.makeText(LoginActivity.this, "登陆失败", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        };
        Server.v2EX(this).getOnceString(callback);
    }

    private void login(String username, String pwd, String once) {
        Callback<Response> callback = new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                try {
                    String body = CustomUtil.streamFormToString(response.getBody().in());
                    android.util.Log.d("ljx", CustomUtil.getErrorMsg(body));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                android.util.Log.d("ljx", "error" + error.toString());
                Toast.makeText(LoginActivity.this, "登陆失败", Toast.LENGTH_SHORT).show();
            }
        };
        LoginBody body = new LoginBody();
        body.setNext("/");
        body.setU(username);
        body.setP(pwd);
        body.setOnce(once);
        Server.login(this).login(username, pwd, once, "/", callback);
    }

    private boolean isFormClean() {
        if (!TextUtils.isEmpty(input_name.getText()) && !TextUtils.isEmpty(input_pwd.getText())) {
            return false;
        }
        return true;
    }
}
