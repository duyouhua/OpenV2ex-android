package licrafter.com.v2ex.api;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by shell on 15-11-15.
 */
public interface AUTH {

    String BASE_URL = "http://www.v2ex.com";

    /**
     * 获取once字符串
     *
     * @return
     */
    @GET("/signin")
    Observable<String> getOnceString();

    /**
     * 登录
     *
     * @param u
     * @param p
     * @param once
     * @return
     */
    @FormUrlEncoded
    @Headers("Referer: https://www.v2ex.com/signin")
    @POST("/signin")
    Observable<String> login(@Field("once") int once, @Field("u") String u, @Field("p") String p, @Field("next") String next);

    /**
     * 发表新的帖子
     *
     * @param title
     * @param content
     * @param node_name
     * @param once
     * @return
     */
    @FormUrlEncoded
    @POST("/new")
    Observable<String> newTopic(@Field("title") String title, @Field("content") String content, @Field("node_name") String node_name, @Field("once") String once);

    /**
     * 获取帖子详细内容和评论列表(评论分页)
     *
     * @param topicId
     * @param page
     * @return
     */
    @GET("/t/{topicId}")
    Observable<String> getTopicDetailsById(@Path("topicId") String topicId, @Query("p") int page);

    /**
     * 收藏帖子
     *
     * @param topicId
     * @param t
     * @return
     */
    @GET("/favorite/topic/{topicId}")
    Observable<String> favoriteTopic(@Header("Referer") String referer, @Path("topicId") String topicId, @Query("t") String t);

    /**
     * 取消收藏
     * @param referer
     * @param topicId
     * @param t
     * @return
     */
    @GET("/unfavorite/topic/{topicId}")
    Observable<String> unFavoriteTopic(@Header("Referer") String referer,@Path("topicId")String topicId,@Query("t")String t);


}
