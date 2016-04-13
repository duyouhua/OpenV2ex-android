package licrafter.com.v2ex.event;/**
 * Created by Administrator on 2016/4/13.
 */

/**
 * author: lijinxiang
 * date: 2016/4/13
 **/
public class ImageClickEvent {

    private String url;

    public ImageClickEvent(String url){
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
