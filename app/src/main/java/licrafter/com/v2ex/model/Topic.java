package licrafter.com.v2ex.model;


/**
 * 通过解析html得到的Topci model
 * Created by shell on 15-11-10.
 */
public class Topic {
    private String topicId;                 //话题id
    private String avatar;                  //发布者头像链接
    private String userId;                  //发布者id
    private String title;                   //话题标题
    private String nodeName;                //来自节点的名字
    private String nodeId;                  //来自节点的英文id
    private String lastedReviewer;          //最新回复的用户id
    private String createTime;              //发布时间
    private int replies;                    //回复次数
    private boolean hasRead;                   //是否已读
    private String tabName;

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String id) {
        this.topicId = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getLastedReviewer() {
        return lastedReviewer;
    }

    public void setLastedReviewer(String lastedReviewer) {
        this.lastedReviewer = lastedReviewer;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getReplies() {
        return replies;
    }

    public void setReplies(int replies) {
        this.replies = replies;
    }

    public boolean isHasRead() {
        return hasRead;
    }

    public void setHasRead(boolean hasRead) {
        this.hasRead = hasRead;
    }

    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }
}