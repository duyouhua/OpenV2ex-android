package licrafter.com.v2ex.event;/**
 * Created by Administrator on 2016/3/23.
 */

/**
 * author: lijinxiang
 * date: 2016/3/23
 **/
public class UserEvent {

    private String name;
    private String avatar;

    public UserEvent(String name, String avatar) {
        this.name = name;
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
