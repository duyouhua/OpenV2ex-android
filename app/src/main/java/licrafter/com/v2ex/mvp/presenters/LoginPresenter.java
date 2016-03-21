package licrafter.com.v2ex.mvp.presenters;/**
 * Created by Administrator on 2016/3/21.
 */

import licrafter.com.v2ex.api.service.V2exService;
import licrafter.com.v2ex.ui.activity.LoginActivity;
import licrafter.com.v2ex.util.ApiErrorUtil;
import licrafter.com.v2ex.util.CustomUtil;
import licrafter.com.v2ex.util.JsoupUtil;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * author: lijinxiang
 * date: 2016/3/21
 **/
public class LoginPresenter extends BasePresenter<LoginActivity> {


    public void login(final String username, final String password) {
        compositeSubscription.add(V2exService.getInstance().login().getOnceString()
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        return JsoupUtil.parseOnce(s);
                    }
                })
                .flatMap(new Func1<String, Observable<String>>() {
                    @Override
                    public Observable<String> call(String once) {
                        return V2exService.getInstance().login().login(username, password, once, "");
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
                        if (getView() != null) {
                            ApiErrorUtil.handleError(e);
                            getView().onFailure(e);
                        }
                    }

                    @Override
                    public void onNext(String s) {
                        android.util.Log.d("ljx", CustomUtil.getErrorMsg(s));
                    }
                }));
    }
}
