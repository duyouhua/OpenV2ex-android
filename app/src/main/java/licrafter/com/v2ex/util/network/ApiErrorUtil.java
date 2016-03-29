package licrafter.com.v2ex.util.network;/**
 * Created by Administrator on 2016/3/18.
 */

import android.widget.Toast;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import licrafter.com.v2ex.BaseApplication;
import licrafter.com.v2ex.R;
import retrofit2.adapter.rxjava.HttpException;

/**
 * author: lijinxiang
 * date: 2016/3/18
 **/
public class ApiErrorUtil {

    public static String handleError(Throwable e) {
        String error = e.toString();
        android.util.Log.d("v2ex", "error:" + e.toString());
        if (e instanceof ConnectException || e instanceof UnknownHostException) {
            error = BaseApplication.getContext().getString(R.string.network_connect_error);
            Toast.makeText(BaseApplication.getContext(), error, Toast.LENGTH_SHORT).show();
        } else if (e instanceof HttpException) {
            int code = ((HttpException) e).code();
            if (code == 403) {
                error = BaseApplication.getContext().getString(R.string.forbidden);
                Toast.makeText(BaseApplication.getContext(), error, Toast.LENGTH_SHORT).show();
            }
        } else if (e instanceof TimeoutException) {
            error = BaseApplication.getContext().getString(R.string.time_out);
            Toast.makeText(BaseApplication.getContext(), error, Toast.LENGTH_SHORT).show();
        }
        return error;
    }

    /**
     * 登陆报错解析
     *
     * @param response
     * @return
     */
    public static String getErrorMsg(String response) {
        Pattern errorPattern = Pattern.compile("<div class=\"problem\">(.*)</div>");
        Matcher errorMatcher = errorPattern.matcher(response);
        String errorContent;
        if (errorMatcher.find()) {
            errorContent = errorMatcher.group(1).replaceAll("<[^>]+>", "");
        } else {
            errorContent = null;
        }
        return errorContent;
    }
}
