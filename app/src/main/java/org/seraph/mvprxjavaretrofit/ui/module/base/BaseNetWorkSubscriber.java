package org.seraph.mvprxjavaretrofit.ui.module.base;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.seraph.mvprxjavaretrofit.data.network.exception.ServerErrorCode;
import org.seraph.mvprxjavaretrofit.data.network.exception.ServerErrorException;
import org.seraph.mvprxjavaretrofit.utlis.NetWorkUtils;

/**
 * 网络订阅者父类通用操作
 * date：2017/3/15 14:34
 * author：xiongj
 * mail：417753393@qq.com
 **/
public abstract class BaseNetWorkSubscriber<T, V extends IBaseContract.IBaseView> implements Subscriber<T> {

    private V v;

    protected BaseNetWorkSubscriber(V v) {
        this.v = v;
    }

    @Override
    public void onSubscribe(Subscription s) {
        //判断网络是否可用
        if (!NetWorkUtils.isNetworkConnected(v.getContext())) {
            onError(new ServerErrorException(ServerErrorCode.NETWORK_ERR));
            s.cancel();
        } else {
            s.request(1);
        }
    }

    @Override
    public void onError(Throwable t) {
        v.hideLoading();
        onError(ServerErrorCode.errorCodeToMessageShow(t));
    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    public abstract void onSuccess(T t);

    public abstract void onError(String errStr);


    @Override
    public void onComplete() {
        v.hideLoading();
    }
}
