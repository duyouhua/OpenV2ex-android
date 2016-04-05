package licrafter.com.v2ex.ui.activity;/**
 * Created by Administrator on 2016/3/23.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.Bind;
import licrafter.com.v2ex.R;
import licrafter.com.v2ex.base.BaseToolbarActivity;
import licrafter.com.v2ex.model.response.CreateTopicResponse;
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
    LJSearchView mSearchView;

    private TopicEditPresenter mPresenter;
    private SearchAdapter mSearchAdapter;
    private String mTopicNode;

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
        mSearchAdapter = new SearchAdapter(this);
    }

    @Override
    protected void loadData() {
        showDialog();
        mPresenter.getSearchList();
    }

    @Override
    protected void initListener() {
        mSubmitButton.setOnClickListener(onClickListener);
        mNodeInput.setOnClickListener(onClickListener);
        mSearchAdapter.setOnItemClickListener(new SearchAdapter.OnItemClickListener() {
            @Override
            public void onClick(String name, String title) {
                mTopicNode = name;
                mNodeInput.setText(title);
                mSearchView.hide();
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
                    if (!checkNull(title)) {
                        showDialog();
                        mPresenter.postTopic(title, content, mTopicNode);
                    } else {
                        Toast.makeText(TopicEditActivity.this, getString(R.string.topic_title_is_null), Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.nodeSelectTextView:
                    mSearchView.show();
                    break;
            }
        }
    };

    private boolean checkNull(String title) {
        return TextUtils.isEmpty(title);
    }

    @Override
    protected void detachView() {
        mPresenter.detachView();
    }

    @Override
    public void onFailure(String e) {
        hideDialog();
    }

    public void getSearchListSuccess(ArrayList<SearchItem> searchItems) {
        hideDialog();
        mSearchAdapter.setData(searchItems);
        mSearchView.setAdapter(mSearchAdapter);

    }

    public void onCreatTopicSuccess(CreateTopicResponse response) {
        hideDialog();
        if (response.getMessage() == null) {
            Toast.makeText(this, getString(R.string.create_success), Toast.LENGTH_SHORT).show();
            if (response.getUrl()!=null){
                Intent intent = new Intent(this,WebViewActivity.class);
                intent.putExtra("url",response.getUrl());
                android.util.Log.d("ljx",response.getUrl());
                startActivity(intent);
            }
            finish();
        } else {
            Toast.makeText(this, response.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
