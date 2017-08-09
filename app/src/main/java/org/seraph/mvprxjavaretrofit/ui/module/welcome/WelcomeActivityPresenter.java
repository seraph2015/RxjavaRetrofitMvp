package org.seraph.mvprxjavaretrofit.ui.module.welcome;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * 欢迎页逻辑
 * date：2017/5/3 10:12
 * author：xiongj
 * mail：417753393@qq.com
 **/
class WelcomeActivityPresenter implements WelcomeActivityContract.Presenter {

    private WelcomeActivityContract.View mView;


    @Inject
    WelcomeActivityPresenter() {
    }


    @Override
    public void setView(WelcomeActivityContract.View view) {
        mView = view;
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
        Observable.intervalRange(0, 1, 3, 3, TimeUnit.SECONDS).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                mView.jumpNextActivity();
            }
        });
    }

}
