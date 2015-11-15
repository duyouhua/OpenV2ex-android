package licrafter.com.v2ex.api;

import java.util.List;

import licrafter.com.v2ex.model.JsonTopic;
import licrafter.com.v2ex.model.Node;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by lijinxiang on 11/5/15.
 */
public interface V2EXAPI {
    public static String BASE_API = "https://www.v2ex.com/api";

    /**
     * 获取最热帖子
     *
     * @param callback
     */
    @GET("/topics/hot.json")
    void getHotTopics(Callback<List<JsonTopic>> callback);

    /**
     * 获取所有节点列表
     *
     * @param callback
     */
    @GET("/nodes/all.json")
    void getNodesList(Callback<List<Node>> callback);

    /**
     * 根据用户名获得帖子列表
     * @param username
     * @param callback
     */
    @GET("/topics/show.json")
    void getTopicsByUserName(@Query("username") String username, Callback<List<JsonTopic>> callback);
}
