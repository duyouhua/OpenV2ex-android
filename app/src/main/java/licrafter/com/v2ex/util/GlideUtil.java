package licrafter.com.v2ex.util;/**
 * Created by Administrator on 2016/4/13.
 */

import android.util.DisplayMetrics;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;

import licrafter.com.v2ex.R;

/**
 * author: lijinxiang
 * date: 2016/4/13
 **/
public class GlideUtil {

    public static void loadResource(String url, ImageView imageView) {
        DisplayMetrics metrics = imageView.getResources().getDisplayMetrics();
        final int displayWidth = metrics.widthPixels;
        final int displayHeight = metrics.heightPixels;

        Glide.with(imageView.getContext())
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(R.drawable.anim_loading)
                .into(new GlideDrawableImageViewTarget(imageView) {
                    @Override
                    public void getSize(SizeReadyCallback cb) {
                        super.getSize(cb);
                        cb.onSizeReady(displayWidth / 2, displayHeight / 2);
                    }
                });
    }
}
