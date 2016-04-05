package licrafter.com.v2ex.model.response;

import licrafter.com.v2ex.model.Topic;

/**
 * author: shell
 * date 16/4/3 下午6:04
 **/
public class CreateTopicResponse {

    private String message;
    private String url;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
