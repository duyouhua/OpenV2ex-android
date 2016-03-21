package licrafter.com.v2ex.util;/**
 * Created by Administrator on 2016/3/18.
 */

import retrofit2.Converter;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * author: lijinxiang
 * date: 2016/3/18
 * GsonConverterFactory can not handle String Response #1151
 * https://github.com/square/retrofit/issues/1151
 * https://github.com/kamilwlf/Kamil-thoughts/blob/master/ToStringConverterFactory.java
 **/

public class StringConverter extends Converter.Factory {

    private static final MediaType MEDIA_TYPE = MediaType.parse("text/plain");


    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        if (String.class.equals(type)) {
            return new Converter<ResponseBody, String>() {
                @Override
                public String convert(ResponseBody value) throws IOException {
                    return value.string();
                }
            };
        }
        return null;
    }

//    @Override
//    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
//        if (String.class.equals(type)) {
//            return new Converter<String, RequestBody>() {
//                @Override
//                public RequestBody convert(String value) throws IOException {
//                    return RequestBody.create(MEDIA_TYPE, value);
//                }
//            };
//        }
//        return null;
//    }
}
