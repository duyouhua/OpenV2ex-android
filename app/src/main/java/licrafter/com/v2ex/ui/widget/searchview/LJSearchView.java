package licrafter.com.v2ex.ui.widget.searchView;/**
 * /**
 * author: lijinxiang
 * date: 2016/4/1
 **/

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import licrafter.com.v2ex.R;
import licrafter.com.v2ex.util.CustomUtil;

public class LJSearchView extends FrameLayout implements View.OnClickListener, TextWatcher, Filter.FilterListener {

    private int hintTextColor;
    private String hintText;

    private EditText queryEditText;
    private RelativeLayout searchViewRelativeLayout;
    private ImageView backButton;
    private ImageView cancelButton;
    private RecyclerView historyRecyclerView;

    private SearchAdapter historyAdapter;

    public LJSearchView(Context context) {
        this(context, null);
    }

    public LJSearchView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LJSearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.layout_search_view, this, true);
        queryEditText = (EditText) findViewById(R.id.queryEditText);
        setBackgroundResource(R.color.transparent_background);
        searchViewRelativeLayout = (RelativeLayout) findViewById(R.id.searchViewRelativeLayout);
        backButton = (ImageView) findViewById(R.id.searchBackButton);
        cancelButton = (ImageView) findViewById(R.id.searchCancelButton);
        historyRecyclerView = (RecyclerView) findViewById(R.id.searchResultRecyclerView);
        historyRecyclerView.setHasFixedSize(true);
        historyRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LJSearchView, 0, 0);
        if (typedArray != null) {
            hintText = typedArray.getString(R.styleable.LJSearchView_hint_text);
            hintTextColor = typedArray.getColor(R.styleable.LJSearchView_hint_color, 0);
            typedArray.recycle();
        }
        if (hintText != null) {
            queryEditText.setHint(hintText);
        }
        if (hintTextColor != 0) {
            queryEditText.setHintTextColor(hintTextColor);
        }

        queryEditText.addTextChangedListener(this);
        cancelButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
    }

    public void show() {

        setVisibility(VISIBLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Animator animator = ViewAnimationUtils.createCircularReveal(searchViewRelativeLayout, searchViewRelativeLayout.getWidth() / 2
                    , searchViewRelativeLayout.getHeight() / 2, 0, searchViewRelativeLayout.getWidth());
            animator.setDuration(300);
            animator.addListener(showListener);
            animator.setInterpolator(new AccelerateDecelerateInterpolator());
            animator.start();
        } else {
            ObjectAnimator animator = ObjectAnimator.ofFloat(searchViewRelativeLayout, "scaleX", 0f, 1.0f);
            animator.setInterpolator(new AccelerateDecelerateInterpolator());
            animator.addListener(showListener);
            animator.setDuration(300).start();
        }
    }

    public void hide() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Animator animator = ViewAnimationUtils.createCircularReveal(searchViewRelativeLayout, searchViewRelativeLayout.getWidth() / 2
                    , searchViewRelativeLayout.getHeight() / 2, searchViewRelativeLayout.getWidth(), 0);
            animator.setDuration(300);
            animator.setInterpolator(new AccelerateDecelerateInterpolator());
            animator.addListener(hideListener);
            animator.start();
        }
    }

    private Animator.AnimatorListener hideListener = new AnimatorListenerAdapter() {

        @Override
        public void onAnimationStart(Animator animation) {
            super.onAnimationStart(animation);
            historyRecyclerView.setVisibility(GONE);
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            setVisibility(GONE);
            queryEditText.setText("");
        }
    };

    private Animator.AnimatorListener showListener = new AnimatorListenerAdapter() {

        @Override
        public void onAnimationEnd(Animator animation) {
            queryEditText.requestFocus();
            CustomUtil.showInputMethod(queryEditText);
            historyRecyclerView.setVisibility(VISIBLE);
        }
    };

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.searchBackButton) {
            hide();
        } else if (id == R.id.searchCancelButton) {
            queryEditText.setText("");
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        startFilter(s);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public void setAdapter(SearchAdapter adapter) {
        this.historyAdapter = adapter;
        historyRecyclerView.setAdapter(historyAdapter);
    }

    public void setHintText(CharSequence hint) {
        queryEditText.setHint(hint);
    }

    public void setHintTextColor(int color) {
        queryEditText.setHintTextColor(color);
    }

    private void startFilter(CharSequence s) {
        if (historyAdapter != null) {
            (historyAdapter).getFilter().filter(s, this);
        }
    }

    @Override
    public void onFilterComplete(int count) {
    }

}
