package licrafter.com.v2ex.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import licrafter.com.v2ex.R;
import licrafter.com.v2ex.adapter.AnimationRecyclerViewAdapter.AnimationRecyclerAdapter;
import licrafter.com.v2ex.adapter.AnimationRecyclerViewAdapter.AnimationViewHolder;

/**
 * Created by shell on 15-11-9.
 */
public abstract class CommonAdapter<T> extends AnimationRecyclerAdapter {

    private Context mContext;
    private RecyclerView mListView;
    private List<T> mData;
    private int mLayoutId;
    private LayoutInflater mInflater;
    private OnLoadmoreListener mLoadmoreListener;
    private int firstVisibleItem, totalItemCount, visibleItemCount;
    private boolean isLoading;

    private final int TYPE_ITEM = 1;
    private final int TYPE_LOADING = 0;

    public CommonAdapter(Context context, RecyclerView listview, List<T> data, int layoutId) {
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
                        if (mLoadmoreListener != null&&mData.get(mData.size()-1)==null) {
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
                View loading = mInflater.inflate(R.layout.item_loading, parent, false);
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
        if (mData.get(position) != null && holder instanceof AnimationViewHolder) {
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

    public abstract void convert(AnimationViewHolder holder, T item);

    public class LoadingViewHolder extends RecyclerView.ViewHolder {

        public LoadingViewHolder(View itemView) {
            super(itemView);
        }
    }
}
