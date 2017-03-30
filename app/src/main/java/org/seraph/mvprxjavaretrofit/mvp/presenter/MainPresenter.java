package org.seraph.mvprxjavaretrofit.mvp.presenter;

import android.support.v4.app.Fragment;

import org.seraph.mvprxjavaretrofit.ui.fragment.BaseFragment;
import org.seraph.mvprxjavaretrofit.ui.fragment.MainFourFragment;
import org.seraph.mvprxjavaretrofit.ui.fragment.MainOneFragment;
import org.seraph.mvprxjavaretrofit.ui.fragment.MainThreeFragment;
import org.seraph.mvprxjavaretrofit.ui.fragment.MainTwoFragment;
import org.seraph.mvprxjavaretrofit.mvp.view.BaseView;
import org.seraph.mvprxjavaretrofit.mvp.view.MainActivityView;
import org.seraph.mvprxjavaretrofit.utli.FragmentController;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * mian逻辑处理层
 * date：2017/2/15 11:27
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class MainPresenter extends BaseActivityPresenter {

    private MainActivityView mView;

    @Override
    public void attachView(BaseView view) {
        super.attachView(view);
        this.mView = (MainActivityView) view;
    }

    //当前选中的界面
    private BaseFragment fragment;

    private FragmentController mFragmentController;

    private String[] tags = new String[]{"one", "two", "three", "four"};


    public void initData() {
        mFragmentController = mView.getFragmentController();
        mFragmentController.setFragmentTags(tags);
    }



    /**
     * 设置选中的碎片,切换主题
     */
    public void setSelectedFragment(int positionIndex) {
        Class<? extends Fragment> clazz;
        switch (positionIndex) {
            case 0:
                clazz = MainOneFragment.class;
                break;
            case 1:
                clazz = MainTwoFragment.class;
                break;
            case 2:
                clazz = MainThreeFragment.class;
                break;
            case 3:
                clazz = MainFourFragment.class;
                break;
            default:
                clazz = MainOneFragment.class;
                break;
        }
        fragment = (BaseFragment) mFragmentController.add(clazz, tags[positionIndex], null);
        fragment.restoreData();
    }

    @Override
    public void unSubscribe() {
        super.unSubscribe();
        fragment.mPresenter.unSubscribe();
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
            mView.viewFinish();
        }
        Observable.timer(2, TimeUnit.SECONDS).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                isBackPressed = false;
            }
        });

    }


}
