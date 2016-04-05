package licrafter.com.v2ex.model.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import licrafter.com.v2ex.model.Node;

/**
 * author: shell
 * date 16/4/3 上午10:40
 **/
public class RealmNode extends RealmObject {

    @PrimaryKey
    private String id;
    private String name;
    private String url;
    private String title;
    private String title_alternative;
    private String topics;
    private String header;
    private String footer;
    private String created;

    public RealmNode() {

    }

    public RealmNode(Node node) {
        this.id = node.getId();
        this.name = node.getName();
        this.url = node.getUrl();
        this.title = node.getTitle();
        this.title_alternative = node.getTitle_alternative();
        this.topics = node.getTopics();
        this.header = node.getHeader();
        this.footer = node.getFooter();
        this.created = node.getCreated();
    }

    public Node toNode() {
        Node node = new Node();
        node.setId(id);
        node.setName(name);
        node.setUrl(url);
        node.setTitle(title);
        node.setTitle_alternative(title_alternative);
        node.setTopics(topics);
        node.setHeader(header);
        node.setFooter(footer);
        node.setCreated(created);
        return node;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle_alternative() {
        return title_alternative;
    }

    public void setTitle_alternative(String title_alternative) {
        this.title_alternative = title_alternative;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getTopics() {
        return topics;
    }

    public void setTopics(String topics) {
        this.topics = topics;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}
