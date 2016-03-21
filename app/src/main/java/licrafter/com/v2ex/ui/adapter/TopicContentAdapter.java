package licrafter.com.v2ex.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import licrafter.com.v2ex.R;
import licrafter.com.v2ex.model.Response.TopicResponse;
import licrafter.com.v2ex.ui.adapter.AnimationRecyclerViewAdapter.AnimationRecyclerAdapter;
import licrafter.com.v2ex.ui.adapter.AnimationRecyclerViewAdapter.AnimationViewHolder;
import licrafter.com.v2ex.ui.widget.RichTextView;

/**
 * Created by shell on 15-11-12.
 */
public class TopicContentAdapter extends AnimationRecyclerAdapter {

    private Context mContext;
    private List<TopicResponse.Comment> commentList;
    private LayoutInflater mInflater;

    public TopicContentAdapter(Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_comment, parent, false);
        return new AnimationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof AnimationViewHolder) {
            AnimationViewHolder vh = (AnimationViewHolder) holder;
            TopicResponse.Comment comment = commentList.get(position);
            super.onBindViewHolder(holder, position);
            ImageView iv_avatar = vh.getView(R.id.iv_avatar);
            TextView tv_username = vh.getView(R.id.tv_username);
            TextView tv_create_time = vh.getView(R.id.tv_create_time);
            TextView tv_rank = vh.getView(R.id.tv_rank);
            RichTextView tv_content = vh.getView(R.id.tv_content);
            //Picasso.with(mContext).load(comment.avatar).into(iv_avatar);
            tv_username.setText(comment.userName);
            tv_create_time.setText(comment.createTime);
            tv_rank.setText(comment.rank);
            tv_content.setRichText(comment.content);

        }
    }

    @Override
    public int getItemCount() {
        if (commentList == null)
            return 0;
        return commentList.size();
    }

    public void setData(List<TopicResponse.Comment> comments) {
        this.commentList = comments;
        notifyDataSetChanged();
    }

}
