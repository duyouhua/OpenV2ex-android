package licrafter.com.v2ex.ui.activity;/**
 * Created by Administrator on 2016/3/23.
 */

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.Bind;
import licrafter.com.v2ex.R;
import licrafter.com.v2ex.base.BaseToolbarActivity;
import licrafter.com.v2ex.mvp.presenters.TopicEditPresenter;
import licrafter.com.v2ex.mvp.views.MvpView;

/**
 * author: lijinxiang
 * date: 2016/3/23
 **/
public class TopicEditActivity extends BaseToolbarActivity implements MvpView {

    @Bind(R.id.nodeInputEditText)
    EditText mNodeInput;
    @Bind(R.id.titleInputText)
    EditText mTitleInput;
    @Bind(R.id.contentInputText)
    EditText mContentInput;
    @Bind(R.id.submitBtn)
    Button mSubmitButton;

    private TopicEditPresenter mPresenter;

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

    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initListener() {
        mSubmitButton.setOnClickListener(onClickListener);
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
    public void onFailure(Throwable e) {

    }
}
