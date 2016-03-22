package licrafter.com.v2ex.mvp.presenters;/**
 * Created by Administrator on 2016/3/18.
 */

import java.util.ArrayList;

import licrafter.com.v2ex.api.service.V2exService;
import licrafter.com.v2ex.model.Topic;
import licrafter.com.v2ex.ui.fragment.TopicListFragment;
import licrafter.com.v2ex.util.network.ApiErrorUtil;
import licrafter.com.v2ex.util.JsoupUtil;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * author: lijinxiang
 * date: 2016/3/18
 **/
public class TopicListPresenter extends BasePresenter<TopicListFragment> {

    public void getTopicList(final String tabTitle) {
        compositeSubscription.add(V2exService.getInstance().v2EX().getTabTopics(tabTitle)
                .map(new Func1<String, ArrayList<Topic>>() {
                    @Override
                    public ArrayList<Topic> call(String s) {
                        return JsoupUtil.parse(tabTitle, s).getTopics();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<Topic>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (getView() != null) {
                            ApiErrorUtil.handleError(e);
                            getView().onFailure(e);
                        }
                    }

                    @Override
                    public void onNext(ArrayList<Topic> topics) {
                        if (getView() != null) {
                            ((TopicListFragment) getView()).onGetTopicSuccess(topics);
                        }
                    }
                }));
    }
}
