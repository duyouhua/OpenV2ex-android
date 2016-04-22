package licrafter.com.v2ex.model.response;/**
 * Created by Administrator on 2016/4/22.
 */

/**
 * author: lijinxiang
 * date: 2016/4/22
 **/
public class LoginFormInfo {

    private String nameKey;
    private String pswKey;
    private String once;

    public String getOnce() {
        return once;
    }

    public void setOnce(String once) {
        this.once = once;
    }

    public String getNameKey() {
        return nameKey;
    }

    public void setNameKey(String nameKey) {
        this.nameKey = nameKey;
    }

    public String getPswKey() {
        return pswKey;
    }

    public void setPswKey(String pswKey) {
        this.pswKey = pswKey;
    }
}
