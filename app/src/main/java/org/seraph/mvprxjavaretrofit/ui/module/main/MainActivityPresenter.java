package org.seraph.mvprxjavaretrofit.ui.module.main;

import android.support.v4.app.Fragment;

import org.seraph.mvprxjavaretrofit.utlis.FragmentController;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * mian逻辑处理层
 * date：2017/2/15 11:27
 * author：xiongj
 * mail：417753393@qq.com
 **/
class MainActivityPresenter implements MainActivityContract.Presenter {

    private MainActivityContract.View mView;

    private FragmentController mFragmentController;

    private String[] tags = new String[]{"one", "two", "three", "four"};


    @Override
    public void start() {
        mFragmentController = mView.getFragmentController();
        mFragmentController.setFragmentTags(tags);
    }

    @Override
    public void setView(MainActivityContract.View view) {
        this.mView = view;
    }


    @Inject
    MainActivityPresenter() {

    }

    /**
     * 设置选中的碎片,切换主题
     */
    public void setSelectedFragment(int positionIndex) {
        Class<? extends Fragment> clazz;
        switch (positionIndex) {
            case 0:
                clazz = MainOneFragment.class;
                mView.setTitle("首页");
                break;
            case 1:
                clazz = MainTwoFragment.class;
                mView.setTitle("搜索");
                break;
            case 2:
                clazz = MainThreeFragment.class;
                mView.setTitle("HTTPS TEST");
                break;
            case 3:
                clazz = MainFourFragment.class;
                mView.setTitle("TEST");
                break;
            default:
                clazz = MainOneFragment.class;
                mView.setTitle("首页");
                break;
        }
        mFragmentController.add(clazz, tags[positionIndex], null);
    }


    public void onBackPressed() {
        doublePressBackToast();
    }


    /**
     * 判断是否已经点击过一次回退键
     */
    private boolean isBackPressed = false;

    private void doublePressBackToast() {
        if (!isBackPressed) {
            isBackPressed = true;
            mView.showToast("再按一次退出程序");
        } else {
            mView.finish();
        }
        Observable.timer(2, TimeUnit.SECONDS).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                isBackPressed = false;
            }
        });

    }


}
