package licrafter.com.v2ex.ui.adapter;/**
 * Created by Administrator on 2016/3/21.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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
    private boolean mHasNextPage = true;
    private View mHeaderView;
    private String error;

    protected CommonRecyclerAdapter(Context context, int itemLayoutId) {
        mDatas = new ArrayList<>();
        this.mContext = context;
        this.mItemLayoutId = itemLayoutId;
        mInflater = LayoutInflater.from(context);
        error = mContext.getString(R.string.app_name);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_EMPTY:
                return new EmptyViewHolder(mInflater.inflate(R.layout.item_empty, parent, false));
            case TYPE_ITEM:
                ItemViewHolder item = new ItemViewHolder(mInflater.inflate(mItemLayoutId, parent, false));
                getItemViewHolder(item);
                return item;
            case TYPE_HEADER:
                HeaderViewHolder header = new HeaderViewHolder(mHeaderView);
                return header;
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
            bindData((ItemViewHolder) holder,position);
        }
        if (holder instanceof EmptyViewHolder){
            ((EmptyViewHolder) holder).getTextView(R.id.tv_error).setText(error);
        }
    }

    @Override
    public int getItemCount() {
        if (mHeaderView == null) {
            return mDatas.size() > 0 ? mDatas.size() + 1 : 1;
        } else {
            return mDatas.size() > 0 ? mDatas.size() + 2 : 2;
        }
    }

    public void setErrorInfo(String error){
        this.error = error;
        notifyDataSetChanged();
    }

    public void hasNextPage(boolean more) {
        this.mHasNextPage = more;
        notifyDataSetChanged();
    }

    public void setHeaderView(View headerView) {
        this.mHeaderView = headerView;
        notifyDataSetChanged();
    }


    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null) {
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
            }
            if (mDatas.size() <= 0 && position == 1) {
                return TYPE_FOOTER;
            } else if (mDatas.size() > 0 && position <= mDatas.size()) {
                return TYPE_ITEM;
            } else {
                return TYPE_FOOTER;
            }
        }
    }

    public void setData(ArrayList<T> list) {
        mDatas.clear();
        mDatas.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(ArrayList<T> list){
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

    protected abstract void getItemViewHolder(ItemViewHolder viewHolder);

    protected abstract void bindData(ItemViewHolder viewHolder,int position);
}
