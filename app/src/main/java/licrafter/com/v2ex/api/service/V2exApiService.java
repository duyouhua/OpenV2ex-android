package licrafter.com.v2ex.api.service;/**
 * Created by Administrator on 2016/3/18.
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import licrafter.com.v2ex.api.V2EXAPI;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * author: lijinxiang
 * date: 2016/3/18
 **/
public class V2exApiService {
    private static V2exApiService mInstance;

    private V2EXAPI mV2exApi;
    private Gson mGson;
    private OkHttpClient mClient;

    public static V2exApiService getmInstance() {
        if (mInstance == null) {
            mInstance = new V2exApiService();
        }
        return mInstance;
    }

    public V2exApiService() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        mClient = new OkHttpClient.Builder()
                .readTimeout(12, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(jsonInterceptor)
                .build();

        mGson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .serializeNulls()
                .create();
    }

    public V2EXAPI v2exApi() {
        if (mV2exApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(V2EXAPI.BASE_API)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(mGson))
                    .client(mClient)
                    .build();
            mV2exApi = retrofit.create(V2EXAPI.class);
        }
        return mV2exApi;
    }

    private Interceptor jsonInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request newRequest = chain.request().newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .build();
            return chain.proceed(newRequest);
        }
    };
}
