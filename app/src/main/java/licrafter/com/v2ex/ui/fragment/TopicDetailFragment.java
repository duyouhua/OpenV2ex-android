package licrafter.com.v2ex.ui.fragment;/**
 * Created by Administrator on 2016/3/26.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.Bind;
import licrafter.com.v2ex.BaseApplication;
import licrafter.com.v2ex.R;
import licrafter.com.v2ex.base.BaseFragment;
import licrafter.com.v2ex.event.FavoriteEvent;
import licrafter.com.v2ex.model.Topic;
import licrafter.com.v2ex.model.TopicDetail;
import licrafter.com.v2ex.mvp.presenters.TopicDetailPresenter;
import licrafter.com.v2ex.mvp.views.MvpView;
import licrafter.com.v2ex.ui.activity.LoginActivity;
import licrafter.com.v2ex.ui.activity.TopicDetailActivity;
import licrafter.com.v2ex.util.CustomUtil;
import licrafter.com.v2ex.util.RxBus;
import licrafter.com.v2ex.util.network.TokenCache;
import rx.Subscription;
import rx.functions.Action1;

/**
 * author: lijinxiang
 * date: 2016/3/26
 **/
public class TopicDetailFragment extends BaseFragment implements MvpView {

    private Topic topic;

    @Bind(R.id.iv_avatar)
    RoundedImageView mAvatarView;
    @Bind(R.id.tv_username)
    TextView mUserNameView;
    @Bind(R.id.tv_create_time)
    TextView mCreatTimeView;
    @Bind(R.id.tv_title)
    TextView mTitleView;
    @Bind(R.id.tv_content)
    WebView mcontentWebView;
    @Bind(R.id.detail_ScrollView)
    NestedScrollView mDetailScrollView;

    private TopicDetailPresenter mPresenter;
    private TopicDetail topicDetail;
    private Subscription mFavoriteSubscription;
    private float x, y;

    public static TopicDetailFragment newInstance(Topic topic) {
        TopicDetailFragment fragment = new TopicDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("topic", topic);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            topic = (Topic) getArguments().getSerializable("topic");
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_topic_detail;
    }

    @Override
    protected void attachView() {
        mPresenter = new TopicDetailPresenter();
        mPresenter.attachView(this);
    }

    @Override
    protected void initViews(View view) {
        ((TopicDetailActivity) getActivity()).setAppBarShadow(false);
        ((TopicDetailActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.topic_detail));
        if (topic != null) {
            mTitleView.setText(topic.getTitle());
            Glide.with(this).load(topic.getAvatar()).into(mAvatarView);
            mUserNameView.setText(topic.getUserId());
        }
        initWebView();
    }

    @Override
    protected void setListeners() {
        mFavoriteSubscription = RxBus.getDefault().toObserverable(FavoriteEvent.class)
                .subscribe(new Action1<FavoriteEvent>() {
                    @Override
                    public void call(FavoriteEvent favoriteEvent) {
                        if (BaseApplication.isLogin() && !topicDetail.getCsrfToken().equals("false")) {
                            showLoadingDialog();
                            if (topicDetail.isFravorite()) {
                                mPresenter.unFavoriteTopic(topic.getTopicId(), topicDetail.getCsrfToken());
                            } else {
                                mPresenter.favoriteTopic(topic.getTopicId(), topicDetail.getCsrfToken());
                            }
                        } else {
                            Toast.makeText(getActivity(), getString(R.string.please_login), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getActivity(), LoginActivity.class));
                            getActivity().finish();
                        }
                    }
                });
    }

    @Override
    protected void loadData() {
        if (topicDetail == null) {
            showLoadingDialog();
            mPresenter.getTopicDetail(topic.getTopicId());
        } else {
            parseTopicDetail(topicDetail);
        }
    }

    @Override
    protected void detachView() {
        mPresenter.detachView();
    }

    @SuppressLint("JavascriptInterface")
    public void initWebView() {
        mcontentWebView.setWebViewClient(webViewClient);

        WebSettings webSettings = mcontentWebView.getSettings();
        webSettings.setDisplayZoomControls(false);
        webSettings.setSupportZoom(false);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(false);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        mcontentWebView.addJavascriptInterface(new JavascriptInterface(getActivity()),"imagelistner");
    }

    public void parseTopicDetail(TopicDetail topicDetail) {
        hideLoadingDialog();
        this.topicDetail = topicDetail;
        mCreatTimeView.setText("发布于 " + topicDetail.getCreateTime() + " " + topicDetail.getClickCount());
        ((TopicDetailActivity) getActivity()).setShoucangStatus(topicDetail.isFravorite());
        openWebView(topicDetail.getContent());
    }

    /**
     * http://stackoverflow.com/questions/3099344/can-androids-webview-automatically-resize-huge-images
     *
     * @param data
     */
    @SuppressLint("NewApi")
    private void openWebView(String data) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mcontentWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        } else {
            mcontentWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        }
        mcontentWebView.loadDataWithBaseURL("file:///android_asset/", getHtmlData(data), "text/html", "utf-8", null);
    }

    private String getHtmlData(String bodyHTML) {
        String head = "<head><style>img{max-width: 80%; width:auto; height: auto;}</style></head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }

    @Override
    public void onFailure(String e) {
        hideLoadingDialog();
        mcontentWebView.loadData(e, "text/html; charset=UTF-8", null);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (!mFavoriteSubscription.isUnsubscribed()) {
            mFavoriteSubscription.unsubscribe();
        }
    }

    public void parseFavorite(String token, boolean isFavorite) {
        hideLoadingDialog();
        Toast.makeText(getActivity(), "操作成功", Toast.LENGTH_SHORT).show();
        topicDetail.setCsrfToken(token);
        topicDetail.setFravorite(isFavorite);
        ((TopicDetailActivity) getActivity()).setShoucangStatus(isFavorite);
    }

    private WebViewClient webViewClient = new WebViewClient() {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.contains(".jpg")) {
                Toast.makeText(getContext(), "图片url = " + url, Toast.LENGTH_LONG).show();
            } else {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            addImageClickListner();
        }

        public void onLoadResource(WebView view, String url) {
            //android.util.Log.d("ljx","url = "+url);
            super.onLoadResource(view, url);
        }
    };

    // 注入js函数监听
    private void addImageClickListner() {
        // 这段js函数的功能就是，遍历所有的img几点，并添加onclick函数，函数的功能是在图片点击的时候调用本地java接口并传递url过去
        mcontentWebView.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName(\"img\"); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "    objs[i].onclick=function()  " +
                "    {  "
                + "        window.imagelistner.openImage(this.src);  " +
                "    }  " +
                "}" +
                "})()");
    }

    public class JavascriptInterface{
        private Context context;

        public JavascriptInterface(Context context){
            this.context = context;
        }

        public void openImage(String img){
            android.util.Log.d("ljx","url = "+img);
        }
    }

    private void addCookie() {
        if (BaseApplication.isLogin()) {
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            cookieManager.setCookie("https://www.v2ex.com", "token=" + TokenCache.getToken());
        }
    }

}
