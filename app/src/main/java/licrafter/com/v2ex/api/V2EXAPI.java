package licrafter.com.v2ex.api;

import java.util.List;

import licrafter.com.v2ex.model.JsonTopic;
import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by lijinxiang on 11/5/15.
 */
public interface V2EXAPI {
    public static String BASE_API = "https://www.v2ex.com/api";

    @GET("/topics/hot.json")
    void getHotTopics(Callback<List<JsonTopic>> callback);
}
