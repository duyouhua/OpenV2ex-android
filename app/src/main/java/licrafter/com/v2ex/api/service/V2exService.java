package licrafter.com.v2ex.api.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.concurrent.TimeUnit;

import licrafter.com.v2ex.BaseApplication;
import licrafter.com.v2ex.api.LOGIN;
import licrafter.com.v2ex.api.V2EX;
import licrafter.com.v2ex.api.V2EXAPI;
import licrafter.com.v2ex.util.CustomCookieJar;
import licrafter.com.v2ex.util.PersistentCookieStore;
import licrafter.com.v2ex.util.StringConverter;
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

    private static V2exService instance;

    private V2EX mV2ex;
    private LOGIN mLogin;
    private Gson gson;
    private OkHttpClient client;
    private PersistentCookieStore persistentCookieStore;

    public static V2exService getInstance() {
        if (instance == null) {
            instance = new V2exService();
        }
        return instance;
    }

    private V2exService() {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        persistentCookieStore = new PersistentCookieStore(BaseApplication.getContext());
        client = new OkHttpClient.Builder()
                .cookieJar(new CustomCookieJar(new CookieManager(persistentCookieStore, CookiePolicy.ACCEPT_ALL)))
                .readTimeout(12, TimeUnit.SECONDS)
                .addInterceptor(htmlInterceptor)
                .addInterceptor(loggingInterceptor)
                .build();

        gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .serializeNulls()
                .create();
    }

    public OkHttpClient getClient() {
        return client;
    }

    public V2EX v2EX() {
        if (mV2ex == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(V2EX.BASE_URL)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(new StringConverter())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(client)
                    .build();
            mV2ex = retrofit.create(V2EX.class);
        }
        return mV2ex;
    }

    public LOGIN login() {
        if (mLogin == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(LOGIN.BASE_URL)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(new StringConverter())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(client)
                    .build();
            mLogin = retrofit.create(LOGIN.class);
        }
        return mLogin;
    }

    //这个interceptor添加顺序要在loggingInterceptor之前，否则无效
    private Interceptor htmlInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request newRequest = chain.request().newBuilder()
                    .addHeader("Origin", "https://www.v2ex.com")
                    .addHeader("Referer", "https://www.v2ex.com/signin")
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .removeHeader("User-Agent")
                    .addHeader("User-Agent", BaseApplication.userAgentString)
                    .build();
            return chain.proceed(newRequest);
        }
    };
}
