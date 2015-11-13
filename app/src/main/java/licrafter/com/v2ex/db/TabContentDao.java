package licrafter.com.v2ex.db;

import android.content.Context;
import android.widget.Toast;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.UpdateBuilder;

import java.sql.SQLException;
import java.util.List;

import licrafter.com.v2ex.model.TabContent;
import licrafter.com.v2ex.model.Topic;

/**
 * Created by shell on 15-11-10.
 */
public class TabContentDao {

    private Context context;
    private DatabaseHelper helper;
    private Dao<TabContent, Integer> tabContentDao;
    private TopicDao topicDao;

    public TabContentDao(Context context) {
        this.context = context;
        topicDao = new TopicDao(context);
        try {
            helper = DatabaseHelper.getHelper(context);
            tabContentDao = helper.getDao(TabContent.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加一个TabContent到数据库
     *
     * @param tab
     */
    public void addTabContent(TabContent tab) {
        try {
            if (!isTabExists(tab.getName())) {
                //如果数据库中没有该tab的记录
                tabContentDao.create(tab);
                for (Topic topic : tab.getTopics()) {
                    topic.setContent(tab);
                    topicDao.addTopic(topic);
                }
            } else {
                //如果数据库中已经有该tab的记录
                updatePages(tab.getName(), "page", tab.getPage());
                updatePages(tab.getName(), "totalPages", tab.getTotalPages());
                TabContent content = getTabContent(tab.getName());
                for (Topic topic : tab.getTopics()) {
                    topic.setContent(content);
                    topicDao.addTopic(topic);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * tabContent是否存在
     *
     * @return
     */
    public boolean isTabExists(String tab) {
        List<TabContent> tabs;
        try {
            tabs = tabContentDao.queryBuilder().where().eq("name", tab).query();
            if (tabs.size() >= 1) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public TabContent getTabContent(String tab) {

        try {
            TabContent content = tabContentDao.queryBuilder().where().eq("name", tab).queryForFirst();
            return content;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 更新page totalPages
     *
     * @param tab
     * @param col
     * @param value
     */
    public void updatePages(String tab, String col, Integer value) {
        try {
            UpdateBuilder<TabContent, Integer> updateBuilder = tabContentDao.updateBuilder();
            updateBuilder.where().eq("name", tab);
            updateBuilder.updateColumnValue(col, value);
            updateBuilder.update();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
