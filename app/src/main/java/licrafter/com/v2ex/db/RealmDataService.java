package licrafter.com.v2ex.db;/**
 * Created by Administrator on 2016/3/24.
 */

import io.realm.Realm;
import licrafter.com.v2ex.model.TabContent;

/**
 * author: lijinxiang
 * date: 2016/3/24
 **/
public class RealmDataService {

    private static Realm realm;

    public static void storeTabContent(TabContent tabContent){
        realm = Realm.getDefaultInstance();

    }
}
