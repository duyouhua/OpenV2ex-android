package licrafter.com.v2ex.api;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by shell on 15-11-15.
 */
public interface LOGIN {

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
    @POST("/signin")
    Observable<String> login(@Field("once") int once, @Field("u") String u, @Field("p") String p, @Field("next") String next);
}
