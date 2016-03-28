package licrafter.com.v2ex.model;/**
 * Created by Administrator on 2016/3/28.
 */

/**
 * author: lijinxiang
 * date: 2016/3/28
 **/
public class TopicComment {

    public String avatar;
    public String userName;
    public String createTime;
    public String content;
    public String rank;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
}
