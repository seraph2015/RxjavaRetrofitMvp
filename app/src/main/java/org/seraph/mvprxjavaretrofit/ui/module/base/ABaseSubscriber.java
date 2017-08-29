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
public abstract class ABaseSubscriber<T> implements Subscriber<T> {

    private IBaseContract.IBaseView mView;

    private ServerErrorCode errorCode;

    protected ABaseSubscriber(IBaseContract.IBaseView view) {
        this.mView = view;
        errorCode = new ServerErrorCode(mView);
    }

    public ABaseSubscriber() {
        errorCode = new ServerErrorCode();
    }

    @Override
    public void onSubscribe(Subscription s) {
        //判断网络是否可用
        if (!NetworkUtils.isConnected()) {
            onError(new ServerErrorException("当前网络不可用，请检查网络情况", ServerErrorException.CODE_NET_ERR));
        } else {
            s.request(1);
        }
    }

    @Override
    public void onError(Throwable t) {
        onError(errorCode.errorCodeToMessageShow(t));
        if (mView != null)
            mView.hideLoading();
    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    public abstract void onSuccess(T t);

    public abstract void onError(String errStr);


    @Override
    public void onComplete() {
        if (mView != null)
            mView.hideLoading();
    }

}
