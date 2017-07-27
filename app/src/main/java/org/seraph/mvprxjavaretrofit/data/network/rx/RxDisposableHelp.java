package org.seraph.mvprxjavaretrofit.data.network.rx;

import org.reactivestreams.Subscription;
import org.seraph.mvprxjavaretrofit.ui.module.base.ABaseActivity;
import org.seraph.mvprxjavaretrofit.ui.module.base.ABaseFragment;
import org.seraph.mvprxjavaretrofit.ui.module.base.ABaseNetWorkSubscriber;
import org.seraph.mvprxjavaretrofit.ui.module.base.IBaseContract;

import io.reactivex.FlowableTransformer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 统一的Disposable管理帮助类
 * 帮助在关闭等待框{@link ABaseActivity#showLoading(String)}{@link ABaseFragment#showLoading(String)}自动取消,
 * 所有在{@link ABaseNetWorkSubscriber#onSubscribe(Subscription)}中添加的订阅
 * <p>
 * 其余部分的rxjava生命周期管理交给{@link IBaseContract.IBaseView#bindToLifecycle()}自动或者子类对应的方法进行手动管理
 * 使用方法见示例{@link RxSchedulers#io_main(IBaseContract.IBaseView)}
 * 在{@link io.reactivex.Flowable#compose(FlowableTransformer)}操作符使用
 * <p>
 * date：2017/7/27 11:48
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class RxDisposableHelp {

    private static CompositeDisposable mDisposables;


    private static void init() {
        if (mDisposables == null) {
            mDisposables = new CompositeDisposable();
        }
    }

    /**
     * 加入到订阅列表
     */
    public static void addSubscription(Disposable disposable) {
        if (disposable == null) return;
        init();
        mDisposables.add(disposable);
    }

    public static void dispose(Disposable disposable) {
        if (mDisposables != null) {
            mDisposables.delete(disposable);
        }
    }

    /**
     * 取消所有的订阅
     */
    public static void dispose() {
        if (mDisposables != null) {
            mDisposables.clear();
        }
    }

    public static CompositeDisposable getDisposables() {
        init();
        return mDisposables;
    }

}
