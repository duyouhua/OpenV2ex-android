package licrafter.com.v2ex.api;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by lijinxiang on 11/5/15.
 */
public interface V2EX {
    String BASE_URL = "https://www.v2ex.com";

    /**
     * 获取tab主题列表
     *
     * @param tab
     * @return
     */
    @GET("/")
    Observable<String> getTabTopics(@Query("tab") String tab);

    /**
     * 获取最近的主题列表(和tab列表差不多，但是有多页)
     *
     * @param recent
     * @param page
     * @return
     */
    @GET("/{recent}")
    Observable<String> getRecentTopics(@Path("recent") String recent, @Query("p") int page);

    /**
     * 根据借点id获取话题列表
     *
     * @param nodeId
     * @param page
     * @return
     */
    @GET("/go/{nodeId}")
    Observable<String> getTopicsByNodeId(@Path("nodeId") String nodeId, @Query("p") int page);

    @GET("/member/{userId}/topics")
    Observable<String> getTopicsByUserId(@Path("userId") String userId);

}
