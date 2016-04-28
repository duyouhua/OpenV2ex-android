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
    LJWebView mWebContent;
    @Bind(R.id.swipe_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private String mUrl;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    protected void attachVeiw() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        CustomUtil.initStyle(mSwipeRefreshLayout);
        if (getIntent().getStringExtra("mUrl") != null) {
            mSwipeRefreshLayout.setRefreshing(true);
            mUrl = getIntent().getStringExtra("mUrl");
            mWebContent.setWebChromeClient(chromeClient);
            mWebContent.loadUrl(mUrl);
        }
    }

    WebChromeClient chromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (view.getProgress() == 100) {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            android.util.Log.d("ljx",view.getTitle());
            mActionBarHelper.setTitle(view.getTitle());
        }
    };

    @Override
    public void onBackPressed() {
        if (mWebContent.canGoBack()) {
            mWebContent.goBack();
        } else {
            finish();
        }
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initListener() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mWebContent.loadUrl(mUrl);
            }
        });
        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mWebContent.canGoBack()) {
                    mWebContent.goBack();
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
