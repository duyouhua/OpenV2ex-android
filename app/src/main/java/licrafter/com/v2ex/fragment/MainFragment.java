package licrafter.com.v2ex.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;
import licrafter.com.v2ex.MainActivity;
import licrafter.com.v2ex.R;
import licrafter.com.v2ex.adapter.TopicPagerAdapter;
import licrafter.com.v2ex.module.TopicNode;
import licrafter.com.v2ex.widget.PagerSlidingTabStrip;

/**
 * Created by lijinxiang on 11/5/15.
 */
public class MainFragment extends BaseFragment{

    @Bind(R.id.tabs)
    PagerSlidingTabStrip mTabStrip;
    @Bind(R.id.vp_content)
    ViewPager mVpContent;

    private List<TopicNode> nodes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nodes = new ArrayList<>();
        for (int i=0;i<15;i++){
            TopicNode node = new TopicNode();
            node.setTopicTitle("标题"+i);
            nodes.add(node);
        }
        mVpContent.setAdapter(new TopicPagerAdapter(getActivity().getSupportFragmentManager(), nodes));
        mTabStrip.setViewPager(mVpContent);
        mTabStrip.setTextColor(getResources().getColor(R.color.grey400));
    }

    public Fragment newInstance(MainActivity cativity){
        Fragment fragment = new MainFragment();
        return fragment;
    }
}
