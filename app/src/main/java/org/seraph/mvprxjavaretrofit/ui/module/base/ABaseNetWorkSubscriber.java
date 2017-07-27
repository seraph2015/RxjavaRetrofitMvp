package org.seraph.mvprxjavaretrofit.ui.module.base;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.seraph.mvprxjavaretrofit.data.network.exception.ServerErrorCode;
import org.seraph.mvprxjavaretrofit.data.network.exception.ServerErrorException;
import org.seraph.mvprxjavaretrofit.utlis.NetWorkUtils;

import io.reactivex.disposables.Disposable;

/**
 * 网络订阅者父类通用操作
 * date：2017/3/15 14:34
 * author：xiongj
 * mail：417753393@qq.com
 **/
public abstract class ABaseNetWorkSubscriber<T> implements Subscriber<T>, Disposable {

    private IBaseContract.IBaseView v;

    private Subscription mSubscription;
    /**
     * 是否取消订阅
     */
    private boolean isDisposed = false;

    protected ABaseNetWorkSubscriber(IBaseContract.IBaseView v) {
        this.v = v;
    }

    @Override
    public void onSubscribe(Subscription s) {
        mSubscription = s;
        //判断网络是否可用
        if (!NetWorkUtils.isNetworkConnected(v.getContext())) {
            dispose();
            onError(new ServerErrorException(ServerErrorCode.NETWORK_ERR));
        } else {
            isDisposed = false;
            s.request(1);
        }
        DisposableHelp.addSubscription(this);
    }

    @Override
    public void onError(Throwable t) {
        v.hideLoading();
        onError(ServerErrorCode.errorCodeToMessageShow(t));
    }

    @Override
    public void onNext(T t) {
        if (!isDisposed) {
            onSuccess(t);
        }
    }

    public abstract void onSuccess(T t);

    public abstract void onError(String errStr);


    @Override
    public void onComplete() {
        v.hideLoading();
    }


    @Override
    public void dispose() {
        isDisposed = true;
        if (mSubscription != null) {
            mSubscription.cancel();
        }
    }

    @Override
    public boolean isDisposed() {
        return isDisposed;
    }
}
