package org.seraph.mvprxjavaretrofit.ui.module.main;

import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;

import com.blankj.utilcode.util.FragmentUtils;
import com.blankj.utilcode.util.LogUtils;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.jakewharton.rxbinding2.support.design.widget.RxBottomNavigationView;

import org.seraph.mvprxjavaretrofit.AppConstants;
import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.databinding.TestActivityMainBinding;
import org.seraph.mvprxjavaretrofit.ui.module.base.ABaseActivity;
import org.seraph.mvprxjavaretrofit.ui.module.main.contract.MainActivityContract;
import org.seraph.mvprxjavaretrofit.ui.module.main.presenter.MainActivityPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

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
    private List<Fragment> fragments = new ArrayList<>();

    @Inject
    FragmentManager fragmentManager;

    @Inject
    MainOneFragment mOneFragment;
    @Inject
    MainTwoFragment mTwoFragment;
    @Inject
    MainThreeFragment mThreeFragment;
    @Inject
    MainFourFragment mFourFragment;


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
        binding.bnvMain.enableShiftingMode(false);
        binding.bnvMain.enableItemShiftingMode(false);
        RxBottomNavigationView.itemSelections(binding.bnvMain).subscribe(bottomNavigationConsumer);
    }


    private void initFragment(int showIndex) {
        fragments.clear();
        fragments.add(mOneFragment);
        fragments.add(mTwoFragment);
        fragments.add(mThreeFragment);
        fragments.add(mFourFragment);
        //默认显示第一个
        FragmentUtils.removeAll(fragmentManager);
        FragmentUtils.add(fragmentManager, fragments, R.id.fl_home, showIndex);
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
