package licrafter.com.v2ex.ui.adapter.base;/**
 * Created by Administrator on 2016/3/21.
 */

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * author: lijinxiang
 * date: 2016/3/21
 **/
public class BaseViewHolder extends RecyclerView.ViewHolder {

    private final SparseArray<View> mViews;
    private View mConvertView;

    public BaseViewHolder(View itemView) {
        super(itemView);
        this.mViews = new SparseArray<>();
        this.mConvertView = itemView;
    }


    /**
     * 通过控件ｉｄ来获得ｖｉｅｗ
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (null == view) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public TextView getTextView(int viewId){
        return getView(viewId);
    }
    public ProgressBar getProgressBar(int viewId){
        return getView(viewId);
    }
    public ImageView getImageView(int viewId){
        return getView(viewId);
    }

    public View getConvertView() {
        return this.mConvertView;
    }
}
