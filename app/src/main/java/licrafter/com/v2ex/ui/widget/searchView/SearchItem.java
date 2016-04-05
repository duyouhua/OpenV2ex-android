package licrafter.com.v2ex.ui.widget.searchView;/**
 * Created by Administrator on 2016/4/1.
 */

/**
 * author: lijinxiang
 * date: 2016/4/1
 **/
public class SearchItem {

    private String title;
    private String name;

    private boolean hasIcon;

    public SearchItem(String title, String name) {
        this.title = title;
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String text) {
        this.title = text;
    }

    public boolean isHasIcon() {
        return hasIcon;
    }

    public void setHasIcon(boolean hasIcon) {
        this.hasIcon = hasIcon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
