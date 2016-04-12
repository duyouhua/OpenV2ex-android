package licrafter.com.v2ex.ui.widget.gestureView.views.interfaces;

import licrafter.com.v2ex.ui.widget.gestureView.animation.ViewPositionAnimator;

/**
 * Common interface for views supporting position animation.
 */
public interface AnimatorView {

    /**
     * @return {@link ViewPositionAnimator} instance to control animation from other view position.
     */
    ViewPositionAnimator getPositionAnimator();

}
