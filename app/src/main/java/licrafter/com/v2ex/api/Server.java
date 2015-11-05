package licrafter.com.v2ex.api;

import android.content.Context;

import licrafter.com.v2ex.BuildConfig;
import retrofit.ErrorHandler;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

/**
 * Created by lijinxiang on 11/5/15.
 */
public class Server {

    private static V2EX mV2ex;
    private static V2EXAPI mV2exApi;

    public static V2EXAPI v2exApi(Context context){
        if (mV2exApi == null){
            RestAdapter.Builder builder =initRequestHeader(context);
            RestAdapter restAdapter = builder.build();
            mV2exApi = restAdapter.create(V2EXAPI.class);
        }
        return mV2exApi;
    }

    private static RestAdapter.Builder initRequestHeader(Context context){
        RestAdapter.Builder builder = new RestAdapter.Builder().setEndpoint(V2EXAPI.BASE_API).setClient(new OkClient());
        if (BuildConfig.DEBUG){
            builder.setLogLevel(RestAdapter.LogLevel.FULL);
        }else {
            builder.setLogLevel(RestAdapter.LogLevel.NONE);
        }
        return builder;
    }

}
