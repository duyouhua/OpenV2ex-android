package licrafter.com.v2ex.mvp.presenters;/**
 * Created by Administrator on 2016/3/21.
 */

import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import licrafter.com.v2ex.api.service.AuthService;
import licrafter.com.v2ex.model.LoginResult;
import licrafter.com.v2ex.mvp.views.MvpView;
import licrafter.com.v2ex.ui.widget.LoginDialog;
import licrafter.com.v2ex.ui.widget.RegisterDialog;
import licrafter.com.v2ex.util.network.ApiErrorUtil;
import licrafter.com.v2ex.util.JsoupUtil;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * author: lijinxiang
 * date: 2016/3/21
 **/
public class LoginPresenter extends BasePresenter<MvpView> {


    public void login(final String username, final String password) {
        compositeSubscription.add(AuthService.getInstance().auth().getOnceString()
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        return JsoupUtil.parseOnce(s);
                    }
                })
                .flatMap(new Func1<String, Observable<String>>() {
                    @Override
                    public Observable<String> call(String once) {
                        return AuthService.getInstance().auth().login(Integer.valueOf(once), username, password, "/");
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
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String response) {
                        return JsoupUtil.parseRegisterCode(response);
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
                        }
                    }

                    @Override
                    public void onNext(String once) {
                        if (getView() != null) {
                            ((RegisterDialog) getView()).parseRegCode(once);
                        }
                    }
                }));
    }

    public void getCodeImage(String once) {
        compositeSubscription.add(AuthService.getInstance().auth().getCodeImage(once)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
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
                    public void onNext(ResponseBody response) {
                        if (getView() != null) {
                            writeResponseBodyToDisk(response);
                        }
                    }
                }));
    }

    /**
     * 解析bitmap的方法失败，只能使用现下载再添加的方法了。。。
     *
     * @param body
     * @return
     */
    private boolean writeResponseBodyToDisk(ResponseBody body) {
        File futureStudioIconFile = new File(((RegisterDialog) getView()).getContext().getExternalFilesDir(null) + File.separator + "_code.png");

        try {
            // todo change the file location/name according to your needs
            android.util.Log.d("ljx", "path = " + futureStudioIconFile.getAbsolutePath());
            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                //不加@Steaming标注，会一次性把整张图片加载到内存，所以byte[]大小要大于图片所占大小
                byte[] fileReader = new byte[4028];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    android.util.Log.d("ljx", "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
                ((RegisterDialog) getView()).parseCodeImage(futureStudioIconFile);
            }
        } catch (IOException e) {
            return false;
        }

    }
}
