package org.seraph.mvprxjavaretrofit.ui.module.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.jakewharton.rxbinding2.support.design.widget.RxBottomNavigationView;

import org.seraph.mvprxjavaretrofit.AppApplication;
import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.di.component.main.DaggerMainActivityComponent;
import org.seraph.mvprxjavaretrofit.di.module.ActivityModule;
import org.seraph.mvprxjavaretrofit.ui.module.base.BaseActivity;
import org.seraph.mvprxjavaretrofit.utlis.FragmentController;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * 主界面
 * date：2017/2/15 11:24
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class MainActivity extends BaseActivity implements MainActivityContract.View {


    @BindView(R.id.fl_home)
    FrameLayout flHome;
    @BindView(R.id.bnv_main)
    BottomNavigationView bnvMain;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    public int getContextView() {
        return R.layout.activity_mian;
    }

    @Inject
    MainActivityPresenter mPresenter;

    @Inject
    FragmentController fragmentController;


    @Override
    public void setupActivityComponent() {
        DaggerMainActivityComponent.builder().appComponent(AppApplication.getAppComponent()).activityModule(new ActivityModule(this)).build().inject(this);
    }

    @Override
    public void initCreate(@Nullable Bundle savedInstanceState) {
        fragmentController.setContainerViewId(R.id.fl_home);
        mPresenter.setView(this);
        mPresenter.start();
        RxBottomNavigationView.itemSelections(bnvMain).subscribe(bottomNavigationConsumer);
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
    public void setTitle(String title) {
        toolbar.setTitle(title);
    }

    @Override
    public FragmentController getFragmentController() {
        return fragmentController;
    }


}
