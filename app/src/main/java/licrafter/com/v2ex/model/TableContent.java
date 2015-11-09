package licrafter.com.v2ex.model;

import java.util.List;

/**
 * 该model是通过解析html得到的
 * Created by shell on 15-11-8.
 */
public class TableContent {

    private int page;                       //当前页码
    private int totalPages;                 //总页码
    private List<Topic> topics;             //话题列表

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

    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }

    public static class Topic{
        public String topicId;                    //话题id
        public String avatar;                  //发布者头像链接
        public String userId;                  //发布者id
        public String title;                   //话题标题
        public String nodeName;                 //来自节点的名字
        public String nodeId;               //来自节点的英文id
        public String lastedReviewer;          //最新回复的用户id
        public String createTime;              //发布时间
        public int replies;                    //回复次数
    }
}
