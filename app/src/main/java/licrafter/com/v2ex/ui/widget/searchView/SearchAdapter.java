package licrafter.com.v2ex.ui.widget.searchView;/**
 * Created by Administrator on 2016/4/1.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import licrafter.com.v2ex.R;

/**
 * author: lijinxiang
 * date: 2016/4/1
 **/
public class SearchAdapter extends RecyclerView.Adapter implements Filterable {

    private Context mContext;
    private ArrayList<SearchItem> mDatas;
    private ArrayList<SearchItem> mResult;

    public SearchAdapter(Context context, ArrayList<SearchItem> data) {
        this.mContext = context;
        mDatas = data;
        mResult = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemViewHolder holder = new ItemViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_search_result, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ((ItemViewHolder) holder).textView.setText(mResult.get(position).getText());
        }
    }

    @Override
    public int getItemCount() {
        return mResult.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults resultsFilter = new FilterResults();
                if (!TextUtils.isEmpty(constraint)) {
                    String key = constraint.toString().toLowerCase(Locale.getDefault());
                    ArrayList<SearchItem> searchData = new ArrayList<>();
                    for (SearchItem item : mDatas) {
                        String str = item.getText().toString().toLowerCase(Locale.getDefault());
                        if (str.contains(key)) {
                            searchData.add(item);
                        }
                    }
                    resultsFilter.values = searchData;
                    resultsFilter.count = searchData.size();
                } else {
                    resultsFilter.values = mDatas;
                    resultsFilter.count = mDatas.size();
                }
                return resultsFilter;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results.values != null) {
                    mResult.clear();
                    List<?> list = (List<?>) results.values;
                    for (Object object : list) {
                        if (object instanceof SearchItem) {
                            mResult.add((SearchItem) object);
                        }
                    }
                    notifyDataSetChanged();
                }
            }
        };
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.search_item_text);
        }
    }

}
