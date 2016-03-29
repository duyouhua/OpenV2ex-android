package licrafter.com.v2ex.mvp.presenters;/**
 * Created by Administrator on 2016/3/26.
 */


import licrafter.com.v2ex.api.service.V2exService;
import licrafter.com.v2ex.model.TopicComment;
import licrafter.com.v2ex.model.TopicDetail;
import licrafter.com.v2ex.mvp.views.MvpView;
import licrafter.com.v2ex.ui.fragment.TopicCommentListFragment;
import licrafter.com.v2ex.ui.fragment.TopicDetailFragment;
import licrafter.com.v2ex.util.JsoupUtil;
import licrafter.com.v2ex.util.network.ApiErrorUtil;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * author: lijinxiang
 * date: 2016/3/26
 **/
public class TopicDetailPresenter extends BasePresenter<MvpView> {

    private boolean isLoading;

    public void getTopicDetail(String topicId) {
        compositeSubscription.add(V2exService.getInstance().v2EX().getTopicDetailsById(topicId, 1)
                .map(new Func1<String, TopicDetail>() {
                    @Override
                    public TopicDetail call(String response) {
                        TopicDetail detail = JsoupUtil.parseTopicDetail(response);
                        return detail;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TopicDetail>() {
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
                    public void onNext(TopicDetail topicDetail) {
                        if (getView() != null) {
                            ((TopicDetailFragment) getView()).parseTopicDetail(topicDetail);
                        }
                    }
                }));
    }

    public void getCommentsList(String topicId, int pageIndex, final boolean isRefresh) {
        if (isLoading) {
            return;
        }
        isLoading = true;
        compositeSubscription.add(V2exService.getInstance().v2EX().getTopicDetailsById(topicId, pageIndex)
                .map(new Func1<String, TopicComment>() {
                    @Override
                    public TopicComment call(String response) {
                        return JsoupUtil.parseComments(response);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TopicComment>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        isLoading = false;
                        if (getView() != null) {
                            getView().onFailure(ApiErrorUtil.handleError(e));
                        }
                    }

                    @Override
                    public void onNext(TopicComment topicComment) {
                        isLoading = false;
                        if (getView() != null) {
                            if (isRefresh) {
                                ((TopicCommentListFragment) getView()).parseComments(topicComment);
                            } else {
                                ((TopicCommentListFragment) getView()).onLoadMoreSuccess(topicComment);
                            }
                        }
                    }
                }));
    }
}
