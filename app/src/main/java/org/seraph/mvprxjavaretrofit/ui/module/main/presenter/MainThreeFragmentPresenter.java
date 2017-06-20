package org.seraph.mvprxjavaretrofit.ui.module.main.presenter;

import org.reactivestreams.Subscription;
import org.seraph.mvprxjavaretrofit.data.network.ApiManager;
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

    private ApiManager mApiManager;

    @Inject
    MainThreeFragmentPresenter(ApiManager apiManager) {
        mApiManager = apiManager;
    }


    private Subscription mSubscription;

    @Override
    public void start() {

    }

    @Override
    public void unSubscribe() {
        if (mSubscription != null) {
            mSubscription.cancel();
        }
    }

    @Override
    public void setView(MainThreeFragmentContract.View view) {
        this.mView = view;
    }

    @Override
    public void post12306Https() {
        mApiManager.do12306().doOnSubscribe(new Consumer<Subscription>() {
            @Override
            public void accept(Subscription subscription) throws Exception {
                mSubscription = subscription;
                mView.showLoading("正在访问");
            }
        }).subscribe(new Consumer<String>() {
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
