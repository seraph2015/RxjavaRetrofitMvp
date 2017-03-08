package org.seraph.mvprxjavaretrofit.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.mvp.presenter.BaseActivityPresenter;
import org.seraph.mvprxjavaretrofit.mvp.presenter.MainPresenter;
import org.seraph.mvprxjavaretrofit.mvp.view.MainActivityView;
import org.seraph.mvprxjavaretrofit.utlis.FragmentController;

import butterknife.ButterKnife;

/**
 * 主界面
 * date：2017/2/15 11:24
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class MainActivity extends BaseActivity implements MainActivityView {


    public LinearLayout mMenu;

    @Override
    protected int getContextView() {
        return R.layout.activity_mian;
    }

    public MainPresenter mPresenter;

    @Override
    protected BaseActivityPresenter getPresenter() {
        mPresenter = new MainPresenter();
        return mPresenter;
    }


    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("主页");
        toolbar.setLogo(R.mipmap.icon_main_logo);
        setStatusBarImmersionMode(true);
        mMenu = ButterKnife.findById(this, R.id.layout_menu);
        ButterKnife.findById(this, R.id.ll_menu_one).setOnClickListener(this::onClick);
        ButterKnife.findById(this, R.id.ll_menu_two).setOnClickListener(this::onClick);
        ButterKnife.findById(this, R.id.ll_menu_three).setOnClickListener(this::onClick);
        ButterKnife.findById(this, R.id.ll_menu_four).setOnClickListener(this::onClick);
        mPresenter.initData();
        mPresenter.changeCurrentClickState(0);
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_menu_one:
                mPresenter.changeCurrentClickState(0);
                break;
            case R.id.ll_menu_two:
                mPresenter.changeCurrentClickState(1);
                break;
            case R.id.ll_menu_three:
                mPresenter.changeCurrentClickState(2);
                break;
            case R.id.ll_menu_four:
                mPresenter.changeCurrentClickState(3);
                break;
        }
    }

    @Override
    public int getMenuChildCount() {
        return mMenu.getChildCount();
    }


    @Override
    public FragmentController getFragmentController() {
        return new FragmentController(this, R.id.fl_home);
    }


    @Override
    public void setMenuItem(int position, @ColorInt int bgColor, @DrawableRes int resId, @ColorInt int textColor) {
        LinearLayout llItem = (LinearLayout) mMenu.getChildAt(position);
        ImageView imageView = (ImageView) llItem.getChildAt(0);
        TextView textView = (TextView) llItem.getChildAt(1);
        llItem.setBackgroundColor(bgColor);
        imageView.setImageResource(resId);
        textView.setTextColor(textColor);
    }

    @Override
    public void onBackPressed() {
        mPresenter.onBackPressed();

    }

    @Override
    public Context getContext() {
        return this;
    }
}
