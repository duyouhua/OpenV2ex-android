package licrafter.com.v2ex.mvp.presenters;/**
 * Created by Administrator on 2016/3/21.
 */

import java.io.File;
import java.util.HashMap;

import licrafter.com.v2ex.api.service.AuthService;
import licrafter.com.v2ex.model.LoginResult;
import licrafter.com.v2ex.model.response.LoginFormInfo;
import licrafter.com.v2ex.model.response.RegFormInfo;
import licrafter.com.v2ex.mvp.views.MvpView;
import licrafter.com.v2ex.ui.widget.LoginDialog;
import licrafter.com.v2ex.ui.widget.RegisterDialog;
import licrafter.com.v2ex.util.CustomUtil;
import licrafter.com.v2ex.util.network.ApiErrorUtil;
import licrafter.com.v2ex.util.JsoupUtil;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * author: lijinxiang
 * date: 2016/3/21
 **/
public class LoginPresenter extends BasePresenter<MvpView> {


    public void login(final String username, final String password) {
        compositeSubscription.add(AuthService.getInstance().auth().getOnceString()
                .map(new Func1<String, LoginFormInfo>() {
                    @Override
                    public LoginFormInfo call(String s) {
                        return JsoupUtil.parseLoginFormInfo(s);
                    }
                })
                .flatMap(new Func1<LoginFormInfo, Observable<String>>() {
                    @Override
                    public Observable<String> call(LoginFormInfo formInfo) {
                        HashMap<String, String> hashMap = new HashMap<String, String>();
                        hashMap.put(formInfo.getNameKey(), username);
                        hashMap.put(formInfo.getPswKey(), password);
                        hashMap.put("once", formInfo.getOnce());
                        hashMap.put("next", "/");
                        return AuthService.getInstance().auth().login(hashMap);
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
                            getView().onFailure(ApiErrorUtil.handleError(e));
                        }
                    }

                    @Override
                    public void onNext(LoginResult result) {
                        if (getView() != null) {
                            ((LoginDialog) getView()).onLoginSuccess(result);
                        }
                    }
                }));
    }

    public void getRegisterCode() {
        compositeSubscription.add(AuthService.getInstance().auth()
                .getRegisterCode()
                .map(new Func1<String, RegFormInfo>() {
                    @Override
                    public RegFormInfo call(String response) {
                        return JsoupUtil.parseRegFormInfo(response);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RegFormInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (getView() != null) {
                            ApiErrorUtil.handleError(e);
                        }
                    }

                    @Override
                    public void onNext(RegFormInfo formInfo) {
                        if (getView() != null) {
                            ((RegisterDialog) getView()).parseRegCode(formInfo);
                        }
                    }
                }));
    }

    public void getCodeImage(String once) {
        compositeSubscription.add(AuthService.getInstance().auth().getCodeImage(once)
                .map(new Func1<ResponseBody, File>() {
                    @Override
                    public File call(ResponseBody responseBody) {
                        return CustomUtil.writeResponseBodyToDisk(responseBody);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<File>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (getView() != null) {
                            ApiErrorUtil.handleError(e);
                        }
                    }

                    @Override
                    public void onNext(File codeImage) {
                        if (getView() != null) {
                            ((RegisterDialog) getView()).parseCodeImage(codeImage);
                        }
                    }
                }));
    }

    public void register(RegFormInfo formInfo) {
        HashMap<String, String> fieldMap = new HashMap<>();
        fieldMap.put(formInfo.getNameKey(), formInfo.getNameValue());
        fieldMap.put(formInfo.getPswKey(), formInfo.getPswValue());
        fieldMap.put(formInfo.getEmailKey(), formInfo.getEmailValue());
        fieldMap.put(formInfo.getCodeKey(), formInfo.getCodeValue());
        fieldMap.put("once", formInfo.getOnce());
        compositeSubscription.add(AuthService.getInstance().auth().register(fieldMap)
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
                        }
                    }

                    @Override
                    public void onNext(String response) {
                        if (getView() != null) {
                            String error = ApiErrorUtil.getErrorMsg(response);
                            ((RegisterDialog) getView()).parseRegError(error);
                        }
                    }
                }));
    }
}
