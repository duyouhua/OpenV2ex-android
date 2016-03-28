package licrafter.com.v2ex.util.behavior;

import android.animation.Animator;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.Interpolator;

/**
 * author: shell
 * date 16/3/27 下午4:26
 **/
public class FooterBehavior extends CoordinatorLayout.Behavior<View> {

    private static final Interpolator INTERPOLATOR = new FastOutSlowInInterpolator();

    private int directionChange;
    private boolean isBottom;

    public FooterBehavior(Context context, AttributeSet attr) {
        super(context, attr);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        //判断是不是垂直方向滑动
        return (nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL);
    }

    @Override
    public void onNestedScroll(final CoordinatorLayout coordinatorLayout, final View child, View target, int dxConsumed, final int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
        if (target instanceof NestedScrollView) {
            final NestedScrollView scrollView = ((NestedScrollView) target);

            scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                    if ((scrollView.getHeight() + scrollY) == v.getChildAt(v.getChildCount() - 1).getBottom()) {
                        isBottom = true;
                        show(child);
                    } else {
                        isBottom = false;
                    }
                }
            });
        }
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
        if (target instanceof NestedScrollView){
            if (dy > 0 && directionChange < 0 || dy < 0 && directionChange > 0) {
                child.animate().cancel();
                directionChange = 0;
            }
            directionChange += dy;

            if (directionChange > child.getHeight() && child.getVisibility() == View.VISIBLE && !isBottom) {
                hide(child);
            } else if (directionChange < 0 && child.getVisibility() == View.GONE) {
                show(child);
            }
        }
    }

    private void hide(final View view) {
        ViewPropertyAnimator animator = view.animate().translationY(view.getHeight()).setInterpolator(INTERPOLATOR).setDuration(200);
        animator.setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                show(view);
            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animator.start();
    }

    private void show(final View view) {
        ViewPropertyAnimator animator = view.animate().translationY(0).setInterpolator(INTERPOLATOR).setDuration(200);
        animator.setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animator) {

            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animator.start();
    }
}
