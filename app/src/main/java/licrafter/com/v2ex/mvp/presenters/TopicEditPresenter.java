package licrafter.com.v2ex.mvp.presenters;/**
 * Created by Administrator on 2016/3/23.
 */

import java.util.ArrayList;

import licrafter.com.v2ex.api.service.AuthService;
import licrafter.com.v2ex.api.service.V2exApiService;
import licrafter.com.v2ex.model.Node;
import licrafter.com.v2ex.model.response.CreateTopicResponse;
import licrafter.com.v2ex.ui.activity.TopicEditActivity;
import licrafter.com.v2ex.ui.widget.searchView.SearchItem;
import licrafter.com.v2ex.util.JsoupUtil;
import licrafter.com.v2ex.util.network.ApiErrorUtil;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * author: lijinxiang
 * date: 2016/3/23
 **/
public class TopicEditPresenter extends BasePresenter<TopicEditActivity> {

    public void postTopic(final String title, final String content, final String nodeId) {
        compositeSubscription.add(AuthService.getInstance().auth()
                .getOnceString()
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String response) {
                        return JsoupUtil.parseOnce(response);
                    }
                }).flatMap(new Func1<String, Observable<String>>() {
                    @Override
                    public Observable<String> call(String once) {
                        return AuthService.getInstance().auth()
                                .newTopic(title, content, nodeId, once);
                    }
                })
                .map(new Func1<String, CreateTopicResponse>() {
                    @Override
                    public CreateTopicResponse call(String response) {
                        return JsoupUtil.parseNewTopicResponse(response);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CreateTopicResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (getView()!=null){
                            ApiErrorUtil.handleError(e);
                            getView().onFailure(e.toString());
                        }
                    }

                    @Override
                    public void onNext(CreateTopicResponse response) {
                        if (getView()!=null){
                            ((TopicEditActivity)getView()).onCreatTopicSuccess(response);
                        }
                    }
                }));
    }

    public void getSearchList() {
        compositeSubscription.add(V2exApiService.getInstance().v2exApi().getNodesList()
                .map(new Func1<ArrayList<Node>, ArrayList<SearchItem>>() {
                    @Override
                    public ArrayList<SearchItem> call(ArrayList<Node> nodes) {
                        ArrayList<SearchItem> searchItems = new ArrayList<SearchItem>();
                        for (Node node : nodes) {
                            searchItems.add(new SearchItem(node.getTitle(), node.getName()));
                        }
                        return searchItems;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<SearchItem>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (getView()!=null){
                            ApiErrorUtil.handleError(e);
                            getView().onFailure(e.toString());
                        }
                    }

                    @Override
                    public void onNext(ArrayList<SearchItem> searchItems) {
                        if (getView()!=null){
                            ((TopicEditActivity)getView()).getSearchListSuccess(searchItems);
                        }
                    }
                }));
    }
}
