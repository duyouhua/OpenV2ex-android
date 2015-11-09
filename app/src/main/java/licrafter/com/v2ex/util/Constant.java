package licrafter.com.v2ex.util;

import java.util.ArrayList;
import java.util.List;

import licrafter.com.v2ex.model.Table;

/**
 * Created by lijinxiang on 11/5/15.
 */
public class Constant {

    private static List<Table> tables;

    public static class EXTRA{
        public static final String TAB_TITLE = "topic_title";
    }

    public static class SharedPreference{
        public static final String SHARED_FILE = "openv2ex_share";
    }

    public static List<Table> getTables(){
        if (tables == null){
            tables = new ArrayList<>();
//            tables.add(new Table("创意","creative"));
            tables.add(new Table("技术","tech"));
//            tables.add(new Table("好玩","play"));
//            tables.add(new Table("Apple","apple"));
//            tables.add(new Table("酷工作","jobs"));
//            tables.add(new Table("交易","deals"));
//            tables.add(new Table("城市","city"));
//            tables.add(new Table("问与答","qna"));
//            tables.add(new Table("R2","r2"));
        }
        return tables;
    }
}
