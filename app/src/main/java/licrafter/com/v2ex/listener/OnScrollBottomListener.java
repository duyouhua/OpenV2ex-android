package licrafter.com.v2ex.listener;/**
 * Created by Administrator on 2016/3/18.
 */

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * author: lijinxiang
 * date: 2016/3/18
 **/
public abstract class OnScrollBottomListener extends RecyclerView.OnScrollListener implements OnBottomListener {

    protected int[] lastPositions;

    protected int lastVisibleItemPosition;


    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof LinearLayoutManager) {
                lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                if (lastPositions == null) {
                    lastPositions = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                }
                lastVisibleItemPosition = ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(lastPositions)[1];
            } else if (layoutManager instanceof GridLayoutManager) {
                lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
            } else {
                throw new IllegalArgumentException("Unsupported LayoutManager used." +
                        "Valid ones are LinearLayoutManager, GridLayoutManager and StaggeredGridLayoutManager");
            }
            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            if (visibleItemCount > 0 && lastVisibleItemPosition >= (totalItemCount - 1)) {
                android.util.Log.d("v2ex","scrolled to bottom");
                onBottom();
            }
        }

    }

    @Override
    public void onBottom() {

    }
}