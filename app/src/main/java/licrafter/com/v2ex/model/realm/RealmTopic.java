package licrafter.com.v2ex.model.realm;/**
 * Created by Administrator on 2016/3/24.
 */

import io.realm.RealmObject;

/**
 * author: lijinxiang
 * date: 2016/3/24
 **/
public class RealmTopic extends RealmObject {

    private String topicId;                 //话题id
    private String avatar;                  //发布者头像链接
    private String userId;                  //发布者id
    private String title;                   //话题标题
    private String nodeName;                //来自节点的名字
    private String nodeId;                  //来自节点的英文id
    private String lastedReviewer;          //最新回复的用户id
    private String createTime;              //发布时间
    private int replies;                    //回复次数
    private boolean read;                   //是否已读
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

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }
}