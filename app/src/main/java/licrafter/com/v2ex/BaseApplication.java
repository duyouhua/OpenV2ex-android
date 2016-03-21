package licrafter.com.v2ex;/**
 * Created by Administrator on 2016/3/18.
 */

import android.app.Application;
import android.content.Context;

import licrafter.com.v2ex.util.DeviceUtil;

/**
 * author: lijinxiang
 * date: 2016/3/18
 **/
public class BaseApplication extends Application {

    private static Context context;
    public static String userAgentString = "";
    public static String appVersion = "";

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        appVersion = DeviceUtil.getVersionName(this);
        userAgentString = "OS/android "
                + "OSVersion/" + DeviceUtil.getAndroidSDKINT() + " "
                + "product/pw "
                + "IMEI/" + DeviceUtil.getIMEI(this) + " "
                + "phoneBrand/" + DeviceUtil.getPhoneBrand() + " "
                + "phoneModel/" + DeviceUtil.getPhoneModel() + " "
                + "appVersionName/" + appVersion + " "
                + "appVersionCode/" + DeviceUtil.getVersionCode(this);
    }

    public static Context getContext() {
        return context;
    }
}
