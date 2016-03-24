package licrafter.com.v2ex.model.realm;/**
 * Created by Administrator on 2016/3/24.
 */

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * author: lijinxiang
 * date: 2016/3/24
 **/
public class RealmTabContent extends RealmObject {

    private String name;                    //tab名字
    private int page;                       //当前页码
    private int totalPages;                 //总页码
    private RealmList<RealmTopic> topics;             //话题列表

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public RealmList<RealmTopic> getTopics() {
        return topics;
    }

    public void setTopics(RealmList<RealmTopic> topics) {
        this.topics = topics;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
