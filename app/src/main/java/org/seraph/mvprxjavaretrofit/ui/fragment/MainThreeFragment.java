package org.seraph.mvprxjavaretrofit.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.ui.activity.MainActivity;
import org.seraph.mvprxjavaretrofit.mvp.presenter.BasePresenter;
import org.seraph.mvprxjavaretrofit.mvp.presenter.MainThreeFragmentPresenter;
import org.seraph.mvprxjavaretrofit.mvp.view.MainThreeFragmentView;

import butterknife.ButterKnife;

/**
 * 第三页
 * date：2017/2/20 16:38
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class MainThreeFragment extends BaseFragment implements MainThreeFragmentView, View.OnClickListener {

    private TextView httpsValue;
    private EditText etInput;
    private TextView emojiValue;


    @Override
    protected int getContextView() {
        return R.layout.fragment_three;
    }


    private MainThreeFragmentPresenter mPresenter;


    @Override
    protected BasePresenter getPresenter() {
        mPresenter = new MainThreeFragmentPresenter();
        return mPresenter;
    }

    private MainActivity mMainActivity;

    @Override
    protected void init(Bundle savedInstanceState) {
        Button btnHttps = ButterKnife.findById(rootView, R.id.btn_https);
        Button btnEmoji = ButterKnife.findById(rootView, R.id.btn_emoji);
        etInput = ButterKnife.findById(rootView, R.id.et_input);
        emojiValue = ButterKnife.findById(rootView, R.id.tv_emoji_show);
        httpsValue = ButterKnife.findById(rootView, R.id.tv_https_value);
        btnHttps.setOnClickListener(this);
        btnEmoji.setOnClickListener(this);

        mMainActivity = (MainActivity) getActivity();
        mPresenter.initData();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_emoji:
                mPresenter.showEmoji();
                break;
            case R.id.btn_https:
                mPresenter.post12306Https();
                break;
        }
    }


    @Override
    public void setTextView(CharSequence charSequence) {
        httpsValue.setText(charSequence);
    }

    @Override
    public String getInputValue() {
        return etInput.getText().toString().trim();
    }

    @Override
    public void showEmojiValue(CharSequence tempInput) {
        emojiValue.setText(tempInput);
    }

    @Override
    public void setTitleAndLogo(String title, int logoIcon) {
        if (mMainActivity != null) {
            mMainActivity.setTitle(title);
            mMainActivity.setTooBarLogo(logoIcon);
        }
    }

    @Override
    public void upDataToolbarAlpha(float percentScroll) {
        mMainActivity.mPresenter.upDataToolbarAlpha(percentScroll);
    }
}
