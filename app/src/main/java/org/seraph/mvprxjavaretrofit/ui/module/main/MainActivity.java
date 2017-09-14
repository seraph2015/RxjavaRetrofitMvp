package org.seraph.mvprxjavaretrofit.ui.module.main;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.FragmentUtils;
import com.blankj.utilcode.util.LogUtils;
import com.jakewharton.rxbinding2.support.design.widget.RxBottomNavigationView;

import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.di.component.DaggerMainActivityComponent;
import org.seraph.mvprxjavaretrofit.di.component.MainActivityComponent;
import org.seraph.mvprxjavaretrofit.di.component.base.AppComponent;
import org.seraph.mvprxjavaretrofit.di.module.base.ActivityModule;
import org.seraph.mvprxjavaretrofit.ui.module.base.ABaseActivity;
import org.seraph.mvprxjavaretrofit.ui.module.base.IComponent;
import org.seraph.mvprxjavaretrofit.ui.module.main.contract.MainActivityContract;
import org.seraph.mvprxjavaretrofit.ui.module.main.presenter.MainActivityPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * 主界面
 * date：2017/2/15 11:24
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class MainActivity extends ABaseActivity<MainActivityContract.View, MainActivityContract.Presenter> implements MainActivityContract.View, IComponent<MainActivityComponent> {

    @BindView(R.id.ll_root)
    LinearLayout rootView;
    @BindView(R.id.bnv_main)
    BottomNavigationView bnvMain;

    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_toolbar_title)
    TextView toolbarTitle;

    @Override
    public int getContextView() {
        return R.layout.test_activity_main;
    }

    @Inject
    MainActivityPresenter mPresenter;

    @Override
    protected MainActivityContract.Presenter getMVPPresenter() {
        return mPresenter;
    }


    private MainActivityComponent mMainActivityComponent;

    @Override
    public void setupActivityComponent(AppComponent appComponent, ActivityModule activityModule) {
        mMainActivityComponent = DaggerMainActivityComponent.builder()
                .appComponent(appComponent)
                .activityModule(activityModule)
                .build();
        mMainActivityComponent.inject(this);
    }

    //页面合集
    private List<Fragment> fragments = new ArrayList<>();

    //当前显示的fragment
    private Fragment mShowFragment;

    @Override
    public void initCreate(@Nullable Bundle savedInstanceState) {
        appbar.setAlpha(0.8f);
        //初始化fragment
        initFragment(0);
        mPresenter.start();
        RxBottomNavigationView.itemSelections(bnvMain).subscribe(bottomNavigationConsumer);
    }

    private void initFragment(int showIndex) {
        fragments.clear();
        fragments.add(new MainOneFragment());
        fragments.add(new MainTwoFragment());
        fragments.add(new MainThreeFragment());
        fragments.add(new MainFourFragment());
        //默认显示第一个
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentUtils.removeAllFragments(fragmentManager);
        mShowFragment = FragmentUtils.addFragments(fragmentManager, fragments, R.id.fl_home, showIndex);
    }

    private Consumer<MenuItem> bottomNavigationConsumer = new Consumer<MenuItem>() {
        @Override
        public void accept(MenuItem menuItem) throws Exception {
            int showIndex = 0;
            switch (menuItem.getItemId()) {
                case R.id.item_one:
                    showIndex = 0;
                    break;
                case R.id.item_two:
                    showIndex = 1;
                    break;
                case R.id.item_three:
                    showIndex = 2;
                    break;
                case R.id.item_four:
                    showIndex = 3;
                    break;
            }
            showIndexFragment(showIndex);
        }
    };

    /**
     * 显示对应位置的fragment
     *
     * @param showIndex 位置
     */
    private void showIndexFragment(int showIndex) {
        mPresenter.setSelectedFragment(showIndex);
        Fragment showFragment = fragments.get(showIndex);
        if (showFragment != null && mShowFragment != showFragment) {
            mShowFragment = FragmentUtils.hideShowFragment(mShowFragment, showFragment);
        }
    }


    @Override
    public void onBackPressed() {
        mPresenter.onBackPressed();
    }

    @Override
    public void setTitle(String title) {
        toolbarTitle.setText(title);
    }

    @Override
    public void setBackgroundResource(@DrawableRes int resid) {
        rootView.setBackgroundResource(resid);
    }


    @Override
    public MainActivityComponent getComponent() {
        return mMainActivityComponent;
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mPresenter.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState == null) {
            return;
        }
        LogUtils.i("MainActivity->onRestoreInstanceState", savedInstanceState);
        //恢复停留的页面
        int page = (int) savedInstanceState.get("page");
        initFragment(page);
        mPresenter.setSelectedFragment(page);
    }

}
