package licrafter.com.v2ex.model;

/**
 * 通过访问api以json返回的 Topic Model
 * Created by lijinxiang on 11/5/15.
 */
public class JsonTopic {

    private String id;
    private String title;
    private String url;
    private String content;
    private String content_rendered;
    private int replies;
    private String created;
    private String last_modified;
    private String last_touched;
    private Members member;
    private Node node;

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public Members getMember() {
        return member;
    }

    public void setMember(Members member) {
        this.member = member;
    }

    public String getLast_touched() {
        return last_touched;
    }

    public void setLast_touched(String last_touched) {
        this.last_touched = last_touched;
    }

    public String getLast_modified() {
        return last_modified;
    }

    public void setLast_modified(String last_modified) {
        this.last_modified = last_modified;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public int getReplies() {
        return replies;
    }

    public void setReplies(int replies) {
        this.replies = replies;
    }

    public String getContent_rendered() {
        return content_rendered;
    }

    public void setContent_rendered(String content_rendered) {
        this.content_rendered = content_rendered;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static class Members{
        public String id;
        public String username;
        public String tagline;
        public String avatar_mini;
        public String avatar_normal;
        public String avatar_large;
    }

    public static class Node{
        public String id;
        public String title;
        public String title_alternative;
        public String url;
        public int topics;
        public String avatar_mini;
        public String avatar_normal;
        public String avatar_large;
    }
}
