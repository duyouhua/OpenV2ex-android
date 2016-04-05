package licrafter.com.v2ex.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

import licrafter.com.v2ex.MainActivity;
import licrafter.com.v2ex.R;
import licrafter.com.v2ex.base.BaseToolbarActivity;
import licrafter.com.v2ex.model.Node;
import licrafter.com.v2ex.mvp.presenters.SelectFavNodePresenter;
import licrafter.com.v2ex.mvp.views.MvpView;

/**
 * author: shell
 * date 16/4/3 上午10:21
 **/
public class SelectFavNodeAvtivity extends BaseToolbarActivity implements MvpView {

    private SelectFavNodePresenter presenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_favnode;
    }

    @Override
    protected void attachVeiw() {
        presenter = new SelectFavNodePresenter();
        presenter.attachView(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void loadData() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void detachView() {
        presenter.detachView();
    }

    @Override
    public void onFailure(String e) {
        hideDialog();
    }

    public void getNodeListSuccess(ArrayList<Node> nodes) {
        hideDialog();
        android.util.Log.d("ljx", "node = " + nodes.size());
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
