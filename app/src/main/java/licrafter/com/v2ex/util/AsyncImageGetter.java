package licrafter.com.v2ex.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import licrafter.com.v2ex.R;

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
        mMaxWidth = ScreenUtil.getDisplayWidth(context) -  ScreenUtil.dp(context, 100);
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
        Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                if (bitmap != null) {
                    int width;
                    int height;
                    if (bitmap.getWidth() > mMaxWidth) {
                        width = mMaxWidth;
                        height = mMaxWidth * bitmap.getHeight() / bitmap.getWidth();
                    } else {
                        width = bitmap.getWidth();
                        height = bitmap.getHeight();
                    }
                    Drawable drawable = new BitmapDrawable(context.getResources(), bitmap);
                    drawable.setBounds(0, 0, width, height);
                    urlDrawable.setBounds(0, 0, width, height);
                    urlDrawable.mDrawable = drawable;
                    //reset text to invalidate.
                    container.setText(container.getText());
                }
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }

        };
        Picasso.with(context).load(source).into(target);
        return urlDrawable;
    }

    public class URLDrawable extends BitmapDrawable {

        public Drawable mDrawable;

        @Override
        public void draw(Canvas canvas) {
            if (mDrawable!=null){
                mDrawable.draw(canvas);
            }else {
                defaultDrawable.draw(canvas);
            }
        }
    }

}
