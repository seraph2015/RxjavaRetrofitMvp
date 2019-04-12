package org.seraph.mvprxjavaretrofit.ui.module.main;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.blankj.utilcode.util.FragmentUtils;
import com.blankj.utilcode.util.LogUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;

import org.seraph.mvprxjavaretrofit.AppConstants;
import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.databinding.ActMainBinding;
import org.seraph.mvprxjavaretrofit.ui.module.base.ABaseActivity;
import org.seraph.mvprxjavaretrofit.ui.module.main.contract.MainActivityContract;
import org.seraph.mvprxjavaretrofit.ui.module.main.presenter.MainActivityPresenter;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


/**
 * 主界面
 * date：2017/2/15 11:24
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class MainActivity extends ABaseActivity implements MainActivityContract.View {

    ActMainBinding binding;

    @Override
    protected void initContextView() {
        binding = DataBindingUtil.setContentView(this, R.layout.act_main);
    }

    @Inject
    MainActivityPresenter presenter;

    @Override
    protected MainActivityContract.Presenter getMVPPresenter() {
        presenter.setView(this);
        return presenter;
    }


    //页面合集
    @Inject
    List<Fragment> fragments;

    @Inject
    FragmentManager fragmentManager;


    @Override
    public void initCreate(@Nullable Bundle savedInstanceState) {
        presenter.start();
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
        presenter.setSelectedFragment(showIndex);
        Fragment showFragment = fragments.get(showIndex);
        if (showFragment != null) {
            FragmentUtils.showHide(showFragment, fragments);
        }
    }


    @Override
    public void onBackPressed() {
        presenter.onBackPressed();
    }

    @Override
    public void setTitle(String title) {
        binding.appbar.toolbar.setTitle(title);
    }


    @Override
    public void setBackgroundResource(Drawable drawable) {
        binding.llRoot.setBackground(drawable);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        presenter.onSaveInstanceState(outState);
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
        presenter.setSelectedFragment(page);
    }


    @Subscribe(tags = {@Tag(AppConstants.RxBusAction.TAG_MAIN_MENU)})
    public void ClickMenuPosition(Integer position) {
        binding.bnvMain.setCurrentItem(position);
    }


}
