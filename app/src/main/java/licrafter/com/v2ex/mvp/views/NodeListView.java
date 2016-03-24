package licrafter.com.v2ex.mvp.views;/**
 * Created by Administrator on 2016/3/24.
 */

import java.util.ArrayList;

import licrafter.com.v2ex.model.Node;

/**
 * author: lijinxiang
 * date: 2016/3/24
 **/
public interface NodeListView extends MvpView {
    public void onGetNodeListSuccess(ArrayList<Node> nodes);
}
