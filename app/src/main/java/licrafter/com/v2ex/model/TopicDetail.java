package licrafter.com.v2ex.model;/**
 * Created by Administrator on 2016/3/26.
 */

/**
 * author: lijinxiang
 * date: 2016/3/26
 **/
public class TopicDetail {

    public String content;
    public String createTime;
    public String repliesCount;
    public String clickCount;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getRepliesCount() {
        return repliesCount;
    }

    public void setRepliesCount(String repliesCount) {
        this.repliesCount = repliesCount;
    }

    public String getClickCount() {
        return clickCount;
    }

    public void setClickCount(String clickCount) {
        this.clickCount = clickCount;
    }
}
