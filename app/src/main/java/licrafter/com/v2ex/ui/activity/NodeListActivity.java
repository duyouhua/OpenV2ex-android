package licrafter.com.v2ex.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import licrafter.com.v2ex.R;
import licrafter.com.v2ex.api.Server;
import licrafter.com.v2ex.model.Node;
import licrafter.com.v2ex.ui.adapter.NodeListAdapter;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by shell on 15-11-13.
 */
public class NodeListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.swipe_layout)
    SwipeRefreshLayout mSwipeLayout;
    @Bind(R.id.rv_content)
    RecyclerView mRecyclerView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private NodeListAdapter madapter;
    private List<Node> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nodes);
        ButterKnife.bind(this);
        mData = new ArrayList<>();
        toolbar.setTitle("节点列表");
        setSupportActionBar(toolbar);
        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setProgressViewOffset(false, 0, 25);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2
                , StaggeredGridLayoutManager.VERTICAL));
        madapter = new NodeListAdapter(this, mData);
        mRecyclerView.setAdapter(madapter);
        getData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getData() {
        mSwipeLayout.setRefreshing(true);
        Callback<List<Node>> callback = new Callback<List<Node>>() {
            @Override
            public void success(List<Node> list, Response response) {
                mSwipeLayout.setRefreshing(false);
                if (list != null) {
                    mData = list;
                    madapter.setData(mData);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                mSwipeLayout.setRefreshing(false);
                Toast.makeText(NodeListActivity.this, "网络错误o(╯□╰)o", Toast.LENGTH_SHORT).show();
            }
        };
        Server.v2exApi(this).getNodesList(callback);
    }

    @Override
    public void onRefresh() {
        getData();
    }
}
