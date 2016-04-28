package licrafter.com.v2ex.util;

import java.util.ArrayList;
import java.util.List;

import licrafter.com.v2ex.model.Tab;

/**
 * Created by lijinxiang on 11/5/15.
 */
public class Constant {

    private static List<Tab> mTables;

    public static List<Tab> getmTables() {
        if (mTables == null) {
            mTables = new ArrayList<>();
            mTables.add(new Tab("创意", "creative"));
            mTables.add(new Tab("技术", "tech"));
            mTables.add(new Tab("最近", "recent"));
            mTables.add(new Tab("好玩", "play"));
            mTables.add(new Tab("Apple", "apple"));
            mTables.add(new Tab("酷工作", "jobs"));
            mTables.add(new Tab("交易", "deals"));
            mTables.add(new Tab("全部","all"));
            mTables.add(new Tab("城市", "city"));
            mTables.add(new Tab("问与答", "qna"));
            mTables.add(new Tab("R2", "r2"));
        }
        return mTables;
    }
}
