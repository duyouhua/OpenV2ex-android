package licrafter.com.v2ex.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import licrafter.com.v2ex.util.Constant;
import licrafter.com.v2ex.util.SharedPrefsUtil;

/**
 * Created by lijinxiang on 11/5/15.
 */
public class BaseFragment extends Fragment {

    protected SharedPrefsUtil mSharedPrefUtil;
    protected Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = getActivity();
        mSharedPrefUtil = SharedPrefsUtil.getInstance(mContext, Constant.SharedPreference.SHARED_FILE);
    }
}
