package licrafter.com.v2ex.ui.widget.gestureView.detectors;/**
 * Created by Administrator on 2016/4/8.
 */

import android.os.Build;
import android.support.annotation.NonNull;
import android.view.View;

/**
 * author: lijinxiang
 * date: 2016/4/8
 **/
public abstract class AnimationEngine implements Runnable {

    private static final long FRAME_TIME = 10L;

    private final View view;

    public AnimationEngine(@NonNull View view) {
        this.view = view;
    }

    @Override
    public void run() {
        boolean continueAnimation = onStep();

        if (continueAnimation) {

        }
    }

    public abstract boolean onStep();

    private void scheduleNextStep() {
        view.removeCallbacks(this);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            view.postDelayed(this, FRAME_TIME);
        } else {
            view.postOnAnimationDelayed(this, FRAME_TIME);
        }
    }

    public void start() {
        scheduleNextStep();
    }
}
