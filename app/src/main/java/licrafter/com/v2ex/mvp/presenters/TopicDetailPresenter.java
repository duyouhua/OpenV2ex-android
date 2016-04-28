package licrafter.com.v2ex.mvp.presenters;/**
 * Created by Administrator on 2016/3/26.
 */


import licrafter.com.v2ex.api.service.AuthService;
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

    private boolean mIsLoading;

    public void getTopicDetail(String topicId) {
        mCompositeSubscription.add(AuthService.getInstance().auth().getTopicDetailsById(topicId, 1)
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
        if (mIsLoading) {
            return;
        }
        mIsLoading = true;
        mCompositeSubscription.add(AuthService.getInstance().auth().getTopicDetailsById(topicId, pageIndex)
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
                        mIsLoading = false;
                        if (getView() != null) {
                            getView().onFailure(ApiErrorUtil.handleError(e));
                        }
                    }

                    @Override
                    public void onNext(TopicComment topicComment) {
                        mIsLoading = false;
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

    public void favoriteTopic(String topicId, String csrfToken) {
        String referer = "http://www.v2ex.com/t/" + topicId;
        mCompositeSubscription.add(AuthService.getInstance().auth().favoriteTopic(referer, topicId, csrfToken)
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String response) {
                        return JsoupUtil.parseCsrfToken(response);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
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
                    public void onNext(String token) {
                        if (getView() != null) {
                            ((TopicDetailFragment) getView()).parseFavorite(token, true);
                        }
                    }
                }));
    }

    public void unFavoriteTopic(String topicId, String scrfToken) {
        String referer = "http://www.v2ex.com/t/" + topicId;
        mCompositeSubscription.add(AuthService.getInstance().auth().unFavoriteTopic(referer, topicId, scrfToken)
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String response) {
                        return JsoupUtil.parseCsrfToken(response);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
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
                    public void onNext(String token) {
                        if (getView() != null) {
                            ((TopicDetailFragment) getView()).parseFavorite(token, false);
                        }
                    }
                }));
    }
}
