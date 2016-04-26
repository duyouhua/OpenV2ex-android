package licrafter.com.v2ex.model.realm;

import io.realm.RealmObject;

/**
 * author: shell
 * date 16/4/26 上午11:24
 **/
public class ReadHistory extends RealmObject {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
