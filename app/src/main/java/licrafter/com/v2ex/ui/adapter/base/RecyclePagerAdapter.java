package licrafter.com.v2ex.ui.adapter.base;/**
 * Created by Administrator on 2016/4/12.
 */

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;
import java.util.Queue;

/**
 * author: lijinxiang
 * date: 2016/4/12
 **/
public abstract class RecyclePagerAdapter<VH extends RecyclePagerAdapter.ViewHolder>
        extends PagerAdapter {

    private final Queue<VH> cache = new LinkedList<>();
    private final SparseArray<VH> attached = new SparseArray<>();

    public abstract VH onCreateViewHolder(@NonNull ViewGroup container);

    public abstract void onBindViewHolder(@NonNull VH holder, int position);

    public void onRecycleViewHolder(@NonNull VH holder) {

    }

    public VH getViewHolder(int position) {
        return attached.get(position);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        VH holder = cache.poll();
        if (holder == null) {
            holder = onCreateViewHolder(container);
        }
        attached.put(position, holder);
        container.addView(holder.itemView, null);
        onBindViewHolder(holder, position);
        return holder;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        VH holder = (VH) object;
        attached.remove(position);
        container.removeView(holder.itemView);
        cache.offer(holder);
        onRecycleViewHolder(holder);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        ViewHolder holder = (ViewHolder) object;
        return holder.itemView == view;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public static class ViewHolder {
        public final View itemView;

        public ViewHolder(@NonNull View itemView) {
            this.itemView = itemView;
        }
    }
}
