package org.seraph.mvprxjavaretrofit.ui.module.main;

import androidx.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.blankj.utilcode.util.FragmentUtils;
import com.blankj.utilcode.util.LogUtils;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;

import org.seraph.mvprxjavaretrofit.AppConstants;
import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.databinding.TestActivityMainBinding;
import org.seraph.mvprxjavaretrofit.ui.module.base.ABaseActivity;
import org.seraph.mvprxjavaretrofit.ui.module.main.contract.MainActivityContract;
import org.seraph.mvprxjavaretrofit.ui.module.main.presenter.MainActivityPresenter;

import java.util.List;

import javax.inject.Inject;


/**
 * 主界面
 * date：2017/2/15 11:24
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class MainActivity extends ABaseActivity<MainActivityContract.Presenter> implements MainActivityContract.View {

    TestActivityMainBinding binding;

    @Override
    protected void initContextView() {
        binding = DataBindingUtil.setContentView(this, R.layout.test_activity_main);
    }

    @Inject
    MainActivityPresenter mPresenter;

    @Override
    protected MainActivityContract.Presenter getMVPPresenter() {
        return mPresenter;
    }


    //页面合集
    @Inject
    List<Fragment> fragments;

    @Inject
    FragmentManager fragmentManager;


    @Override
    public void initCreate(@Nullable Bundle savedInstanceState) {
        mPresenter.start();
        //初始化layout
        initLayout();
    }

    private void initLayout() {
        binding.appbar.appbar.setAlpha(0.8f);
        initFragment(0);
        binding.bnvMain.enableAnimation(false);
        binding.bnvMain.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
        binding.bnvMain.setItemHorizontalTranslationEnabled(false);
        binding.bnvMain.setOnNavigationItemSelectedListener(bottomNavigation);
    }


    BottomNavigationView.OnNavigationItemSelectedListener bottomNavigation = menuItem -> {
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
        return true;
    };


    private void initFragment(int showIndex) {
        //默认显示第一个
        FragmentUtils.removeAll(fragmentManager);
        FragmentUtils.add(fragmentManager, fragments, R.id.fl_home, showIndex);
        showIndexFragment(showIndex);
    }

    /**
     * 显示对应位置的fragment
     *
     * @param showIndex 位置
     */
    private void showIndexFragment(int showIndex) {
        mPresenter.setSelectedFragment(showIndex);
        Fragment showFragment = fragments.get(showIndex);
        if (showFragment != null) {
            FragmentUtils.showHide(showFragment, fragments);
        }
    }


    @Override
    public void onBackPressed() {
        mPresenter.onBackPressed();
    }

    @Override
    public void setTitle(String title) {
        binding.appbar.tvToolbarTitle.setText(title);
    }


    @Override
    public void setBackgroundResource(Drawable drawable) {
        binding.llRoot.setBackground(drawable);
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
        int page = (int) savedInstanceState.get(MainActivityPresenter.MAIN_SAVE_KEY);
        initFragment(page);
        mPresenter.setSelectedFragment(page);
    }


    @Subscribe(tags = {@Tag(AppConstants.RxBusAction.TAG_MAIN_MENU)})
    public void ClickMenuPosition(Integer position) {
        binding.bnvMain.setCurrentItem(position);
    }


}
