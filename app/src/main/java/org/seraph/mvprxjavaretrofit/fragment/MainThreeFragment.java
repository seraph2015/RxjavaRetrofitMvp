package org.seraph.mvprxjavaretrofit.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.mvp.presenter.BasePresenter;
import org.seraph.mvprxjavaretrofit.mvp.presenter.MainThreeFragmentPresenter;
import org.seraph.mvprxjavaretrofit.mvp.view.MainThreeFragmentView;

import butterknife.ButterKnife;

/**
 * 主界面
 * date：2017/2/20 16:38
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class MainThreeFragment extends BaseFragment implements MainThreeFragmentView {

    private TextView textView;

    @Override
    protected int getContextView() {
        return R.layout.fragment_three;
    }


    MainThreeFragmentPresenter mPresenter;


    @Override
    protected BasePresenter getPresenter() {
        mPresenter = new MainThreeFragmentPresenter();
        return mPresenter;
    }


    @Override
    protected void init(Bundle savedInstanceState) {
        Button btnHttps = ButterKnife.findById(rootView,R.id.btn_https);
        textView = ButterKnife.findById(rootView,R.id.tv_value);
        btnHttps.setOnClickListener(this::onClick);
        mPresenter.initData();
    }

    private void onClick(View view) {
      mPresenter.post12306Https();
    }


    @Override
    public void setTextView(CharSequence charSequence) {
        textView.setText(charSequence);
    }
}
