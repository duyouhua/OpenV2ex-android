package licrafter.com.v2ex.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * 通过解析html得到的Topci model
 * Created by shell on 15-11-10.
 */
@DatabaseTable(tableName = "tb_topic")
public class Topic {
    @DatabaseField(generatedId = true)
    private int id;                         //数据库记录id,自增
    @DatabaseField(columnName = "topicId")
    private String topicId;                 //话题id
    @DatabaseField(columnName = "avatar")
    private String avatar;                  //发布者头像链接
    @DatabaseField(columnName = "userId")
    private String userId;                  //发布者id
    @DatabaseField(columnName = "title")
    private String title;                   //话题标题
    @DatabaseField(columnName = "nodeName")
    private String nodeName;                //来自节点的名字
    @DatabaseField(columnName = "nodeId")
    private String nodeId;                  //来自节点的英文id
    @DatabaseField(columnName = "lastedReviewer")
    private String lastedReviewer;          //最新回复的用户id
    @DatabaseField(columnName = "createTime")
    private String createTime;              //发布时间
    @DatabaseField(columnName = "replies")
    private int replies;                    //回复次数
    @DatabaseField(columnName = "read")
    private boolean read;                   //是否已读
    @DatabaseField(columnName = "tabName")  //所在tab名字
    private String tabName;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private TabContent content;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public TabContent getContent() {
        return content;
    }

    public void setContent(TabContent content) {
        this.content = content;
    }

    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }
}