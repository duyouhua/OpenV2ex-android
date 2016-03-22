package licrafter.com.v2ex.model;/**
 * Created by Administrator on 2016/3/22.
 */

/**
 * author: lijinxiang
 * date: 2016/3/22
 **/
public class LoginResult {

    private String userId;
    private String userAvatar;

    public LoginResult(String userId, String userAvatar) {
        this.userId = userId;
        this.userAvatar = userAvatar;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }
}
