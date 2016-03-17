package licrafter.com.v2ex.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;

import butterknife.Bind;
import butterknife.ButterKnife;
import licrafter.com.v2ex.R;
import licrafter.com.v2ex.ui.fragment.MyProfitFragment;
import licrafter.com.v2ex.ui.fragment.OtherProfitFragment;
import licrafter.com.v2ex.util.Constant;

/**
 * Created by shell on 15-11-15.
 */
public class ProfitActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private String userName = "";
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profit);
        ButterKnife.bind(this);
        transaction = getSupportFragmentManager().beginTransaction();
        if (getIntent().hasExtra(Constant.EXTRA.PROFIT_TYPE)&&getIntent().hasExtra("title")){
            userName = getIntent().getStringExtra("title");
            switch (getIntent().getStringExtra(Constant.EXTRA.PROFIT_TYPE)){
                case Constant.EXTRA.PRO_ME:
                    Fragment myProfit = new MyProfitFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("username",userName);
                    myProfit.setArguments(bundle);
                    transaction.replace(R.id.container,myProfit).commit();
                    break;
                case Constant.EXTRA.PRO_OTHERS:
                    Fragment otherProfit = new OtherProfitFragment();
                    transaction.replace(R.id.container,otherProfit).commit();
                    break;

            }
        }
        toolbar.setTitle(userName);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    public Toolbar getToolbar(){
        return toolbar;
    }
}
