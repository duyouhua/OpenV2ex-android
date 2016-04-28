package licrafter.com.v2ex.api.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import licrafter.com.v2ex.api.V2EX;
import licrafter.com.v2ex.util.network.StringConverter;
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
public class V2exService {

    private static V2exService mInstance;

    private V2EX mV2ex;
    private Gson mGson;
    private OkHttpClient mClient;

    public static V2exService getmInstance() {
        if (mInstance == null) {
            mInstance = new V2exService();
        }
        return mInstance;
    }

    private V2exService() {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        mClient = new OkHttpClient.Builder()
                .readTimeout(12, TimeUnit.SECONDS)
                .addInterceptor(htmlInterceptor)
                .addInterceptor(loggingInterceptor)
                .build();

        mGson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .serializeNulls()
                .create();
    }

    public V2EX v2EX() {
        if (mV2ex == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(V2EX.BASE_URL)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(new StringConverter())
                    .addConverterFactory(GsonConverterFactory.create(mGson))
                    .client(mClient)
                    .build();
            mV2ex = retrofit.create(V2EX.class);
        }
        return mV2ex;
    }


    //这个interceptor添加顺序要在loggingInterceptor之前，否则无效
    private Interceptor htmlInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request newRequest = chain.request().newBuilder()
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .build();
            return chain.proceed(newRequest);
        }
    };
}
