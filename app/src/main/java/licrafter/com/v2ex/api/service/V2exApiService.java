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
    private static V2exApiService instance;

    private V2EXAPI mV2exApi;
    private Gson gson;
    private OkHttpClient client;

    public static V2exApiService getInstance() {
        if (instance == null) {
            instance = new V2exApiService();
        }
        return instance;
    }

    public V2exApiService() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client = new OkHttpClient.Builder()
                .readTimeout(12, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(jsonInterceptor)
                .build();

        gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .serializeNulls()
                .create();
    }

    public V2EXAPI v2exApi() {
        if (mV2exApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(V2EXAPI.BASE_API)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(client)
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
