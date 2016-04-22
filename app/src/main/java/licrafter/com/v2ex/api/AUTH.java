package licrafter.com.v2ex.api;

import java.util.HashMap;

import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import rx.Observable;

/**
 * Created by shell on 15-11-15.
 */
public interface AUTH {

    String BASE_URL = "https://www.v2ex.com";

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
     * @return
     */
    @FormUrlEncoded
    @Headers("Referer: https://www.v2ex.com/signin")
    @POST("/signin")
    Observable<String> login(@FieldMap HashMap<String, String> hashMap);

    /**
     * 获取注册once
     *
     * @return
     */
    @GET("/signup")
    Observable<String> getRegisterCode();

    /**
     * get register code image
     *
     * @param once
     * @return
     */
    @Streaming
    @GET("_captcha")
    Observable<ResponseBody> getCodeImage(@Query("once") String once);

    /**
     * register
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("/signup")
    Observable<String> register(@FieldMap HashMap<String, String> map);

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
     *
     * @param referer
     * @param topicId
     * @param t
     * @return
     */
    @GET("/unfavorite/topic/{topicId}")
    Observable<String> unFavoriteTopic(@Header("Referer") String referer, @Path("topicId") String topicId, @Query("t") String t);


}
