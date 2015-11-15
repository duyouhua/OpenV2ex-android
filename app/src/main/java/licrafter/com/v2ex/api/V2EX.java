package licrafter.com.v2ex.api;

import java.util.List;

import licrafter.com.v2ex.model.Node;
import licrafter.com.v2ex.model.request.LoginBody;
import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by lijinxiang on 11/5/15.
 */
public interface V2EX {
    public static String BASE_URL = "https://www.v2ex.com";

    /**
     * 获取tab主题列表
     *
     * @param tab
     * @param callback
     */
    @GET("/")
    void getTabTopics(@Query("tab") String tab, Callback<Response> callback);

    /**
     * 获取最近的主题列表(和tab列表差不多)
     *
     * @param recent
     * @param callback
     */
    @GET("/{recent}")
    void getRecentTopics(@Path("recent") String recent, @Query("p") int page, Callback<Response> callback);

    /**
     * 获取帖子详细内容和评论列表
     *
     * @param topicId
     * @param page
     * @param callback
     */
    @GET("/t/{topicId}")
    void getTopicDetails(@Path("topicId") String topicId, @Query("p") int page, Callback<Response> callback);

    /**
     * 根据节点id获取话题列表
     *
     * @param nodeId
     * @param page
     * @param callback
     */
    @GET("/go/{nodeId}")
    void getTopicsByNodeId(@Path("nodeId") String nodeId, @Query("p") int page, Callback<Response> callback);

    /**
     * 获取once字符串
     *
     * @param callback
     */
    @GET("/signin")
    void getOnceString(Callback<Response> callback);

}
