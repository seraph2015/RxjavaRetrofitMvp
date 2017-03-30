package org.seraph.mvprxjavaretrofit.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import com.jakewharton.rxbinding2.support.design.widget.RxBottomNavigationView;

import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.mvp.presenter.BaseActivityPresenter;
import org.seraph.mvprxjavaretrofit.mvp.presenter.MainPresenter;
import org.seraph.mvprxjavaretrofit.mvp.view.MainActivityView;
import org.seraph.mvprxjavaretrofit.utli.FragmentController;

import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;

/**
 * 主界面
 * date：2017/2/15 11:24
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class MainActivity extends BaseActivity implements MainActivityView {


    BottomNavigationView bnvMain;

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
        setStatusBarImmersionMode(true);
        bnvMain = ButterKnife.findById(this, R.id.bnv_main);
        mPresenter.initData();
        RxBottomNavigationView.itemSelections(bnvMain).subscribe(bottomNavigationConsumer);
    }

    public void setTooBarLogo(@DrawableRes int resId){
        toolbar.setLogo(resId);
    }

    @Override
    public FragmentController getFragmentController() {
        return new FragmentController(this, R.id.fl_home);
    }


    private Consumer<MenuItem> bottomNavigationConsumer = new Consumer<MenuItem>() {
        @Override
        public void accept(MenuItem menuItem) throws Exception {
            switch (menuItem.getItemId()) {
                case R.id.item_one:
                    mPresenter.setSelectedFragment(0);
                    break;
                case R.id.item_two:
                    mPresenter.setSelectedFragment(1);
                    break;
                case R.id.item_three:
                    mPresenter.setSelectedFragment(2);
                    break;
                case R.id.item_four:
                    mPresenter.setSelectedFragment(3);
                    break;
            }
        }
    };


    @Override
    public void onBackPressed() {
        mPresenter.onBackPressed();
    }

    @Override
    public Context getContext() {
        return this;
    }


}
