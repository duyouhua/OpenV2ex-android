package licrafter.com.v2ex;/**
 * Created by Administrator on 2016/3/18.
 */

import android.app.Application;
import android.content.Context;

/**
 * author: lijinxiang
 * date: 2016/3/18
 **/
public class BaseApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
