package licrafter.com.v2ex.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import licrafter.com.v2ex.R;
import licrafter.com.v2ex.model.Node;
import licrafter.com.v2ex.ui.fragment.TabFragment;
import licrafter.com.v2ex.ui.util.Constant;

/**
 * Created by shell on 15-11-13.
 */
public class NodeActivity extends BaseActivity{

    @Bind(R.id.content)
    FrameLayout content;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private Node node;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_node);
        ButterKnife.bind(this);
        if (getIntent().hasExtra(Constant.EXTRA.NODE)){
            node = (Node) getIntent().getSerializableExtra(Constant.EXTRA.NODE);
        }
        toolbar.setTitle(node != null ? node.getTitle() : "数据发生错误");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Fragment tabFragment = new TabFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.EXTRA.NODE, node);
        tabFragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content,tabFragment).commit();
    }

}
