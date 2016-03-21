package licrafter.com.v2ex.util;/**
 * Created by Administrator on 2016/3/18.
 */

import android.widget.Toast;

import java.net.ConnectException;
import java.net.UnknownHostException;

import licrafter.com.v2ex.BaseApplication;
import licrafter.com.v2ex.R;
import retrofit2.adapter.rxjava.HttpException;

/**
 * author: lijinxiang
 * date: 2016/3/18
 **/
public class ApiErrorUtil {

    public static void handleError(Throwable e) {
        android.util.Log.d("v2ex", "error:" + e.toString());
        if (e instanceof ConnectException || e instanceof UnknownHostException) {
            Toast.makeText(BaseApplication.getContext(), BaseApplication
                    .getContext().getString(R.string.network_connect_error), Toast.LENGTH_SHORT).show();
        } else if (e instanceof HttpException) {
            int code = ((HttpException) e).code();
            if (code == 403) {
                Toast.makeText(BaseApplication.getContext(), BaseApplication
                        .getContext().getString(R.string.forbidden), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
