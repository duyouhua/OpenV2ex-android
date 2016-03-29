package licrafter.com.v2ex.ui.fragment;/**
 * Created by Administrator on 2016/3/23.
 */

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.Bind;
import licrafter.com.v2ex.R;
import licrafter.com.v2ex.base.BaseFragment;
import licrafter.com.v2ex.model.Node;
import licrafter.com.v2ex.mvp.presenters.NodeListPresenter;
import licrafter.com.v2ex.mvp.views.NodeListView;
import licrafter.com.v2ex.ui.adapter.CommonRecyclerAdapter;
import licrafter.com.v2ex.util.CustomUtil;

/**
 * author: lijinxiang
 * date: 2016/3/23
 **/
public class NodeListFragment extends BaseFragment implements NodeListView {

    @Bind(R.id.rv_content)
    RecyclerView mNodeRecyclerView;
    @Bind(R.id.swipe_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private NodeListPresenter mPresenter;
    private NodeListAdapter mAdapter;

    public static NodeListFragment newInstance() {
        return new NodeListFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_node_list;
    }

    @Override
    protected void attachView() {
        mPresenter = new NodeListPresenter();
        mPresenter.attachView(this);
    }

    @Override
    protected void initViews(View view) {
        CustomUtil.initStyle(mSwipeRefreshLayout);
        mSwipeRefreshLayout.setProgressViewOffset(false,0,30);
        mAdapter = new NodeListAdapter(getContext(), R.layout.item_node);
        mNodeRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mNodeRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void setListeners() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getNodeList();
            }
        });
    }

    @Override
    protected void loadData() {
        mSwipeRefreshLayout.setRefreshing(true);
        mPresenter.getNodeList();
    }

    @Override
    protected void detachView() {
        mPresenter.detachView();
    }

    @Override
    public void onFailure(String e) {
        if (mSwipeRefreshLayout.isRefreshing()){
            mSwipeRefreshLayout.setRefreshing(false);
        }
        mAdapter.setErrorInfo(e);
    }

    @Override
    public void onGetNodeListSuccess(ArrayList<Node> nodes) {
        if (mSwipeRefreshLayout.isRefreshing()){
            mSwipeRefreshLayout.setRefreshing(false);
        }
        mAdapter.setData(nodes);
    }

    private class NodeListAdapter extends CommonRecyclerAdapter<Node> {

        protected NodeListAdapter(Context context, int itemLayoutId) {
            super(context, itemLayoutId);
        }

        @Override
        protected void getItemViewHolder(ItemViewHolder viewHolder) {
            viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "点击了", Toast.LENGTH_LONG).show();
                }
            });
        }

        @Override
        protected void bindData(ItemViewHolder viewHolder, int position) {
            Node node = mDatas.get(position);
            viewHolder.getTextView(R.id.tv_title).setText(node.getTitle());
            viewHolder.getTextView(R.id.tv_header).setText(node.getHeader());
            viewHolder.getTextView(R.id.tv_topic_count).setText("主题数 " + node.getTopics());
        }
    }
}
