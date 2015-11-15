package licrafter.com.v2ex.api;

import licrafter.com.v2ex.model.request.LoginBody;
import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by shell on 15-11-15.
 */
public interface LOGIN {

    public static String BASE_URL = "https://www.v2ex.com";

    /**
     * 登陆
     *
     * @param callback
     */
    @FormUrlEncoded
    @POST("/signin")
    void login(@Field("u") String u, @Field("p") String p, @Field("once") String once, @Field("next") String next, Callback<Response> callback);
}
