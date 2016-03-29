package licrafter.com.v2ex.mvp.presenters;/**
 * Created by Administrator on 2016/3/24.
 */

import java.util.ArrayList;

import licrafter.com.v2ex.api.service.V2exApiService;
import licrafter.com.v2ex.model.Node;
import licrafter.com.v2ex.ui.fragment.NodeListFragment;
import licrafter.com.v2ex.util.network.ApiErrorUtil;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * author: lijinxiang
 * date: 2016/3/24
 **/
public class NodeListPresenter extends BasePresenter<NodeListFragment> {

    public void getNodeList() {
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
                            getView().onFailure(ApiErrorUtil.handleError(e));
                        }
                    }

                    @Override
                    public void onNext(ArrayList<Node> nodes) {
                        ((NodeListFragment)getView()).onGetNodeListSuccess(nodes);
                    }
                }));
    }
}
