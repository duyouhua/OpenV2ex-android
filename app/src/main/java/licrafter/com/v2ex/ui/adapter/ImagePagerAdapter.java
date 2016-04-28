package licrafter.com.v2ex.ui.adapter;/**
 * Created by Administrator on 2016/4/12.
 */

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

import java.util.ArrayList;

import licrafter.com.v2ex.ui.adapter.base.RecyclePagerAdapter;
import licrafter.com.v2ex.ui.widget.gestureView.views.GestureImageView;
import licrafter.com.v2ex.ui.widget.gestureView.views.interfaces.GestureView;
import licrafter.com.v2ex.util.GlideUtil;

/**
 * author: lijinxiang
 * date: 2016/4/12
 **/
public class ImagePagerAdapter extends RecyclePagerAdapter<ImagePagerAdapter.ViewHolder> {

    private final ViewPager mViewPager;
    private ArrayList<String> mImageUrls;
    private final GestureSettingsSetupListener mSetupListener;

    public ImagePagerAdapter(ViewPager viewPager, GestureSettingsSetupListener setupListener) {
        this.mViewPager = viewPager;
        this.mImageUrls = new ArrayList<>();
        this.mSetupListener = setupListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup container) {
        ViewHolder holder = new ViewHolder(container);
        holder.image.getController().enableScrollInViewPager(mViewPager);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (mSetupListener != null) {
            mSetupListener.onSetupGestureView(holder.image);
        }
        GlideUtil.loadResource(mImageUrls.get(position), holder.image);
    }

    public static GestureImageView getImage(RecyclePagerAdapter.ViewHolder holder) {
        return ((ViewHolder) holder).image;
    }

    public void setData(ArrayList<String> urls) {
        this.mImageUrls = urls;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mImageUrls.size();
    }

    public void setCurrentItem(String currentItem) {
        mViewPager.setCurrentItem(mImageUrls.indexOf(currentItem));
    }

    static class ViewHolder extends RecyclePagerAdapter.ViewHolder {
        final GestureImageView image;

        ViewHolder(ViewGroup container) {
            super(new GestureImageView(container.getContext()));
            image = (GestureImageView) itemView;
        }
    }

    public interface GestureSettingsSetupListener {
        void onSetupGestureView(GestureView view);
    }

}
