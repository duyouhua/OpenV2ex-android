package licrafter.com.v2ex.model.old;

import java.io.Serializable;

/**
 * Created by shell on 15-11-15.
 */
public class MyProfitHeader implements Serializable {

    private String name;
    private String nodeCount;
    private String topicCount;
    private String favCount;

    public MyProfitHeader(String name, String nodeCount, String topicCount, String favCount) {
        this.name = name;
        this.nodeCount = nodeCount;
        this.topicCount = topicCount;
        this.favCount = favCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTopicCount() {
        return topicCount;
    }

    public void setTopicCount(String topicCount) {
        this.topicCount = topicCount;
    }

    public String getNodeCount() {
        return nodeCount;
    }

    public void setNodeCount(String nodeCount) {
        this.nodeCount = nodeCount;
    }

    public String getFavCount() {
        return favCount;
    }

    public void setFavCount(String favCount) {
        this.favCount = favCount;
    }
}
