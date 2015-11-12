package licrafter.com.v2ex.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;


/**
 * Created by lijinxiang on 11/5/15.
 */
public class BaseActivity extends ActionBarActivity {

    protected Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        mContext = this;
    }

}
