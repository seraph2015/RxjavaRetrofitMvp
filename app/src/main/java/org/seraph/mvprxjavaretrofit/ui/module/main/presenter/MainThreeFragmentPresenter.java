package org.seraph.mvprxjavaretrofit.ui.module.main.presenter;

import android.content.DialogInterface;

import org.reactivestreams.Subscription;
import org.seraph.mvprxjavaretrofit.data.network.exception.ServerErrorCode;
import org.seraph.mvprxjavaretrofit.data.network.rx.RxSchedulers;
import org.seraph.mvprxjavaretrofit.data.network.service.Api12306Service;
import org.seraph.mvprxjavaretrofit.ui.module.base.ABaseSubscriber;
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
                .subscribe(new ABaseSubscriber<String>(mView) {
                    @Override
                    public void onSuccess(String s) {

                    }

                    @Override
                    public void onError(String errStr) {
                        if (ServerErrorCode.MALFORMED_JSON_EXCEPTION.equals(errStr)) {
                            mView.setTextView("访问成功");
                        } else {
                            mView.setTextView(errStr);
                        }
                    }
                });

    }


}
