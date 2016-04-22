package licrafter.com.v2ex.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import licrafter.com.v2ex.BaseApplication;
import licrafter.com.v2ex.R;
import okhttp3.ResponseBody;

/**
 * Created by shell on 15-11-7.
 */
public class CustomUtil {

    public static void initStyle(SwipeRefreshLayout swipeRefreshLayout) {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setProgressViewOffset(false, 0, 30);
            swipeRefreshLayout.setColorSchemeResources(
                    R.color.loading_color_1,
                    R.color.loading_color_2,
                    R.color.loading_color_3,
                    R.color.loading_color_4,
                    R.color.loading_color_5);
        }
    }

    /**
     * 在代码中修改status bar颜色
     * 也可以在values-v21里重写styles.yml
     * http://stackoverflow.com/questions/22192291/how-to-change-the-status-bar-color-in-android
     */

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setStatusBarColor(Activity activity) {
        Window window = activity.getWindow();

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // finally change the color
        window.setStatusBarColor(activity.getResources().getColor(R.color.teal500));
    }

    public static void showInputMethod(View view) {
        final InputMethodManager manager = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.showSoftInput(view, 0);
    }

    /**
     * 解析bitmap的方法失败，只能使用现下载再添加的方法了
     *
     * @param body
     * @return
     */
    public static File writeResponseBodyToDisk(ResponseBody body) {
        File codeImage = new File(BaseApplication.getContext().getExternalFilesDir(null) + File.separator + "_code.png");

        try {
            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {

                byte[] fileReader = new byte[1024];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(codeImage);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    android.util.Log.d("ljx", "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                return codeImage;
            } catch (IOException e) {
                return null;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return null;
        }

    }
}
