package licrafter.com.v2ex.ui.adapter.AnimationRecyclerViewAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by shell on 15-11-9.
 */
public class AnimationRecyclerAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter {

    private int mAnimationPosition = -1;

    public AnimationRecyclerAdapter() {

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof AnimationViewHolder) {
            AnimationViewHolder vh = (AnimationViewHolder) holder;
            if (mAnimationPosition < position) {
                vh.animatorSet.start();
                mAnimationPosition = position;
            } else {
                mAnimationPosition = position;
            }
        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public int getmAnimationPosition() {
        return mAnimationPosition;
    }

    public void setmAnimationPosition(int mAnimationPosition) {
        this.mAnimationPosition = mAnimationPosition;
    }

    public void resetAnimationPosition() {
        mAnimationPosition = -1;
    }
}
