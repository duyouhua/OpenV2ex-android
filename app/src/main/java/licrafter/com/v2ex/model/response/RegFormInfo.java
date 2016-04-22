package licrafter.com.v2ex.model.response;/**
 * Created by Administrator on 2016/4/22.
 */

/**
 * author: lijinxiang
 * date: 2016/4/22
 **/
public class RegFormInfo {

    private String nameKey;
    private String pswKey;
    private String emailKey;
    private String codeKey;

    private String nameValue;
    private String pswValue;
    private String emailValue;
    private String codeValue;

    private String once;

    public String getNameKey() {
        return nameKey;
    }

    public void setNameKey(String nameKey) {
        this.nameKey = nameKey;
    }

    public String getCodeKey() {
        return codeKey;
    }

    public void setCodeKey(String codeKey) {
        this.codeKey = codeKey;
    }

    public String getEmailKey() {
        return emailKey;
    }

    public void setEmailKey(String emailKey) {
        this.emailKey = emailKey;
    }

    public String getPswKey() {
        return pswKey;
    }

    public void setPswKey(String pswKey) {
        this.pswKey = pswKey;
    }

    public String getOnce() {
        return once;
    }

    public void setOnce(String once) {
        this.once = once;
    }

    public String getNameValue() {
        return nameValue;
    }

    public void setNameValue(String nameValue) {
        this.nameValue = nameValue;
    }

    public String getEmailValue() {
        return emailValue;
    }

    public void setEmailValue(String emailValue) {
        this.emailValue = emailValue;
    }

    public String getPswValue() {
        return pswValue;
    }

    public void setPswValue(String pswValue) {
        this.pswValue = pswValue;
    }

    public String getCodeValue() {
        return codeValue;
    }

    public void setCodeValue(String codeValue) {
        this.codeValue = codeValue;
    }
}
