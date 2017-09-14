package org.seraph.mvprxjavaretrofit.ui.module.main.presenter;

import android.os.Bundle;

import com.blankj.utilcode.util.ToastUtils;

import org.seraph.mvprxjavaretrofit.AppActivityManage;
import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.data.network.rx.RxDisposableHelp;
import org.seraph.mvprxjavaretrofit.ui.module.main.contract.MainActivityContract;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * mian逻辑处理层
 * date：2017/2/15 11:27
 * author：xiongj
 * mail：417753393@qq.com
 **/

public class MainActivityPresenter implements MainActivityContract.Presenter {

    private MainActivityContract.View mView;

    private String[] titles = new String[]{"首页", "搜索", "HTTPS", "其它"};
    private int[] bgs = new int[]{R.mipmap.test_bg_fragment_one, R.mipmap.test_bg_fragment_two, R.mipmap.test_bg_fragment_three, R.mipmap.test_bg_fragment_four};

    private int position = 0;

    @Override
    public void setView(MainActivityContract.View view) {
        this.mView = view;
    }


    @Inject
    MainActivityPresenter() {

    }

    /**
     * 数据保存的KEY
     */
    public static final String MAIN_SAVE_KEY = "showIndex";

    @Override
    public void start() {

    }


    /**
     * 设置选中的碎片
     */
    public void setSelectedFragment(int positionIndex) {
        position = positionIndex;
        mView.setTitle(titles[position]);
        mView.setBackgroundResource(bgs[position]);
    }


    public void onBackPressed() {
        doublePressBackToast();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //保存停留的页面
        outState.putInt(MAIN_SAVE_KEY, position);
    }



    /**
     * 判断是否已经点击过一次回退键
     */
    private boolean isBackPressed = false;

    private void doublePressBackToast() {
        if (!isBackPressed) {
            isBackPressed = true;
            ToastUtils.showShortToast("再按一次退出程序");
        } else {
            AppActivityManage.getInstance().appExit();

        }
        Disposable disposable = Observable.timer(2, TimeUnit.SECONDS).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                isBackPressed = false;
                RxDisposableHelp.dispose();
            }
        });
        RxDisposableHelp.addSubscription(disposable);
    }


}
