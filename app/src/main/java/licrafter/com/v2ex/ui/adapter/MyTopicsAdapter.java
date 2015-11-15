package licrafter.com.v2ex.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import licrafter.com.v2ex.model.JsonTopic;
import licrafter.com.v2ex.ui.adapter.AnimationRecyclerViewAdapter.AnimationRecyclerAdapter;

/**
 * Created by shell on 15-11-15.
 */
public class MyTopicsAdapter extends AnimationRecyclerAdapter {

    private List<JsonTopic> mData;

    public MyTopicsAdapter(Context context) {

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        if (mData == null)
            return 0;
        return mData.size();
    }
}
