package licrafter.com.v2ex.mvp.presenters;

import licrafter.com.v2ex.mvp.views.MvpView;
import rx.subscriptions.CompositeSubscription;

/**
 * author: lijinxiang
 * date: 2016/3/18
 **/
public class BasePresenter<T extends MvpView> implements Presenter<T> {

    private MvpView mMvpView;
    public CompositeSubscription mCompositeSubscription;

    @Override
    public void attachView(T view) {
        this.mMvpView = view;
        this.mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void detachView() {
        mMvpView = null;
        mCompositeSubscription.unsubscribe();
        mCompositeSubscription = null;
    }

    public boolean isViewAttached() {
        return mMvpView != null;
    }

    public MvpView getView() {
        return mMvpView;
    }
}
