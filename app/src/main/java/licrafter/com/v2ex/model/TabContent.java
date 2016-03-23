package licrafter.com.v2ex.model;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 该model是通过解析html得到的
 * Created by shell on 15-11-8.
 */
public class TabContent {

    private int id;                         //数据库记录id
    private String name;                    //tab名字
    private int page;                       //当前页码
    private int totalPages;                 //总页码
    private Collection<Topic> topics;             //话题列表

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

    public ArrayList<Topic> getTopics() {
        ArrayList<Topic> topiclist = new ArrayList<>();
        for (Topic topic : topics) {
            topiclist.add(topic);
        }

        return topiclist;
    }

    public void setTopics(Collection<Topic> topics) {
        this.topics = topics;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
