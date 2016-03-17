package licrafter.com.v2ex.api;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import licrafter.com.v2ex.util.Constant;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lijinxiang on 11/5/15.
 */
public class Server {

    private static Server instance;

    private V2EX mV2ex;
    private V2EXAPI mV2exApi;
    private LOGIN mLogin;
    private Gson gson;
    private OkHttpClient client;

    public Server getInstance() {
        if (instance == null) {
            instance = new Server();
        }
        return instance;
    }

    private Server() {
        client = new OkHttpClient();
        client.newBuilder().readTimeout(12, TimeUnit.SECONDS);

        if (Constant.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            client.interceptors().add(loggingInterceptor);
        }

        gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .serializeNulls()
                .create();
    }

    public V2EXAPI v2exApi() {
        if (mV2exApi == null) {
            client.newBuilder().interceptors().add(jsonInterceptor);
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

    public V2EX v2EX() {
        if (mV2ex == null) {
            client.newBuilder().interceptors().add(htmlInterceptor);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(V2EX.BASE_URL)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(client)
                    .build();
            mV2ex = retrofit.create(V2EX.class);
        }
        return mV2ex;
    }

    public LOGIN login() {
        if (mLogin == null) {
            client.newBuilder().interceptors().add(htmlInterceptor);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(LOGIN.BASE_URL)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(client)
                    .build();
            mLogin = retrofit.create(LOGIN.class);
        }
        return mLogin;
    }

    private Interceptor htmlInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request newRequest = chain.request().newBuilder()
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .build();
            return chain.proceed(newRequest);
        }
    };

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
