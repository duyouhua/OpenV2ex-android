package licrafter.com.v2ex.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import licrafter.com.v2ex.ui.widget.LoadingDialog;

/**
 * Created by Administrator on 2016/2/2.
 */
public abstract class BaseFragment extends Fragment {

    private boolean mIsVisible;
    private boolean mIsPrepare;
    private BaseToolbarActivity mActivity;

    private LoadingDialog mLoadingDialog;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context != null) {
            mActivity = (BaseToolbarActivity) context;
        }
    }

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
        mLoadingDialog = new LoadingDialog(getActivity());
        attachView();
        initViews(view);
        setListeners();
        mIsPrepare = true;
        loadData();
    }

//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if (getUserVisibleHint()){
//            mIsVisible = true;
//            lazyLoad();
//        }else {
//            mIsVisible = false;
//        }
//    }

//    public void lazyLoad(){
//        if (!mIsVisible||!mIsPrepare){
//            return;
//        }
//        loadData();
//    }

    public BaseToolbarActivity getBaseActivity() {
        return mActivity;
    }

    public void showLoadingDialog() {
        if (mLoadingDialog != null && !mLoadingDialog.isShowing()) {
            mLoadingDialog.show();
        }
    }

    public void hideLoadingDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

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
