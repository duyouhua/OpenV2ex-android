package licrafter.com.v2ex.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 该model是通过解析html得到的
 * Created by shell on 15-11-8.
 */
@DatabaseTable(tableName = "tb_table_content")
public class TabContent {

    @DatabaseField(generatedId = true)
    private int id;                         //数据库记录id
    @DatabaseField(columnName = "name")
    private String name;                    //tab名字
    @DatabaseField(columnName = "page")
    private int page;                       //当前页码
    @DatabaseField(columnName = "totalPages")
    private int totalPages;                 //总页码
    @ForeignCollectionField(eager = true)
    private Collection<Topic> topics;             //话题列表

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<Topic> getTopics() {
        List<Topic> topiclist = new ArrayList<>();
        for (Topic topic : topics) {
            topiclist.add(topic);
        }

        return topiclist;
    }

    public void setTopics(Collection<Topic> topics) {
        this.topics = topics;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
