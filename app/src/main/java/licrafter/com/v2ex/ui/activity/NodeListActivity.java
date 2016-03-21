package licrafter.com.v2ex.ui.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import licrafter.com.v2ex.R;
import licrafter.com.v2ex.model.Node;
import licrafter.com.v2ex.ui.adapter.NodeListAdapter;

/**
 * Created by shell on 15-11-13.
 */
public class NodeListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.rv_content)
    RecyclerView mRecyclerView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @Bind(R.id.header)
    ImageView header;

    private NodeListAdapter madapter;
    private List<Node> mData;
    private int mutedColor = R.attr.colorPrimary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nodes);
        ButterKnife.bind(this);
        mData = new ArrayList<>();
        setSupportActionBar(toolbar);
        collapsingToolbar.setTitle("节点列表");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2
                , StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setHasFixedSize(true);
        madapter = new NodeListAdapter(this, mData);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.mipmap.header);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @SuppressWarnings("ResourceType")
            @Override
            public void onGenerated(Palette palette) {

                mutedColor = palette.getMutedColor(R.color.teal500);
                //和status bar颜色一致,不用图片颜色mutedcolor
                collapsingToolbar.setContentScrimColor(getResources().getColor(R.color.teal500));
                collapsingToolbar.setStatusBarScrimColor(getResources().getColor(R.color.teal500));
            }
        });
        mRecyclerView.setAdapter(madapter);
        getData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getData() {
//        //mSwipeLayout.setRefreshing(true);
//        Callback<List<Node>> callback = new Callback<List<Node>>() {
//            @Override
//            public void success(List<Node> list, Response response) {
//                //mSwipeLayout.setRefreshing(false);
//                if (list != null) {
//                    mData = list;
//                    madapter.setData(mData);
//                }
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                //mSwipeLayout.setRefreshing(false);
//                Toast.makeText(NodeListActivity.this, "网络错误o(╯□╰)o", Toast.LENGTH_SHORT).show();
//            }
//        };
//        V2exService.v2exApi(this).getNodesList(callback);
    }

    @Override
    public void onRefresh() {
        getData();
    }
}
