package licrafter.com.v2ex.util;

import java.util.ArrayList;
import java.util.List;

import licrafter.com.v2ex.model.Tab;

/**
 * Created by lijinxiang on 11/5/15.
 */
public class Constant {

    private static List<Tab> tables;

    public static class EXTRA {
        public static final String TAB_TITLE = "tab_title";
        public static final String AVATAR = "topic";
        public static final String TOPIC = "topic";
        public static final String NODE = "node";
        public static final String NODE_NAME = "node_name";
        public static final String PROFIT_TYPE = "profit_type";
        public static final String PRO_ME = "pro_me";
        public static final String PRO_OTHERS = "pro_others";
        public static final String MYPROFITHEADER = "myprofitheader";
    }

    public static class SharedPreference {
        public static final String SHARED_FILE = "openv2ex_share";
    }

    public static class NETWORK {
        public static final String FIRST_LOADING = "loading";       //第一次获取数据
        public static final String LOAD_MORE = "load_more";         //加载更多
        public static final String REFUSE = "refuse";               //下拉刷新
    }

    public static List<Tab> getTables() {
        if (tables == null) {
            tables = new ArrayList<>();
            tables.add(new Tab("创意", "creative"));
            tables.add(new Tab("技术", "tech"));
            tables.add(new Tab("最近", "recent"));
            tables.add(new Tab("好玩", "play"));
            tables.add(new Tab("Apple", "apple"));
            tables.add(new Tab("酷工作", "jobs"));
            tables.add(new Tab("交易", "deals"));
            tables.add(new Tab("全部","all"));
            tables.add(new Tab("城市", "city"));
            tables.add(new Tab("问与答", "qna"));
            tables.add(new Tab("R2", "r2"));
        }
        return tables;
    }
}
