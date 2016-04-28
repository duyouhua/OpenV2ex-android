package licrafter.com.v2ex.ui.adapter;/**
 * Created by Administrator on 2016/3/21.
 */

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import licrafter.com.v2ex.R;
import licrafter.com.v2ex.ui.adapter.base.BaseViewHolder;

/**
 * author: lijinxiang
 * date: 2016/3/21
 **/
public abstract class CommonRecyclerAdapter<T> extends RecyclerView.Adapter {

    private static final int TYPE_ITEM = 0x00;
    private static final int TYPE_FOOTER = 0x01;
    private static final int TYPE_EMPTY = 0x03;
    private static final int TYPE_HEADER = 0x04;
    protected ArrayList<T> mDatas;
    protected Context mContext;
    private LayoutInflater mInflater;
    private int mItemLayoutId;
    private int mHeadLayoutId;
    private boolean mHasNextPage = false;
    private String mError;

    protected CommonRecyclerAdapter(Context context, int itemLayoutId) {
        this(context, itemLayoutId, 0);
    }

    protected CommonRecyclerAdapter(Context context, int itemLayoutId, int headerLayoutId) {
        mDatas = new ArrayList<>();
        this.mContext = context;
        this.mItemLayoutId = itemLayoutId;
        this.mHeadLayoutId = headerLayoutId;
        mInflater = LayoutInflater.from(context);
        mError = mContext.getString(R.string.no_data);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_EMPTY:
                EmptyViewHolder empty = new EmptyViewHolder(mInflater.inflate(R.layout.item_empty, parent, false));
                if (mHeadLayoutId == 0) {
                    empty.getConvertView().getLayoutParams().height = parent.getHeight();
                } else {
                    empty.getConvertView().getLayoutParams().height = parent.getHeight() - parent.getChildAt(0).getHeight();
                }
                if (empty.getConvertView().getLayoutParams() instanceof StaggeredGridLayoutManager.LayoutParams) {
                    ((StaggeredGridLayoutManager.LayoutParams) empty.getConvertView().getLayoutParams()).setFullSpan(true);
                }
                return empty;
            case TYPE_ITEM:
                ItemViewHolder item = new ItemViewHolder(mInflater.inflate(mItemLayoutId, parent, false));
                getItemViewHolder(item);
                return item;
            case TYPE_HEADER:
                if (mHeadLayoutId != 0) {
                    HeaderViewHolder header = new HeaderViewHolder(mInflater.inflate(mHeadLayoutId, parent, false));
                    return header;
                }
            case TYPE_FOOTER:
                FooterViewHolder footer = new FooterViewHolder(mInflater.inflate(R.layout.item_footer, parent, false));
                return footer;
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FooterViewHolder) {
            if (mHasNextPage) {
                ((FooterViewHolder) holder).getProgressBar(R.id.footerProgressBar).setVisibility(View.VISIBLE);
                ((FooterViewHolder) holder).getTextView(R.id.footerInfo).setText("加载中...");
            } else {
                ((FooterViewHolder) holder).getProgressBar(R.id.footerProgressBar).setVisibility(View.GONE);
                ((FooterViewHolder) holder).getTextView(R.id.footerInfo).setText("没有更多数据了");
            }
        }
        if (holder instanceof ItemViewHolder) {
            if (mHeadLayoutId != 0) {
                position -= 1;
            }
            bindData((ItemViewHolder) holder, position);
        }
        if (holder instanceof HeaderViewHolder) {
            bindHeader((HeaderViewHolder) holder, position);
        }
        if (holder instanceof EmptyViewHolder) {
            ((EmptyViewHolder) holder).getTextView(R.id.tv_error).setText(mError);
        }
    }

    @Override
    public int getItemCount() {
        if (mHeadLayoutId == 0) {
            return mDatas.size() > 0 ? mDatas.size() + 1 : 1;
        } else {
            return mDatas.size() > 0 ? mDatas.size() + 2 : 2;
        }
    }

    public void setErrorInfo(String error) {
        this.mError = error;
        notifyDataSetChanged();
    }

    public void hasNextPage(boolean more) {
        this.mHasNextPage = more;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeadLayoutId == 0) {
            if (mDatas.size() <= 0) {
                return TYPE_EMPTY;
            } else {
                if (position < mDatas.size()) {
                    return TYPE_ITEM;
                } else {
                    return TYPE_FOOTER;
                }
            }
        } else {
            if (position == 0) {
                return TYPE_HEADER;
            } else if (position == mDatas.size() + 1 && mDatas.size() > 0) {
                return TYPE_FOOTER;
            } else if (position == mDatas.size() + 1 && mDatas.size() == 0) {
                return TYPE_EMPTY;
            } else {
                return TYPE_ITEM;
            }
        }
    }

    public void setData(ArrayList<T> list) {
        mDatas.clear();
        mDatas.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(ArrayList<T> list) {
        mDatas.addAll(list);
        notifyDataSetChanged();
    }

    public static class FooterViewHolder extends BaseViewHolder {

        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }

    public static class ItemViewHolder extends BaseViewHolder {

        public ItemViewHolder(View itemView) {
            super(itemView);
        }
    }

    public static class HeaderViewHolder extends BaseViewHolder {

        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    public static class EmptyViewHolder extends BaseViewHolder {

        public EmptyViewHolder(View itemView) {
            super(itemView);

        }
    }

    public GridLayoutManager spanSizeLookupManager(int span) {
        GridLayoutManager manager = new GridLayoutManager(mContext, span);
        manager.setSpanSizeLookup(new AutoSpanSizeLookUp());
        return manager;
    }

    class AutoSpanSizeLookUp extends GridLayoutManager.SpanSizeLookup {

        @Override
        public int getSpanSize(int position) {
            switch (getItemViewType(position)) {
                case TYPE_EMPTY:
                    return 2;
                case TYPE_HEADER:
                    return 2;
                case TYPE_ITEM:
                    return 1;
                case TYPE_FOOTER:
                    return 2;
                default:
                    return 1;
            }
        }
    }

    protected abstract void getItemViewHolder(ItemViewHolder viewHolder);

    protected abstract void bindData(ItemViewHolder viewHolder, int position);

    protected void bindHeader(HeaderViewHolder viewHolder, int position) {

    }
}
