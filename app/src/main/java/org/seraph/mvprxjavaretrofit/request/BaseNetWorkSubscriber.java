package org.seraph.mvprxjavaretrofit.request;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.seraph.mvprxjavaretrofit.mvp.view.BaseView;
import org.seraph.mvprxjavaretrofit.request.exception.ServerErrorCode;
import org.seraph.mvprxjavaretrofit.utlis.NetWorkUtils;

/**
 * 网络订阅者父类通用操作
 * date：2017/3/15 14:34
 * author：xiongj
 * mail：417753393@qq.com
 **/
public abstract class BaseNetWorkSubscriber<T> implements Subscriber<T> {

    private BaseView mBaseView;

    public BaseNetWorkSubscriber(BaseView mBaseView) {
        this.mBaseView = mBaseView;
    }

    @Override
    public void onSubscribe(Subscription s) {
        //判断网络是否可用
        if (!NetWorkUtils.isNetworkConnected(mBaseView.getContext())) {
            mBaseView.showToast("当前网络不可用，请检查网络情况");
            onComplete();
            s.cancel();
        } else {
            s.request(1);
        }
    }

    @Override
    public void onError(Throwable t) {
        onComplete();
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
        if (mBaseView != null) {
            mBaseView.hideLoading();
        }
    }
}
