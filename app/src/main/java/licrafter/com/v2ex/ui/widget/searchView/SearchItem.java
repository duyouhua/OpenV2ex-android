package licrafter.com.v2ex.ui.widget.searchView;/**
 * Created by Administrator on 2016/4/1.
 */

/**
 * author: lijinxiang
 * date: 2016/4/1
 **/
public class SearchItem {

    private String mTitle;
    private String name;

    private boolean mHasIcon;

    public SearchItem(String title, String name) {
        this.mTitle = title;
        this.name = name;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String text) {
        this.mTitle = text;
    }

    public boolean isHasIcon() {
        return mHasIcon;
    }

    public void setHasIcon(boolean mHasIcon) {
        this.mHasIcon = mHasIcon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
