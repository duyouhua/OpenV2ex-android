package licrafter.com.v2ex.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import butterknife.ButterKnife;
import licrafter.com.v2ex.ui.widget.ProgressbarDialog;
import licrafter.com.v2ex.util.SharedPreferenceUtils;

/**
 * Created by Administrator on 2016/1/31.
 */
public abstract class BaseAppCompatActivity extends AppCompatActivity {

    private ProgressbarDialog progressbarDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(this.getLayoutId());


        ButterKnife.bind(this);
        SharedPreferenceUtils.init(getApplicationContext());
        progressbarDialog = new ProgressbarDialog();
        this.initToolbar(savedInstanceState);
        this.attachVeiw();
        this.initView(savedInstanceState);
        this.initListener();
        this.loadData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.detachView();
    }

    protected void showDialog() {
        progressbarDialog.show(getSupportFragmentManager(), "");
    }

    protected void hideDialog() {
        if (!progressbarDialog.isHidden()) {
            progressbarDialog.dismiss();
        }
    }

    /**
     * 设置layout id
     *
     * @return
     */
    protected abstract int getLayoutId();

    protected abstract void initToolbar(Bundle savedInstanceState);

    protected abstract void attachVeiw();

    protected abstract void initView(Bundle savedInstanceState);

    protected abstract void loadData();

    protected abstract void initListener();

    protected abstract void detachView();

    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
