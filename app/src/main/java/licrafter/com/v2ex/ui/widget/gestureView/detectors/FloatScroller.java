package licrafter.com.v2ex.ui.widget.gestureView.detectors;/**
 * Created by Administrator on 2016/4/6.
 */

import android.os.SystemClock;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

/**
 * author: lijinxiang
 * date: 2016/4/6
 **/
public class FloatScroller {

    public static final int DEFAULT_DURATION = 250;

    private final Interpolator interpolator;

    private boolean finished = true;

    private float startValue;
    private float finalValue;

    private float currValue;

    private long startRtc;
    private long duration = DEFAULT_DURATION;

    public FloatScroller() {
        interpolator = new AccelerateDecelerateInterpolator();
    }

    @SuppressWarnings("unused")
    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public void forceFinished() {
        finished = true;
    }

    @SuppressWarnings("unused")
    public void abortAnimation() {
        finished = true;
        currValue = finalValue;
    }

    public boolean computeScroll() {
        if (finished) {
            return false;
        }

        long elapsed = SystemClock.elapsedRealtime() - startRtc;
        if (elapsed >= duration) {
            finished = true;
            currValue = finalValue;
            return false;
        }
        float time = interpolator.getInterpolation((float) elapsed / duration);
        currValue = interpolate(startValue, finalValue, time);
        return true;
    }

    public void startScroll(float startValue, float finalValue) {
        finished = false;
        startRtc = SystemClock.elapsedRealtime();

        this.startValue = startValue;
        this.finalValue = finalValue;
        currValue = startValue;
    }

    public boolean isFinished() {
        return finished;
    }

    public float getStart() {
        return startValue;
    }

    public float getFinal() {
        return finalValue;
    }

    public float getCurr() {
        return currValue;
    }

    private static float interpolate(float x1, float x2, float state) {
        return x1 + (x2 - x1) * state;
    }
}
