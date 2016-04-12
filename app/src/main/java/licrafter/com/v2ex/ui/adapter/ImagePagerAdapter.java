package licrafter.com.v2ex.ui.adapter;/**
 * Created by Administrator on 2016/4/12.
 */

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import licrafter.com.v2ex.ui.adapter.base.RecyclePagerAdapter;
import licrafter.com.v2ex.ui.widget.gestureView.views.GestureImageView;
import licrafter.com.v2ex.ui.widget.gestureView.views.interfaces.GestureView;

/**
 * author: lijinxiang
 * date: 2016/4/12
 **/
public class ImagePagerAdapter extends RecyclePagerAdapter<ImagePagerAdapter.ViewHolder> {

    private final ViewPager viewPager;
    private final String[] imageUrls;
    private final GestureSettingsSetupListener setupListener;

    public ImagePagerAdapter(ViewPager viewPager, String[] imageUrls, GestureSettingsSetupListener setupListener) {
        this.viewPager = viewPager;
        this.imageUrls = imageUrls;
        this.setupListener = setupListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup container) {
        ViewHolder holder = new ViewHolder(container);
        holder.image.getController().enableScrollInViewPager(viewPager);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (setupListener != null) {
            setupListener.onSetupGestureView(holder.image);
        }
        Glide.with(holder.image.getContext()).load(imageUrls[position])
                .into(holder.image);
    }

    public static GestureImageView getImage(RecyclePagerAdapter.ViewHolder holder) {
        return ((ViewHolder) holder).image;
    }

    @Override
    public int getCount() {
        return imageUrls.length;
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
