package licrafter.com.v2ex.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import licrafter.com.v2ex.R;
import licrafter.com.v2ex.model.Node;
import licrafter.com.v2ex.ui.activity.TopicDetailActivity;
import licrafter.com.v2ex.ui.adapter.AnimationRecyclerViewAdapter.AnimationRecyclerAdapter;
import licrafter.com.v2ex.ui.adapter.AnimationRecyclerViewAdapter.AnimationViewHolder;
import licrafter.com.v2ex.util.Constant;
import licrafter.com.v2ex.ui.widget.RichTextView;

/**
 * Created by shell on 15-11-13.
 */
public class NodeListAdapter extends AnimationRecyclerAdapter {

    private Context mContext;
    private List<Node> mData;
    private LayoutInflater mInflater;

    public NodeListAdapter(Context context, List<Node> list) {
        this.mContext = context;
        this.mData = list;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_node, parent, false);
        return new AnimationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Node node = mData.get(position);
        AnimationViewHolder vh = (AnimationViewHolder) holder;
        if (node != null) {
            super.onBindViewHolder(vh, position);
            TextView tv_title = vh.getView(R.id.tv_title);
            RichTextView tv_header = vh.getView(R.id.tv_header);
            TextView tv_topics = vh.getView(R.id.tv_topic_count);
            tv_title.setText(node.getTitle());
            if (node.getHeader() != null) {
                tv_header.setRichText(node.getHeader());
            }else {
                tv_header.setRichText("");
            }
            tv_topics.setText("主题数: " + node.getTopics());
            vh.getConvertView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, TopicDetailActivity.class);
                    //intent.putExtra(Constant.EXTRA.NODE,node);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (mData == null)
            return 0;
        return mData.size();
    }

    public void setData(List<Node> nodes) {
        this.mData = nodes;
        notifyDataSetChanged();
    }
}
