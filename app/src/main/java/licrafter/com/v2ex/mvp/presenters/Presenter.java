package licrafter.com.v2ex.mvp.presenters;

import licrafter.com.v2ex.mvp.views.MvpView;

/**
 * author: lijinxiang
 * date: 2016/3/18
 **/
public interface Presenter<V extends MvpView> {

    void attachView(V view);

    void detachView();
}
