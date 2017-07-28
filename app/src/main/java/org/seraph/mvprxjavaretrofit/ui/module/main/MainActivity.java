package org.seraph.mvprxjavaretrofit.ui.module.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

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
public class MainActivity extends ABaseActivity<MainActivityContract.View, MainActivityContract.Presenter> implements MainActivityContract.View, IComponent<MainActivityComponent> {


    @BindView(R.id.bnv_main)
    BottomNavigationView bnvMain;
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

    @Inject
    FragmentController fragmentController;

    private MainActivityComponent mMainActivityComponent;

    @Override
    public void setupActivityComponent(AppComponent appComponent, ActivityModule activityModule) {
        mMainActivityComponent = DaggerMainActivityComponent.builder()
                .appComponent(appComponent)
                .activityModule(activityModule)
                .build();
        mMainActivityComponent.inject(this);
    }

    @Override
    public void initCreate(@Nullable Bundle savedInstanceState) {
        fragmentController.setContainerViewId(R.id.fl_home);
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
        //toolbar_top_padding_text_min
        toolbarTitle.setText(title);
    }

    @Override
    public FragmentController getFragmentController() {
        return fragmentController;
    }


    @Override
    public MainActivityComponent getComponent() {
        return mMainActivityComponent;
    }
}
