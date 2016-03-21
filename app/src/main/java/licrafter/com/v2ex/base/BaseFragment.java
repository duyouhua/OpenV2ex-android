package licrafter.com.v2ex.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/2/2.
 */
public abstract class BaseFragment extends Fragment {

    private boolean isVisible;
    private boolean isPrepare;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(this.getLayoutId(), container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        attachView();
        initViews(view);
        setListeners();
        isPrepare = true;
        loadData();
    }

//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if (getUserVisibleHint()){
//            isVisible = true;
//            lazyLoad();
//        }else {
//            isVisible = false;
//        }
//    }

//    public void lazyLoad(){
//        if (!isVisible||!isPrepare){
//            return;
//        }
//        loadData();
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        detachView();
    }

    protected abstract int getLayoutId();

    protected abstract void attachView();

    protected abstract void initViews(View view);

    protected abstract void setListeners();

    protected abstract void loadData();

    protected abstract void detachView();
}
