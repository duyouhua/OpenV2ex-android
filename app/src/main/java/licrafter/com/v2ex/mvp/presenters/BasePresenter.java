package licrafter.com.v2ex.mvp.presenters;

import licrafter.com.v2ex.mvp.views.MvpView;
import rx.subscriptions.CompositeSubscription;

/**
 * author: lijinxiang
 * date: 2016/3/18
 **/
public class BasePresenter<T extends MvpView> implements Presenter<T> {

    private MvpView mvpView;
    public CompositeSubscription compositeSubscription;

    @Override
    public void attachView(T view) {
        this.mvpView = view;
        this.compositeSubscription = new CompositeSubscription();
    }

    @Override
    public void detachView() {
        mvpView = null;
        compositeSubscription.unsubscribe();
        compositeSubscription = null;
    }

    public boolean isViewAttached() {
        return mvpView != null;
    }

    public MvpView getView() {
        return mvpView;
    }
}
