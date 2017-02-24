package org.seraph.mvprxjavaretrofit.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.mvp.presenter.BasePresenter;
import org.seraph.mvprxjavaretrofit.mvp.presenter.MainOneFragmentPresenter;
import org.seraph.mvprxjavaretrofit.mvp.view.MainOneFragmentView;
import org.seraph.mvprxjavaretrofit.views.ObservableScrollView;

import butterknife.ButterKnife;

/**
 * 主界面
 * date：2017/2/20 16:38
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class MainOneFragment extends BaseFragment implements MainOneFragmentView {

    private TextView tvContent;
    private TextView tvDbUser;
    private ObservableScrollView oScrollView;


    @Override
    protected int getContextView() {
        return R.layout.fragment_one;
    }


    MainOneFragmentPresenter fragmentOnePresenter;

    @Override
    protected BasePresenter getPresenter() {
        fragmentOnePresenter = new MainOneFragmentPresenter();
        return fragmentOnePresenter;
    }



    @Override
    protected void init(Bundle savedInstanceState) {
        tvContent = ButterKnife.findById(rootView,R.id.tv_content);
        tvDbUser = ButterKnife.findById(rootView,R.id.tv_db_user);
        oScrollView = ButterKnife.findById(rootView,R.id.oScrollView);
        tvContent.setOnClickListener(this::onClick);

        ButterKnife.findById(rootView,R.id.btn_request).setOnClickListener(this::onClick);
        ButterKnife.findById(rootView,R.id.btn_sava_db).setOnClickListener(this::onClick);
        ButterKnife.findById(rootView,R.id.tv_query_user).setOnClickListener(this::onClick);
        ButterKnife.findById(rootView,R.id.tv_clean_user).setOnClickListener(this::onClick);

        oScrollView.setScrollViewListener(percentScroll -> fragmentOnePresenter.upDataToolbarAlpha(percentScroll));

        fragmentOnePresenter.initData();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_request:
                fragmentOnePresenter.getNetWork();
                break;
            case R.id.tv_content:
                fragmentOnePresenter.switchToolBarVisibility();
                break;
            case R.id.btn_sava_db:
                fragmentOnePresenter.saveUserInfo();
                break;
            case R.id.tv_query_user:
                fragmentOnePresenter.queryUserInfo();
                break;
            case R.id.tv_clean_user:
                fragmentOnePresenter.cleanUserInfo();
                break;
        }
    }


    @Override
    public void setTextViewValue(CharSequence charSequence) {
        tvContent.setText(charSequence);
    }

    @Override
    public void setUserTextViewValue(CharSequence charSequence) {
        tvDbUser.setText(charSequence);
    }



}
