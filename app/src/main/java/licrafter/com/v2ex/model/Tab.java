package licrafter.com.v2ex.model;


/**
 * Created by lijinxiang on 11/5/15.
 */
public class Tab{

    private String tabName;
    private String tabValue;

    public Tab(String tabName, String tabValue) {
        this.tabName = tabName;
        this.tabValue = tabValue;
    }

    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }

    public String getTabValue() {
        return tabValue;
    }

    public void setTabValue(String tabValue) {
        this.tabValue = tabValue;
    }
}
