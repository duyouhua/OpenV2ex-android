package licrafter.com.v2ex.model.Response;

import java.util.List;

/**
 * Created by shell on 15-11-12.
 */
public class TopicResponse {

    private TopicDetail detail;
    private List<Comment> comments;

    public TopicDetail getDetail() {
        return detail;
    }

    public void setDetail(TopicDetail detail) {
        this.detail = detail;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public static class Comment{
        public String avatar;
        public String userName;
        public String createTime;
        public String content;
        public String rank;
    }

    public static class TopicDetail{
        public String content;
        public String createTime;
        public String repliesCount;
        public String clickCount;
    }
}
