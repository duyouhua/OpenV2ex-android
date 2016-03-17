package licrafter.com.v2ex.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import butterknife.ButterKnife;
import licrafter.com.v2ex.util.SharedPreferenceUtils;

/**
 * Created by Administrator on 2016/1/31.
 */
public abstract class BaseAppCompatActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(this.getLayoutId());


        ButterKnife.bind(this);
        SharedPreferenceUtils.init(getApplicationContext());
        this.initToolbar(savedInstanceState);
        this.initData();
        this.initView(savedInstanceState);
        this.initListener();
    }

    /**
     * 设置layout id
     *
     * @return
     */
    protected abstract int getLayoutId();

    protected abstract void initToolbar(Bundle savedInstanceState);

    protected abstract void initView(Bundle savedInstanceState);

    protected abstract void initData();

    protected abstract void initListener();

    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
