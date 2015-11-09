package licrafter.com.v2ex.api;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by lijinxiang on 11/5/15.
 */
public interface V2EX {
    public static String BASE_URL = "https://www.v2ex.com";

    @GET("/")
    void getTabTopics(@Query("tab") String tab, Callback<Response> callback);
}
