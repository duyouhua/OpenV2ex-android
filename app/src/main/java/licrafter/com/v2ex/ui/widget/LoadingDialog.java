package licrafter.com.v2ex.ui.widget;/**
 * Created by Administrator on 2016/3/30.
 */

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import licrafter.com.v2ex.R;

/**
 * author: lijinxiang
 * date: 2016/3/30
 **/
public class LoadingDialog extends Dialog {
    public LoadingDialog(Context context) {
        super(context,R.style.LoadingDialogStyle);
        View rootView = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);
        ImageView imageView = (ImageView) rootView.findViewById(R.id.loading_img);
        AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground();
        animationDrawable.start();
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        setContentView(rootView);
    }
}
