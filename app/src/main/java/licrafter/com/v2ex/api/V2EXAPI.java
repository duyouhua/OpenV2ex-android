package licrafter.com.v2ex.api;


import java.util.List;
import licrafter.com.v2ex.model.JSONProfit;
import licrafter.com.v2ex.model.JsonTopic;
import licrafter.com.v2ex.model.Node;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by lijinxiang on 11/5/15.
 */
public interface V2EXAPI {
    String BASE_API = "https://www.v2ex.com/api";

    /**
     * 获取最热帖子
     *
     * @return
     */
    @GET("/topics/hot.json")
    Observable<List<JsonTopic>> getHotTopics();

    /**
     * 获取所有节点列表
     *
     * @return
     */
    @GET("/nodes/all.json")
    Observable<List<Node>> getNodesList();

    /**
     * 根据用户名获得帖子列表
     *
     * @param username
     * @return
     */
    @GET("/topics/show.json")
    Observable<List<JsonTopic>> getTopicsByUserName(@Query("username") String username);

    /**
     * 根据用户名获得个人详情
     *
     * @param username
     * @return
     */
    @GET("/members/show.json")
    Observable<JSONProfit> getProfitByUserName(@Query("username") String username);
}
