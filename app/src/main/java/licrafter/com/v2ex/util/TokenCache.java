package licrafter.com.v2ex.util;


/**
 * Created by Jack on 2015/8/28.
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
