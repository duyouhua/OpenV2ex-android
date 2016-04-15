package licrafter.com.v2ex.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import licrafter.com.v2ex.R;
import licrafter.com.v2ex.model.Topic;

/**
 * Created by shell on 15-11-7.
 */
public class CustomUtil {

    public static String streamFormToString(InputStream inputStream) {
        try {
            int count = inputStream.available();
            byte[] bytes = new byte[count];
            inputStream.read(bytes);
            inputStream.close();
            return new String(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static List<Topic> collectionToList(Collection<Topic> collections) {
        List<Topic> topics = new ArrayList<>();
        Iterator<Topic> iterator = collections.iterator();
        while (iterator.hasNext()) {
            topics.add(iterator.next());
        }
        return topics;
    }

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
}
