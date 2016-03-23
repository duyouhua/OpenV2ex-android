package licrafter.com.v2ex.mvp.presenters;/**
 * Created by Administrator on 2016/3/23.
 */

import licrafter.com.v2ex.api.service.LoginService;
import licrafter.com.v2ex.ui.activity.TopicEditActivity;
import licrafter.com.v2ex.util.JsoupUtil;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * author: lijinxiang
 * date: 2016/3/23
 **/
public class TopicEditPresenter extends BasePresenter<TopicEditActivity> {

    public void postTopic(final String title, final String content, final String nodeId) {
        compositeSubscription.add(LoginService.getInstance().login()
                .getOnceString()
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String response) {
                        return JsoupUtil.parseOnce(response);
                    }
                }).flatMap(new Func1<String, Observable<String>>() {
                    @Override
                    public Observable<String> call(String once) {
                        return LoginService.getInstance().login()
                                .newTopic(title, content, nodeId, once);
                    }
                })
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String response) {
                        return response;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        android.util.Log.d("ljx", "创建结束");
                    }
                }));
    }
}
