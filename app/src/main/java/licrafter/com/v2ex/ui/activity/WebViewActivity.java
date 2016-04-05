package licrafter.com.v2ex.ui.activity;

import android.os.Bundle;

import butterknife.Bind;
import licrafter.com.v2ex.R;
import licrafter.com.v2ex.base.BaseToolbarActivity;
import licrafter.com.v2ex.ui.widget.LJWebView;

/**
 * author: shell
 * date 16/4/3 下午7:34
 **/
public class WebViewActivity extends BaseToolbarActivity {

    @Bind(R.id.webContent)
    LJWebView webContent;

    private String url;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    protected void attachVeiw() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        if (getIntent().getStringExtra("url") != null) {
            url = getIntent().getStringExtra("url");
            webContent.loadUrl(url);
        }
    }

    @Override
    public void onBackPressed() {
        if (webContent.canGoBack()) {
            webContent.goBack();
        } else {
            finish();
        }
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
