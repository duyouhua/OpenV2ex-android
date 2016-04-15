package licrafter.com.v2ex.mvp.presenters;/**
 * Created by Administrator on 2016/4/15.
 */

import java.util.ArrayList;

import licrafter.com.v2ex.api.service.V2exApiService;
import licrafter.com.v2ex.api.service.V2exService;
import licrafter.com.v2ex.model.ProfitTopic;
import licrafter.com.v2ex.model.old.JSONProfit;
import licrafter.com.v2ex.ui.activity.ProfitActivity;
import licrafter.com.v2ex.util.JsoupUtil;
import licrafter.com.v2ex.util.network.ApiErrorUtil;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * author: lijinxiang
 * date: 2016/4/15
 **/
public class ProfitPresenter extends BasePresenter<ProfitActivity> {

    public void getProfitHeader(String user) {
        compositeSubscription.add(V2exApiService.getInstance().v2exApi()
                .getProfitByUserName(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JSONProfit>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (getView() != null) {
                            getView().onFailure(ApiErrorUtil.handleError(e));
                        }
                    }

                    @Override
                    public void onNext(JSONProfit jsonProfit) {
                        if (getView() != null) {
                            ((ProfitActivity) getView()).parseProfitHeader(jsonProfit);
                        }
                    }
                }));
    }

    public void getTopicsByUserId(String userId) {
        compositeSubscription.add(V2exService.getInstance().v2EX()
                .getTopicsByUserId(userId)
                .map(new Func1<String, ArrayList<ProfitTopic>>() {
                    @Override
                    public ArrayList<ProfitTopic> call(String response) {
                        return JsoupUtil.parseProfitTopic(response);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<ProfitTopic>>() {
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
                    public void onNext(ArrayList<ProfitTopic> profitTopics) {
                        if (getView()!=null){
                            ((ProfitActivity)getView()).parseProfitTopic(profitTopics);
                        }
                    }
                }));
    }
}
