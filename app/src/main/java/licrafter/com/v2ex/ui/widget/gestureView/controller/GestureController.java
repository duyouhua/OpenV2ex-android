package licrafter.com.v2ex.ui.widget.gestureView.controller;/**
 * Created by Administrator on 2016/4/5.
 */

import android.content.Context;
import android.graphics.PointF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.OverScroller;

import java.util.ArrayList;
import java.util.List;

import licrafter.com.v2ex.ui.widget.gestureView.MovementBounds;
import licrafter.com.v2ex.ui.widget.gestureView.detectors.AnimationEngine;
import licrafter.com.v2ex.ui.widget.gestureView.detectors.FloatScroller;
import licrafter.com.v2ex.ui.widget.gestureView.detectors.RotationGestureDetector;
import licrafter.com.v2ex.ui.widget.gestureView.stats.State;

/**
 * author: lijinxiang
 * date: 2016/4/5
 **/
public class GestureController  {

//    private static final float ZOOM_GESTURE_MIN_SPAN_DP = 20f;
//    private static final float FLING_COEFFICIENT = 0.9f;
//
//    private final float zoomGestureMinSpan;
//    private final int touchSlop;
//    private final int minimumVelocity;
//    private final int maximumVelocity;
//
//    private final List<OnStateChangeListener> stateListeners = new ArrayList<>();
//    private OnGestureListener onGestureListener;
//
//    private final AnimationEngine animationEngine;
//
//    private final GestureDetector gestureDetector;
//    private final ScaleGestureDetector scaleGestureDetector;
//    private final RotationGestureDetector rotationGestureDetector;
//
//    private boolean isScrollDetected;
//    private boolean isScaleDetected;
//    private float pivotX = Float.NaN;
//    private float pivotY = Float.NaN;
//    private boolean isStateChangedDuringTouch;
//    private boolean isRestrictZoomRequested;
//    private boolean isRestrictRotationRequested;
//    private boolean isAnimatingInBounds;
//
//    private final OverScroller flingScroller;
//    private final FloatScroller stateScroller;
//
//    private final MovementBounds flingBounds = new MovementBounds();
//    private final State prevState = new State();
//    private final State stateStart = new State();
//    private final State stateEnd = new State();
//
//    private final Settings settings;
//    private final State state = new State();
//    private final StateController stateController;
//
//    public GestureController(@NonNull View view) {
//        Context context = view.getContext();
//        DisplayMetrics metrics = view.getResources().getDisplayMetrics();
//        //转化为dip
//        zoomGestureMinSpan = TypedValue.applyDimension(
//                TypedValue.COMPLEX_UNIT_DIP, ZOOM_GESTURE_MIN_SPAN_DP, metrics
//        );
//        settings = new Settings();
//        stateController = new StateController(settings);
//
//    }
//
//    @Override
//    public boolean onTouch(View v, MotionEvent event) {
//        return false;
//    }
//
//
//    public interface OnStateChangeListener {
//        void onStateChanged(State state);
//
//        void onStateReset(State state);
//    }
//
//    private class LocalAnimationEngine extends AnimationEngine {
//
//        LocalAnimationEngine(@NonNull View view) {
//            super(view);
//        }
//
//        @Override
//        public boolean onStep() {
//            boolean shouldProceed = false;
//            if (isAnimatingFling()) {
//                int prevX = flingScroller.getCurrX();
//                int prevY = flingScroller.getCurrY();
//                if (flingScroller.computeScrollOffset()) {
//                    int dx = flingScroller.getCurrX() - prevX;
//                    int dy = flingScroller.getCurrY() - prevY;
//                    if (!onFlingScroll(dx, dy)) {
//                        //无滑动的时候停止滑动
//                        stopFlingAnimation();
//                    }
//                    shouldProceed = true;
//                }
//                if (!isAnimatingFling()){
//                    onFlingAnimationFinished(false);
//                }
//            }
//
//            if (isAnimatingState()){
//                stateScroller.computeScroll();
//                float factor = stateScroller.getCurr();
//                StateController.interpolate(state,stateStart,stateEnd,factor);
//                shouldProceed = true;
//
//                if (!isAnimatingState()){
//                    onStateAnimationFinished(false);
//                }
//            }
//
//            if (shouldProceed){
//                notifyStateUpdated();
//            }
//            return shouldProceed;
//        }
//    }
//
//    protected void notifyStateUpdated(){
//        prevState.set(state);
//        for (OnStateChangeListener listener : stateListeners){
//            listener.onStateChanged(state);
//        }
//    }
//
//    /**
//     * 当图片放大的时候滑翔滚动,返回图片有没有滑动（已经到达边界就不会再滑动了）
//     * true有滑动 false无滑动
//     *
//     * @param dx
//     * @param dy
//     * @return
//     */
//    protected boolean onFlingScroll(int dx, int dy) {
//        //通过state得到当前的x，y
//        float prevX = state.getX();
//        float prevY = state.getY();
//        float toX = prevX + dx;
//        float toY = prevY + dy;
//        //滑动的时候图片是否要有边界
//        if (settings.isRestrictBounds()) {
//            //如果超出便捷，重新计算终点x，y // TODO: 2016/4/8 限制边界功能代码
//            PointF pos = flingBounds.restrict(toX, toY);
//            toX = pos.x;
//            toY = pos.y;
//        }
//        state.translateTo(toX, toY);
//        return !State.equals(prevX, toX) || !State.equals(prevY, toY);
//    }
//
//    public void stopFlingAnimation() {
//        if (isAnimatingFling()) {
//            flingScroller.forceFinished(true);
//        }
//    }
//
//    public boolean isAnimatingFling() {
//        return !flingScroller.isFinished();
//    }
//
//    protected void onFlingAnimationFinished(boolean forced) {
//        if (!forced) {
//            animationKeepInBounds();
//        }
//    }
//
//    public boolean animationKeepInBounds() {
//
//    }
//
//    public boolean animateStateTo(@NonNull State endState) {
//        return animateStateTo(endState, true);
//    }
//
//    private boolean animateStateTo(@Nullable State endState, boolean keepInBound) {
//        if (endState == null) {
//            return false;
//        }
//        State endStateFixed = null;
//        if (keepInBound) {
//            endStateFixed = stateController.restrictStateBoundsCopy(
//                    endState, prevState, pivotX, pivotY, false, false, true);
//        }
//        if (endStateFixed == null) {
//            endStateFixed = endState;
//        }
//
//        if (endStateFixed.equals(state)) {
//            return false;
//        }
//        stopAllAnimations();
//        isAnimatingInBounds = keepInBound;
//        stateStart.set(state);
//        stateEnd.set(endStateFixed);
//        stateScroller.startScroll(0f, 1f);
//        animationEngine.start();
//        return true;
//    }
//
//    public void stopStateAnimation() {
//        if (isAnimatingState()) {
//            stateScroller.forceFinished();
//            onStateAnimationFinished(true);
//        }
//    }
//
//    protected void onStateAnimationFinished(boolean forced) {
//        isAnimatingInBounds = false;
//    }
//
//    public boolean isAnimatingState() {
//        return !stateScroller.isFinished();
//    }
//
//    public void stopAllAnimations() {
//        stopStateAnimation();
//        stopFlingAnimation();
//    }
//
//    /**
//     * {@link GestureDetector.OnGestureListener}
//     */
//    public interface OnGestureListener {
//        void onDown(@NonNull MotionEvent event);
//
//        void onUporCancel(@NonNull MotionEvent event);
//
//        void onUpOrCancel(@NonNull MotionEvent event);
//
//        boolean onSingleTapUp(@NonNull MotionEvent event);
//
//        boolean onSingleTapConfirmed(@NonNull MotionEvent event);
//
//        void onLongPress(@NonNull MotionEvent event);
//
//        boolean onDoubleTap(@NonNull MotionEvent event);
//    }
//
//    public static class SimpleOnGestureListener implements OnGestureListener {
//
//        @Override
//        public void onDown(@NonNull MotionEvent event) {
//
//        }
//
//        @Override
//        public void onUporCancel(@NonNull MotionEvent event) {
//
//        }
//
//        @Override
//        public void onUpOrCancel(@NonNull MotionEvent event) {
//
//        }
//
//        @Override
//        public boolean onSingleTapUp(@NonNull MotionEvent event) {
//            return false;
//        }
//
//        @Override
//        public boolean onSingleTapConfirmed(@NonNull MotionEvent event) {
//            return false;
//        }
//
//        @Override
//        public void onLongPress(@NonNull MotionEvent event) {
//
//        }
//
//        @Override
//        public boolean onDoubleTap(@NonNull MotionEvent event) {
//            return false;
//        }
//    }
//
//    private class InternalGesturesListener implements GestureDetector.OnGestureListener
//            , GestureDetector.OnDoubleTapListener, ScaleGestureDetector.OnScaleGestureListener
//            , RotationGestureDetector.OnRotationGestureListener {
//
//        @Override
//        public boolean onSingleTapConfirmed(MotionEvent e) {
//            return false;
//        }
//
//        @Override
//        public boolean onDoubleTap(MotionEvent e) {
//            return false;
//        }
//
//        @Override
//        public boolean onDoubleTapEvent(MotionEvent e) {
//            return false;
//        }
//
//        @Override
//        public boolean onDown(MotionEvent e) {
//            return false;
//        }
//
//        @Override
//        public void onShowPress(MotionEvent e) {
//
//        }
//
//        @Override
//        public boolean onSingleTapUp(MotionEvent e) {
//            return false;
//        }
//
//        @Override
//        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//            return false;
//        }
//
//        @Override
//        public void onLongPress(MotionEvent e) {
//
//        }
//
//        @Override
//        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//            return false;
//        }
//
//        @Override
//        public boolean onRotate(RotationGestureDetector detector) {
//            return false;
//        }
//
//        @Override
//        public boolean onRotationBegin(RotationGestureDetector detector) {
//            return false;
//        }
//
//        @Override
//        public void onRotationEnd(RotationGestureDetector detector) {
//
//        }
//
//        @Override
//        public boolean onScale(ScaleGestureDetector detector) {
//            return false;
//        }
//
//        @Override
//        public boolean onScaleBegin(ScaleGestureDetector detector) {
//            return false;
//        }
//
//        @Override
//        public void onScaleEnd(ScaleGestureDetector detector) {
//
//        }
//    }
}
