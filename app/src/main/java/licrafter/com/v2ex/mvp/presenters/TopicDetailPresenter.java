package licrafter.com.v2ex.mvp.presenters;/**
 * Created by Administrator on 2016/3/26.
 */

import java.util.ArrayList;

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
                            ApiErrorUtil.handleError(e);
                            getView().onFailure(e);
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

    public void getCommentsList(String topicId, int page) {
        compositeSubscription.add(V2exService.getInstance().v2EX().getTopicDetailsById(topicId, 1)
                .map(new Func1<String, ArrayList<TopicComment>>() {
                    @Override
                    public ArrayList<TopicComment> call(String response) {
                        return JsoupUtil.parseComments(response);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<TopicComment>>() {
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
                    public void onNext(ArrayList<TopicComment> comments) {
                        if (getView() != null) {
                            ((TopicCommentListFragment) getView()).parseComments(comments);
                        }
                    }
                }));
    }
}
