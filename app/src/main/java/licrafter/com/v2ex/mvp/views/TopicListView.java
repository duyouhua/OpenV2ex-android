package licrafter.com.v2ex.mvp.views;


import licrafter.com.v2ex.model.TabContent;

/**
 * author: lijinxiang
 * date: 2016/3/18
 **/
public interface TopicListView extends MvpView{

     void onGetTopicSuccess(TabContent topics);
}
