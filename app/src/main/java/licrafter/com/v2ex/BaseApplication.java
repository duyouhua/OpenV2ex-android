package licrafter.com.v2ex;/**
 * Created by Administrator on 2016/3/18.
 */

import android.app.Application;
import android.content.Context;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import licrafter.com.v2ex.model.Node;
import licrafter.com.v2ex.util.SharedPreferenceUtils;
import rx.Observable;

/**
 * author: lijinxiang
 * date: 2016/3/18
 **/
public class BaseApplication extends Application {

    private static Context mContext;
    private Observable<ArrayList<Node>> nodeObservable;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(mContext)
                .name("v2ex.realm").build();
        Realm.setDefaultConfiguration(realmConfiguration);

    }

    public static Context getContext() {
        return mContext;
    }

    public static boolean isLogin() {
        return SharedPreferenceUtils.getBoolean("loginStatus", false);
    }

    public static void setLogin(boolean login) {
        SharedPreferenceUtils.save("loginStatus", login);
    }
}
