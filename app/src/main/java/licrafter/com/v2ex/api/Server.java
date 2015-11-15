package licrafter.com.v2ex.api;

import android.content.Context;

import licrafter.com.v2ex.BuildConfig;
import retrofit.ErrorHandler;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;
import retrofit.client.UrlConnectionClient;

/**
 * Created by lijinxiang on 11/5/15.
 */
public class Server {

    private static V2EX mV2ex;
    private static V2EXAPI mV2exApi;
    private static LOGIN mLogin;

    public static V2EXAPI v2exApi(Context context) {
        if (mV2exApi == null) {
            RestAdapter.Builder builder = initRequest(context);
            RestAdapter restAdapter = builder.build();
            mV2exApi = restAdapter.create(V2EXAPI.class);
        }
        return mV2exApi;
    }

    public static LOGIN login(Context context){
        if (mLogin == null){
            RestAdapter.Builder builder = initLoginRequestHeader(context);
            RestAdapter restAdapter = builder.build();
            mLogin = restAdapter.create(LOGIN.class);
        }
        return mLogin;
    }

    public static V2EX v2EX(Context context) {
        if (mV2ex == null) {
            RestAdapter.Builder builder = initRequestHeader(context);
            RestAdapter restAdapter = builder.build();
            mV2ex = restAdapter.create(V2EX.class);
        }
        return mV2ex;
    }

    private static RestAdapter.Builder initRequest(Context context) {
        RestAdapter.Builder builder = new RestAdapter.Builder().setEndpoint(V2EXAPI.BASE_API).setClient(new OkClient());
        if (BuildConfig.DEBUG) {
            builder.setLogLevel(RestAdapter.LogLevel.FULL);
        } else {
            builder.setLogLevel(RestAdapter.LogLevel.NONE);
        }
        return builder;
    }

    private static RestAdapter.Builder initRequestHeader(Context context) {
        RequestInterceptor interceptor = null;
        interceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("Referer", V2EX.BASE_URL);
                request.addHeader("Content-Type", "application/x-www-form-urlencoded");
            }
        };
        RestAdapter.Builder builder = new RestAdapter.Builder().setEndpoint(V2EX.BASE_URL).setClient(new UrlConnectionClient());
        if (BuildConfig.DEBUG) {
            builder.setLogLevel(RestAdapter.LogLevel.FULL);
        } else {
            builder.setLogLevel(RestAdapter.LogLevel.NONE);
        }
        if (interceptor != null) {
            builder.setRequestInterceptor(interceptor);
        }
        return builder;
    }

    private static RestAdapter.Builder initLoginRequestHeader(Context context){
        RequestInterceptor interceptor = null;
        interceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("Origin", LOGIN.BASE_URL);
                request.addHeader("Content-Type", "application/x-www-form-urlencoded");
                request.addHeader("Referer","https://www.v2ex.com/signin");
            }
        };
        RestAdapter.Builder builder = new RestAdapter.Builder().setEndpoint(LOGIN.BASE_URL).setClient(new OkClient());
        if (BuildConfig.DEBUG) {
            builder.setLogLevel(RestAdapter.LogLevel.FULL);
        } else {
            builder.setLogLevel(RestAdapter.LogLevel.NONE);
        }
        if (interceptor != null) {
            builder.setRequestInterceptor(interceptor);
        }
        return builder;
    }

}
