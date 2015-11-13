package licrafter.com.v2ex.model;

import java.io.Serializable;

/**
 * Created by shell on 15-11-13.
 */
public class SeriableTopic implements Serializable {
    private String avatar;
    private String title;
    private String userName;
    private String topicId;
    private String nodeName;
    private String nodeId;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public SeriableTopic(Topic topic){
        this.avatar = topic.getAvatar();
        this.nodeId = topic.getNodeId();
        this.nodeName = topic.getNodeName();
        this.title = topic.getTitle();
        this.userName = topic.getUserId();
        this.topicId = topic.getTopicId();
    }
}
