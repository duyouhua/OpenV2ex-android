package licrafter.com.v2ex.mvp.presenters;/**
 * Created by Administrator on 2016/3/18.
 */

import licrafter.com.v2ex.api.service.V2exService;
import licrafter.com.v2ex.model.TabContent;
import licrafter.com.v2ex.ui.fragment.TopicListFragment;
import licrafter.com.v2ex.util.network.ApiErrorUtil;
import licrafter.com.v2ex.util.JsoupUtil;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * author: lijinxiang
 * date: 2016/3/18
 **/
public class TopicListPresenter extends BasePresenter<TopicListFragment> {


    private Observable<String> getObservable(String tabTitle, int pageIndex) {
        if (tabTitle.equals("recent")) {
            return V2exService.getInstance().v2EX().getRecentTopics(tabTitle, pageIndex);
        } else {
            return V2exService.getInstance().v2EX().getTabTopics(tabTitle);
        }
    }

    public void getTopicList(final String tabTitle, int pageIndex, final boolean isRefresh) {
        compositeSubscription.add(getObservable(tabTitle, pageIndex)
                .map(new Func1<String, TabContent>() {
                    @Override
                    public TabContent call(String s) {
                        return JsoupUtil.parse(tabTitle, s);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TabContent>() {
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
                    public void onNext(TabContent content) {
                        if (getView() != null) {
                            if (isRefresh) {
                                ((TopicListFragment) getView()).onGetTopicSuccess(content);
                            } else {
                                ((TopicListFragment) getView()).onLoadMoreSuccess(content);
                            }
                        }
                    }
                }));
    }
}
