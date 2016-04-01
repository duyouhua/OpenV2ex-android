package licrafter.com.v2ex.ui.widget.searchView;/**
 * Created by Administrator on 2016/4/1.
 */

/**
 * author: lijinxiang
 * date: 2016/4/1
 **/
public class SearchItem {

    private String text;

    private boolean hasIcon;

    public SearchItem(String text, boolean hasIcon) {
        this.text = text;
        this.hasIcon = hasIcon;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isHasIcon() {
        return hasIcon;
    }

    public void setHasIcon(boolean hasIcon) {
        this.hasIcon = hasIcon;
    }
}
