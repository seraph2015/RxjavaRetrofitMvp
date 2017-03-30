package org.seraph.mvprxjavaretrofit.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.ui.activity.MainActivity;
import org.seraph.mvprxjavaretrofit.mvp.presenter.BasePresenter;
import org.seraph.mvprxjavaretrofit.mvp.presenter.MainOneFragmentPresenter;
import org.seraph.mvprxjavaretrofit.mvp.view.MainOneFragmentView;
import org.seraph.mvprxjavaretrofit.ui.views.ObservableScrollView;

import butterknife.ButterKnife;

/**
 * 主界面
 * date：2017/2/20 16:38
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class MainOneFragment extends BaseFragment implements MainOneFragmentView, View.OnClickListener {

    private TextView tvContent;
    private TextView tvDbUser;
    private ObservableScrollView oScrollView;


    @Override
    protected int getContextView() {
        return R.layout.fragment_one;
    }


    MainOneFragmentPresenter mPresenter;

    @Override
    protected BasePresenter getPresenter() {
        mPresenter = new MainOneFragmentPresenter();
        return mPresenter;
    }

    private MainActivity mMainActivity;

    @Override
    protected void init(Bundle savedInstanceState) {
        tvContent = ButterKnife.findById(rootView, R.id.tv_content);
        tvDbUser = ButterKnife.findById(rootView, R.id.tv_db_user);
        oScrollView = ButterKnife.findById(rootView, R.id.oScrollView);
        tvContent.setOnClickListener(this);

        ButterKnife.findById(rootView, R.id.btn_request).setOnClickListener(this);
        ButterKnife.findById(rootView, R.id.btn_sava_db).setOnClickListener(this);
        ButterKnife.findById(rootView, R.id.btn_updata_db).setOnClickListener(this);
        ButterKnife.findById(rootView, R.id.tv_query_user).setOnClickListener(this);
        ButterKnife.findById(rootView, R.id.tv_clean_user).setOnClickListener(this);

        mMainActivity = (MainActivity) getActivity();

        oScrollView.setScrollViewListener(new ObservableScrollView.ScrollViewListener() {
            @Override
            public void onScrollChanged(float percentScroll) {
                mPresenter.upDataSaveToolbarAlpha(percentScroll);
            }
        });

        mPresenter.initData();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_request:
                mPresenter.doLogin();
                break;
            case R.id.tv_content:
                if (mMainActivity != null) {
                    mMainActivity.mPresenter.switchToolBarVisibility();
                }
                break;
            case R.id.btn_sava_db:
                mPresenter.saveUserInfo();
                break;
            case R.id.btn_updata_db:
                mPresenter.upDataUserInfo();
                break;
            case R.id.tv_query_user:
                mPresenter.queryUserInfo();
                break;
            case R.id.tv_clean_user:
                mPresenter.cleanUserInfo();
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


    @Override
    public void upDataToolbarAlpha(float percentScroll) {
        if (mMainActivity != null) {
            mMainActivity.mPresenter.upDataToolbarAlpha(percentScroll);
        }
    }

    @Override
    public void setTitleAndLogo(String title, int logoIcon) {
        if (mMainActivity != null) {
            mMainActivity.setTitle(title);
            mMainActivity.setTooBarLogo(logoIcon);
        }
    }


}
