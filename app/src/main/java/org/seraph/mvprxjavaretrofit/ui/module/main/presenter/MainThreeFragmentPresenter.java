package org.seraph.mvprxjavaretrofit.ui.module.main.presenter;

import android.content.DialogInterface;

import org.reactivestreams.Subscription;
import org.seraph.mvprxjavaretrofit.data.network.rx.RxSchedulers;
import org.seraph.mvprxjavaretrofit.data.network.service.Api12306Service;
import org.seraph.mvprxjavaretrofit.ui.module.main.contract.MainThreeFragmentContract;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * FragmentThee逻辑处理层
 * date：2017/2/15 11:27
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class MainThreeFragmentPresenter implements MainThreeFragmentContract.Presenter {


    private MainThreeFragmentContract.View mView;

    private Api12306Service mApi12306Service;

    @Inject
    MainThreeFragmentPresenter(Api12306Service api12306Service) {
        mApi12306Service = api12306Service;
    }


    @Override
    public void start() {

    }

    @Override
    public void setView(MainThreeFragmentContract.View view) {
        this.mView = view;
    }

    @Override
    public void post12306Https() {
        mApi12306Service.do12306Url().compose(RxSchedulers.<String>io_main(mView)).doOnSubscribe(new Consumer<Subscription>() {
            @Override
            public void accept(final Subscription subscription) throws Exception {
                mView.showLoading("正在访问").setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        subscription.cancel();
                    }
                });
            }
        })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.hideLoading();
                        //访问的为12306网站测试证书，所以无法用gson解析
                        if (throwable instanceof javax.net.ssl.SSLHandshakeException) {
                            mView.setTextView("缺少https证书");
                        } else if (throwable instanceof com.google.gson.stream.MalformedJsonException) {
                            mView.setTextView("访问成功");
                        }
                    }
                });

    }


}
