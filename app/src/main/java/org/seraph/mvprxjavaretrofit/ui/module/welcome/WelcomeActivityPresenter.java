package org.seraph.mvprxjavaretrofit.ui.module.welcome;

import org.seraph.mvprxjavaretrofit.data.network.rx.RxSchedulers;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * 欢迎页逻辑
 * date：2017/5/3 10:12
 * author：xiongj
 * mail：417753393@qq.com
 **/
class WelcomeActivityPresenter extends WelcomeActivityContract.Presenter {


    @Inject
    WelcomeActivityPresenter() {
    }


    @Override
    public void start() {
        //3秒后跳转
        CountDown();
    }


    /**
     * 3秒倒计时转跳
     */
    private void CountDown() {
        Observable.intervalRange(0, 1, 3, 3, TimeUnit.SECONDS)
                .compose(RxSchedulers.io_main2(mView)).subscribe(aLong -> mView.jumpNextActivity());
    }

}
