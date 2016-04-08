package licrafter.com.v2ex.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.Toast;

import licrafter.com.v2ex.BaseApplication;
import licrafter.com.v2ex.R;
import licrafter.com.v2ex.util.network.TokenCache;

/**
 * author: shell
 * date 16/4/3 下午6:34
 **/
public class LJWebView extends WebView {

    // private WebView mWebView;

    public LJWebView(Context context) {
        this(context, null);
    }

    public LJWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LJWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        initWebView();
    }

    @SuppressLint("JavascriptInterface")
    private void initWebView() {
        addCookie();
        this.setWebViewClient(webViewClient);
        WebSettings webSettings = getSettings();
        webSettings.setDisplayZoomControls(false);
        webSettings.setSupportZoom(false);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(false);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
    }

    /**
     * http://stackoverflow.com/questions/3099344/can-androids-webview-automatically-resize-huge-images
     *
     * @param data
     */
    @SuppressLint("NewApi")
    public void loadHtml(String data) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        } else {
            getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        }
        loadDataWithBaseURL("file:///android_asset/", getHtmlData(data), "text/html", "utf-8", null);
    }

    private String getHtmlData(String bodyHTML) {
        String head = "<head><style>img{max-width: 80%; width:auto; height: auto;}</style></head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }

    private WebViewClient webViewClient = new WebViewClient() {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.contains(".jpg")) {
                Toast.makeText(getContext(), "图片url = " + url, Toast.LENGTH_LONG).show();
            } else if (url.contains("www.v2ex.com")) {
                view.loadUrl(url);
            } else {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                getContext().startActivity(intent);
            }
            return true;
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        addCookie();
    }

    private void addCookie() {
        if (BaseApplication.isLogin()) {
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
        }
    }
}
