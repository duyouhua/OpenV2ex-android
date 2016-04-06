package licrafter.com.v2ex.ui.widget.gestureView.controller;/**
 * Created by Administrator on 2016/4/5.
 */

import android.support.annotation.NonNull;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.OverScroller;

import java.util.ArrayList;
import java.util.List;

import licrafter.com.v2ex.ui.widget.gestureView.detectors.FloatScroller;
import licrafter.com.v2ex.ui.widget.gestureView.detectors.RotationGestureDetector;
import licrafter.com.v2ex.ui.widget.gestureView.stats.State;

/**
 * author: lijinxiang
 * date: 2016/4/5
 **/
public class GestureController implements View.OnTouchListener {

    private static final float ZOOM_GESTURE_MIN_SPAN_DP = 20f;
    private static final float FLING_COEFFICIENT = 0.9f;

    private final float zoomGestureMinSpan;
    private final int touchSlop;
    private final int minimumVelocity;
    private final int maximumVelocity;

    private final List<OnStateChangeListener> stateListeners = new ArrayList<>();
    private OnGestureListener onGestureListener;

    //private final AnimationEngine animationEngine;

    private final GestureDetector gestureDetector;
    private final ScaleGestureDetector scaleGestureDetector;
    private final RotationGestureDetector rotationGestureDetector;

    private boolean isScrollDetected;
    private boolean isScaleDetected;
    private float pivotX = Float.NaN;
    private float pivotY = Float.NaN;
    private boolean isStateChangedDuringTouch;
    private boolean isRestrictZoomRequested;
    private boolean isRestrictRotationRequested;
    private boolean isAnimatingInBounds;

    private final OverScroller flingScroller;
    private final FloatScroller stateScroller;

    @Override

    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }


    public interface OnStateChangeListener {
        void onStateChanged(State state);

        void onStateReset(State state);
    }

    /**
     * {@link GestureDetector.OnGestureListener}
     */
    public interface OnGestureListener {
        void onDown(@NonNull MotionEvent event);

        void onUporCancel(@NonNull MotionEvent event);

        void onUpOrCancel(@NonNull MotionEvent event);

        boolean onSingleTapUp(@NonNull MotionEvent event);

        boolean onSingleTapConfirmed(@NonNull MotionEvent event);

        void onLongPress(@NonNull MotionEvent event);

        boolean onDoubleTap(@NonNull MotionEvent event);
    }

    public static class SimpleOnGestureListener implements OnGestureListener {

        @Override
        public void onDown(@NonNull MotionEvent event) {

        }

        @Override
        public void onUporCancel(@NonNull MotionEvent event) {

        }

        @Override
        public void onUpOrCancel(@NonNull MotionEvent event) {

        }

        @Override
        public boolean onSingleTapUp(@NonNull MotionEvent event) {
            return false;
        }

        @Override
        public boolean onSingleTapConfirmed(@NonNull MotionEvent event) {
            return false;
        }

        @Override
        public void onLongPress(@NonNull MotionEvent event) {

        }

        @Override
        public boolean onDoubleTap(@NonNull MotionEvent event) {
            return false;
        }
    }

    private class InternalGesturesListener implements GestureDetector.OnGestureListener
    ,GestureDetector.OnDoubleTapListener,ScaleGestureDetector.OnScaleGestureListener
    ,
}
