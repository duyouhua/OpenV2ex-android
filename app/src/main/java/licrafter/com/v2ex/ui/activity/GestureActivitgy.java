package licrafter.com.v2ex.ui.activity;/**
 * Created by Administrator on 2016/4/12.
 */

import android.os.Bundle;

import com.bumptech.glide.Glide;

import butterknife.Bind;
import licrafter.com.v2ex.R;
import licrafter.com.v2ex.base.BaseAppCompatActivity;
import licrafter.com.v2ex.ui.widget.gestureView.views.GestureImageView;

/**
 * author: lijinxiang
 * date: 2016/4/12
 **/
public class GestureActivitgy extends BaseAppCompatActivity {
    @Bind(R.id.gestureview)
    GestureImageView gestureImageView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_gesture;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {

    }

    @Override
    protected void attachVeiw() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        Glide.with(this).load("http://i.imgur.com/EBTYNhV.png")
                .into(gestureImageView);
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void detachView() {

    }
}
