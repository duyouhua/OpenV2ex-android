package licrafter.com.v2ex;/**
 * Created by Administrator on 2016/3/18.
 */

import android.app.Application;
import android.content.Context;

import io.realm.RealmConfiguration;
import licrafter.com.v2ex.util.SharedPreferenceUtils;

/**
 * author: lijinxiang
 * date: 2016/3/18
 **/
public class BaseApplication extends Application {

    private static Context context;
    private static RealmConfiguration realmConfiguration;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        realmConfiguration = new RealmConfiguration.Builder(context)
                .name("v2ex.realm").build();
    }

    public static Context getContext() {
        return context;
    }

    public static RealmConfiguration getRealmConfiguration() {
        return realmConfiguration;
    }

    public static boolean isLogin() {
        return SharedPreferenceUtils.getBoolean("loginStatus", false);
    }

    public static void setLogin(boolean login) {
        SharedPreferenceUtils.save("loginStatus", login);
    }
}
