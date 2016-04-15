package licrafter.com.v2ex.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import butterknife.Bind;
import licrafter.com.v2ex.R;
import licrafter.com.v2ex.base.BaseToolbarActivity;
import licrafter.com.v2ex.ui.widget.LJWebView;
import licrafter.com.v2ex.util.CustomUtil;

/**
 * author: shell
 * date 16/4/3 下午7:34
 **/
public class WebViewActivity extends BaseToolbarActivity {

    @Bind(R.id.webContent)
    LJWebView webContent;
    @Bind(R.id.swipe_layout)
    SwipeRefreshLayout swipeRefreshLayout;

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
        CustomUtil.initStyle(swipeRefreshLayout);
        if (getIntent().getStringExtra("url") != null) {
            swipeRefreshLayout.setRefreshing(true);
            url = getIntent().getStringExtra("url");
            webContent.setWebChromeClient(chromeClient);
            webContent.loadUrl(url);
        }
    }

    WebChromeClient chromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (view.getProgress() == 100) {
                swipeRefreshLayout.setRefreshing(false);
            }
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            android.util.Log.d("ljx",view.getTitle());
            actionBarHelper.setTitle(view.getTitle());
        }
    };

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
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                webContent.loadUrl(url);
            }
        });
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (webContent.canGoBack()) {
                    webContent.goBack();
                } else {
                    finish();
                }
            }
        });

    }

    @Override
    protected void detachView() {

    }
}
