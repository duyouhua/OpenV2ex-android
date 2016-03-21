package licrafter.com.v2ex.mvp.views;

import java.util.ArrayList;

import licrafter.com.v2ex.model.Topic;

/**
 * author: lijinxiang
 * date: 2016/3/18
 **/
public interface TopicListView extends MvpView{

     void onGetTopicSuccess(ArrayList<Topic> topics);
}
