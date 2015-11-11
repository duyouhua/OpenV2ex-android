package licrafter.com.v2ex.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import licrafter.com.v2ex.model.TabContent;
import licrafter.com.v2ex.model.Topic;

/**
 * Created by shell on 15-11-10.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "v2ex_topics.db";
    private Dao<TabContent, Integer> tableContentDao;
    private Dao<Topic,Integer> topicDao;
    private static DatabaseHelper instance;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, TabContent.class);
            TableUtils.createTable(connectionSource, Topic.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {
        try {
            TableUtils.dropTable(connectionSource, TabContent.class, true);
            TableUtils.dropTable(connectionSource, Topic.class, true);
            onCreate(sqLiteDatabase, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 单例获取该Helper
     *
     * @param context
     * @return
     */
    public static synchronized DatabaseHelper getHelper(Context context) {
        if (instance == null) {
            synchronized (DatabaseHelper.class) {
                if (instance == null)
                    instance = new DatabaseHelper(context);
            }
        }

        return instance;
    }

    /**
     * 获取dao
     *
     * @return
     * @throws SQLException
     */
    public Dao<TabContent, Integer> getTableContentDao() {
        try {
            if (tableContentDao == null) {
                tableContentDao = getDao(TabContent.class);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tableContentDao;
    }

    /**
     * 获取dao
     *
     * @return
     * @throws SQLException
     */
    public Dao<Topic, Integer> getTopicDao() {
        try {
            if (topicDao == null) {
                topicDao = getDao(Topic.class);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return topicDao;
    }

    /**
     * 释放资源
     */
    @Override
    public void close() {
        super.close();
        tableContentDao = null;
    }
}
