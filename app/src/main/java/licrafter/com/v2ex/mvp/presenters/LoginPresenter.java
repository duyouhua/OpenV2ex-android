package licrafter.com.v2ex.mvp.presenters;/**
 * Created by Administrator on 2016/3/21.
 */

import licrafter.com.v2ex.api.service.LoginService;
import licrafter.com.v2ex.model.LoginResult;
import licrafter.com.v2ex.ui.activity.LoginActivity;
import licrafter.com.v2ex.util.network.ApiErrorUtil;
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
        compositeSubscription.add(LoginService.getInstance().login().getOnceString()
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        return JsoupUtil.parseOnce(s);
                    }
                })
                .flatMap(new Func1<String, Observable<String>>() {
                    @Override
                    public Observable<String> call(String once) {
                        return LoginService.getInstance().login().login(Integer.valueOf(once), username, password, "/");
                    }
                })
                .map(new Func1<String, LoginResult>() {
                    @Override
                    public LoginResult call(String response) {
                        String errorMsg = ApiErrorUtil.getErrorMsg(response);
                        if (errorMsg == null) {
                            return JsoupUtil.parseLoginResult(response);
                        } else {
                            LoginResult result = new LoginResult();
                            result.setMessage(errorMsg);
                            return result;
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<LoginResult>() {
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
                    public void onNext(LoginResult result) {
                        if (getView() != null) {
                            ((LoginActivity) getView()).onLoginSuccess(result);
                        }
                    }
                }));
    }
}
