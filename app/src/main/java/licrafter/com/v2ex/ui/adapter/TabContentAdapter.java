package licrafter.com.v2ex.ui.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import licrafter.com.v2ex.R;
import licrafter.com.v2ex.ui.adapter.AnimationRecyclerViewAdapter.AnimationRecyclerAdapter;
import licrafter.com.v2ex.ui.adapter.AnimationRecyclerViewAdapter.AnimationViewHolder;
import licrafter.com.v2ex.model.Topic;

/**
 * Created by shell on 15-11-9.
 */
public abstract class TabContentAdapter extends AnimationRecyclerAdapter {

    protected Context mContext;
    protected RecyclerView mListView;
    protected List<Topic> mData;
    protected int mLayoutId;
    protected LayoutInflater mInflater;
    protected OnLoadmoreListener mLoadmoreListener;
    protected int firstVisibleItem, totalItemCount, visibleItemCount;
    protected boolean isLoading;

    private final int TYPE_ITEM = 1;
    private final int TYPE_LOADING = 0;

    public TabContentAdapter(Context context, RecyclerView listview, List<Topic> data, int layoutId) {
        this.mContext = context;
        this.mListView = listview;
        this.mData = data;
        this.mLayoutId = layoutId;
        this.mInflater = LayoutInflater.from(mContext);

        if (mListView.getLayoutManager() instanceof LinearLayoutManager) {
            final LinearLayoutManager manager = (LinearLayoutManager) mListView.getLayoutManager();
            mListView.setOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    totalItemCount = manager.getItemCount();
                    firstVisibleItem = manager.findFirstVisibleItemPosition();
                    visibleItemCount = manager.getChildCount();
                    if (!isLoading && (firstVisibleItem + visibleItemCount) >= totalItemCount) {
                        if (mLoadmoreListener != null) {
                            mLoadmoreListener.onLoadMore();
                            isLoading = true;
                        }
                    }
                }
            });
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_LOADING:
                View loading = mInflater.inflate(R.layout.item_footer, parent, false);
                return new LoadingViewHolder(loading);
            case TYPE_ITEM:
                View item = mInflater.inflate(mLayoutId, parent, false);
                return new AnimationViewHolder(item);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_ITEM) {
            super.onBindViewHolder(holder, position);
            convert((AnimationViewHolder) holder, mData.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        if (null == mData.get(position)) {
            return TYPE_LOADING;
        } else {
            return TYPE_ITEM;
        }
    }

    public interface OnLoadmoreListener {
        void onLoadMore();
    }

    public void setLoaded() {
        isLoading = false;
    }

    public void setOnLoadmoreListener(OnLoadmoreListener listener) {
        this.mLoadmoreListener = listener;
    }

    /**
     * 添加一个数据
     *
     * @param topic
     */
    public void addData(Topic topic) {
        mData.add(topic);
        notifyItemInserted(mData.size() - 1);
    }

    /**
     * 添加一个列表
     *
     * @param topics
     */
    public void addAll(List<Topic> topics) {
        removeLastData();
        mData.addAll(topics);
        notifyItemInserted(mData.size() - 1);
    }

    /**
     * 移除最后一个数据
     */
    public void removeLastData() {
        int size = mData.size();
        if (mData.get(size - 1) == null) {
            mData.remove(size - 1);
            notifyItemRemoved(mData.size());
        }
    }

    /**
     * 重置数据
     *
     * @param topics
     */
    public void resetData(List<Topic> topics) {
        mData = topics;
        notifyDataSetChanged();
    }

    public abstract void convert(AnimationViewHolder holder, Topic item);

    public class LoadingViewHolder extends RecyclerView.ViewHolder {

        public LoadingViewHolder(View itemView) {
            super(itemView);
        }
    }
}
