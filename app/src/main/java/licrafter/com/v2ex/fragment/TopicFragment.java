package licrafter.com.v2ex.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import licrafter.com.v2ex.R;
import licrafter.com.v2ex.util.Constant;

/**
 * Created by lijinxiang on 11/5/15.
 */
public class TopicFragment extends BaseFragment {

    @Bind(R.id.tv_info)TextView info;

    private String title;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_topic, container, false);
        ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        if (bundle.containsKey(Constant.EXTRA.TOPIC_TITLE)){
            title = bundle.getString(Constant.EXTRA.TOPIC_TITLE);
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        info.setText(title);
    }

}
