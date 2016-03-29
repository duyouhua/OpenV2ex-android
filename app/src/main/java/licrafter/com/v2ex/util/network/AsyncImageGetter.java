package licrafter.com.v2ex.util.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import licrafter.com.v2ex.R;
import licrafter.com.v2ex.util.ScreenUtil;

/**
 * Created by shell on 15-11-12.
 */
public class AsyncImageGetter implements Html.ImageGetter {

    private Context context;
    private TextView container;
    private Drawable defaultDrawable;
    private int mMaxWidth;

    public AsyncImageGetter(Context context, TextView view) {
        this.context = context;
        this.container = view;
        mMaxWidth = ScreenUtil.getDisplayWidth(context) - ScreenUtil.dp(context, 100);
        defaultDrawable = context.getResources().getDrawable(R.mipmap.ic_launcher);
    }

    /**
     * 当解析到img标签的时候调用,source是获取到的链接
     *
     * @param source
     * @return
     */
    @Override
    public Drawable getDrawable(String source) {
        final URLDrawable urlDrawable = new URLDrawable();

        Glide.with(context)
                .load(source)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                        if (resource != null) {
                            int width;
                            int height;
                            if (resource.getWidth() > mMaxWidth) {
                                width = mMaxWidth;
                                height = mMaxWidth * resource.getHeight() / resource.getWidth();
                            } else {
                                width = resource.getWidth();
                                height = resource.getHeight();
                            }
                            Drawable drawable = new BitmapDrawable(context.getResources(), resource);
                            drawable.setBounds(0, 0, width, height);
                            urlDrawable.setBounds(0, 0, width, height);
                            urlDrawable.mDrawable = drawable;
                            container.setText(container.getText());
                        }
                    }
                });
        return urlDrawable;
    }

    public class URLDrawable extends BitmapDrawable {

        public Drawable mDrawable;

        @Override
        public void draw(Canvas canvas) {
            if (mDrawable != null) {
                mDrawable.draw(canvas);
            } else {
                defaultDrawable.draw(canvas);
            }
        }
    }

}
