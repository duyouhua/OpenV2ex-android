package licrafter.com.v2ex.ui.activity;/**
 * Created by Administrator on 2016/3/23.
 */

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import licrafter.com.v2ex.R;
import licrafter.com.v2ex.base.BaseToolbarActivity;
import licrafter.com.v2ex.mvp.presenters.TopicEditPresenter;
import licrafter.com.v2ex.mvp.views.MvpView;
import licrafter.com.v2ex.ui.widget.searchView.LJSearchView;
import licrafter.com.v2ex.ui.widget.searchView.SearchAdapter;
import licrafter.com.v2ex.ui.widget.searchView.SearchItem;

/**
 * author: lijinxiang
 * date: 2016/3/23
 **/
public class TopicEditActivity extends BaseToolbarActivity implements MvpView {

    @Bind(R.id.nodeSelectTextView)
    TextView mNodeInput;
    @Bind(R.id.titleInputText)
    TextInputEditText mTitleInput;
    @Bind(R.id.contentInputText)
    TextInputEditText mContentInput;
    @Bind(R.id.submitBtn)
    Button mSubmitButton;
    @Bind(R.id.searchView)
    LJSearchView searchView;

    private TopicEditPresenter mPresenter;
    private ArrayList<SearchItem> items;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_topic_edit;
    }

    @Override
    protected void attachVeiw() {
        mPresenter = new TopicEditPresenter();
        mPresenter.attachView(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        items = new ArrayList<>();
        items.add(new SearchItem("apple", false));
        items.add(new SearchItem("v2ex", false));
        items.add(new SearchItem("1990", false));
        items.add(new SearchItem("3ds", false));
        searchView.setAdapter(new SearchAdapter(this, items));
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initListener() {
        mSubmitButton.setOnClickListener(onClickListener);
        mNodeInput.setOnClickListener(onClickListener);
        searchView.addOnSearchListener(new LJSearchView.OnSearchListener() {
            @Override
            public void onQueryStart() {

            }

            @Override
            public void onQueryTextChanged(String text) {

            }

            @Override
            public boolean onQuerySubmit(String query) {
                return false;
            }

            @Override
            public void onQueryEnd() {

            }
        });
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.submitBtn:
                    String title = mTitleInput.getText().toString();
                    String content = mContentInput.getText().toString();
                    if (!checkNull(title, content)) {
                        mPresenter.postTopic(title, content, "apple");
                    }
                    break;
                case R.id.nodeSelectTextView:
                    searchView.show();
                    break;
            }
        }
    };

    private boolean checkNull(String title, String content) {
        if (title == null || content == null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void detachView() {
        mPresenter.detachView();
    }

    @Override
    public void onFailure(String e) {

    }
}
