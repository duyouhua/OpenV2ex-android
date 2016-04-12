package licrafter.com.v2ex.ui.widget.gestureView.transition;

import android.support.annotation.NonNull;

public abstract class SimpleViewsTracker implements ViewsTracker<Integer> {

    @Override
    public int getPositionForId(@NonNull Integer id) {
        return id;
    }

    @Override
    public Integer getIdForPosition(int position) {
        return position;
    }

}
