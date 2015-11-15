package licrafter.com.v2ex.model.request;

/**
 * Created by shell on 15-11-15.
 */
public class LoginBody {

    private String next;    //跳转路径
    private String once;    //once字符串
    private String p;       //密码
    private String u;       //用户名

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getU() {
        return u;
    }

    public void setU(String u) {
        this.u = u;
    }

    public String getOnce() {
        return once;
    }

    public void setOnce(String once) {
        this.once = once;
    }

    public String getP() {
        return p;
    }

    public void setP(String p) {
        this.p = p;
    }
}
