package org.seraph.mvprxjavaretrofit.ui.module.base;

import com.blankj.utilcode.util.NetworkUtils;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.seraph.mvprxjavaretrofit.data.network.exception.ServerErrorCode;
import org.seraph.mvprxjavaretrofit.data.network.exception.ServerErrorException;

/**
 * 网络订阅者父类通用操作
 * date：2017/3/15 14:34
 * author：xiongj
 * mail：417753393@qq.com
 **/
public abstract class ABaseNetWorkSubscriber<T> implements Subscriber<T> {

    private IBaseContract.IBaseView v;

    protected ABaseNetWorkSubscriber(IBaseContract.IBaseView v) {
        this.v = v;
    }

    public ABaseNetWorkSubscriber() {
    }

    @Override
    public void onSubscribe(Subscription s) {
        //判断网络是否可用
        if (!NetworkUtils.isConnected()) {
            onError(new ServerErrorException(ServerErrorCode.NETWORK_ERR));
        } else {
            s.request(1);
        }
    }

    @Override
    public void onError(Throwable t) {
        onError(ServerErrorCode.errorCodeToMessageShow(t));
        if (v != null)
            v.hideLoading();
    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    public abstract void onSuccess(T t);

    public abstract void onError(String errStr);


    @Override
    public void onComplete() {
        if (v != null)
            v.hideLoading();
    }

}
