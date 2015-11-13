package licrafter.com.v2ex.db;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.UpdateBuilder;

import java.sql.SQLException;

import licrafter.com.v2ex.model.Topic;

/**
 * Created by shell on 15-11-10.
 */
public class TopicDao {

    private Context context;
    private Dao<Topic, Integer> topicDao;
    private DatabaseHelper helper;

    public TopicDao(Context context) {
        this.context = context;
        try {
            helper = DatabaseHelper.getHelper(context);
            topicDao = helper.getDao(Topic.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加一个话题
     *
     * @param topic
     */
    public void addTopic(Topic topic) {
        try {
            topicDao.create(topic);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新topic的read状态
     *
     * @param topicId
     * @return
     */
    public void updateRead(String topicId, boolean read) {
        try {
            UpdateBuilder<Topic, Integer> builder = topicDao.updateBuilder();
            builder.where().eq("topicId", topicId);
            builder.updateColumnValue("read", read);
            builder.update();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
