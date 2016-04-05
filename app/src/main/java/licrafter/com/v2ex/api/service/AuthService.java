package licrafter.com.v2ex.api.service;/**
 * Created by Administrator on 2016/3/22.
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.concurrent.TimeUnit;

import licrafter.com.v2ex.BaseApplication;
import licrafter.com.v2ex.api.AUTH;
import licrafter.com.v2ex.util.network.CustomCookieJar;
import licrafter.com.v2ex.util.network.PersistentCookieStore;
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
 * author: lijinxiang
 * date: 2016/3/22
 **/
public class AuthService {

    private static AuthService instance;
    private Gson gson;
    private OkHttpClient client;
    private AUTH auth;
    private PersistentCookieStore persistentCookieStore;

    public static AuthService getInstance() {
        if (instance == null) {
            instance = new AuthService();
        }

        return instance;
    }

    public PersistentCookieStore getCookieStore(){
        return persistentCookieStore;
    }

    public AuthService() {
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

    public boolean clearCookies() {
        return persistentCookieStore.removeAll();
    }

    public AUTH auth() {
        if (auth == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(AUTH.BASE_URL)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(new StringConverter())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(client)
                    .build();
            auth = retrofit.create(AUTH.class);
        }
        return auth;
    }

    //这个interceptor添加顺序要在loggingInterceptor之前，否则无效
    private Interceptor htmlInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request newRequest = chain.request().newBuilder()
                    .addHeader("Origin", "https://www.v2ex.com")
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .build();
            return chain.proceed(newRequest);
        }
    };
}
