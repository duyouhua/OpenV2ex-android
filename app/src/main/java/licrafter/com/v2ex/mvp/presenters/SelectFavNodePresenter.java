package licrafter.com.v2ex.mvp.presenters;

import java.util.ArrayList;

import licrafter.com.v2ex.api.service.V2exApiService;
import licrafter.com.v2ex.model.Node;
import licrafter.com.v2ex.ui.activity.SelectFavNodeAvtivity;
import licrafter.com.v2ex.util.network.ApiErrorUtil;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * author: shell
 * date 16/4/3 上午10:23
 **/
public class SelectFavNodePresenter extends BasePresenter<SelectFavNodeAvtivity> {

    public void getNodes() {
        compositeSubscription.add(V2exApiService.getInstance().v2exApi().getNodesList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<Node>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (getView()!=null){
                            getView().onFailure(e.toString());
                            ApiErrorUtil.handleError(e);
                        }
                    }

                    @Override
                    public void onNext(ArrayList<Node> nodes) {
                        if (getView()!=null){
                            ((SelectFavNodeAvtivity)getView()).getNodeListSuccess(nodes);
                        }
                    }
                }));

    }

}
