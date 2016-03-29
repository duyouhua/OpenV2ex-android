package licrafter.com.v2ex.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import licrafter.com.v2ex.R;
import licrafter.com.v2ex.model.old.JsonTopic;

import licrafter.com.v2ex.ui.adapter.AnimationRecyclerViewAdapter.AnimationRecyclerAdapter;
import licrafter.com.v2ex.ui.adapter.AnimationRecyclerViewAdapter.AnimationViewHolder;
import licrafter.com.v2ex.util.Constant;

/**
 * Created by shell on 15-11-15.
 */
public class MyTopicsAdapter extends AnimationRecyclerAdapter {

    private List<JsonTopic> mData;
    private LayoutInflater mInflater;
    private Context mContext;

    public MyTopicsAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_topic_card, parent, false);
        return new AnimationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final JsonTopic topic = mData.get(position);
        AnimationViewHolder vh = (AnimationViewHolder) holder;
        if (topic != null) {
            super.onBindViewHolder(holder, position);
            RoundedImageView iv_avatar = vh.getView(R.id.iv_avatar);
            TextView tv_title = vh.getView(R.id.tv_title);
            TextView tv_node = vh.getView(R.id.tv_node);
            TextView tv_author = vh.getView(R.id.tv_author);
            TextView tv_create_time = vh.getView(R.id.tv_create_time);
            TextView tv_replies = vh.getView(R.id.tv_replies);
            //Picasso.with(mContext).load("https:" + topic.getMember().avatar_normal).into(iv_avatar);
            tv_title.setText(topic.getTitle());
            tv_author.setText(topic.getMember().username);
            tv_node.setText(topic.getNode().title);
            tv_replies.setText(topic.getReplies() + "");
            tv_create_time.setText(topic.getCreated());
            vh.getConvertView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent(mContext, OldTopicDetailActivity.class);
//                    SeriableTopic sTopic = new SeriableTopic(topic);
//                    intent.putExtra(Constant.EXTRA.TOPIC, sTopic);
//                    mContext.startActivity(intent);
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

    public void resetData(List<JsonTopic> topics) {
        mData = topics;
        notifyDataSetChanged();
    }

}
