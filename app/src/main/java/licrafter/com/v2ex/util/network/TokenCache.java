package licrafter.com.v2ex.util.network;


import licrafter.com.v2ex.util.SharedPreferenceUtils;

/**
 * Created by shell on 2016/3/22.
 */
public class TokenCache {

    public static void saveToken(String token) {
        SharedPreferenceUtils.save("tokenCache", token);
    }

    public static String getToken() {
        return SharedPreferenceUtils.getString("tokenCache", "");
    }


    public static void clearToken() {
        SharedPreferenceUtils.save("tokenCache", "");
    }

}
