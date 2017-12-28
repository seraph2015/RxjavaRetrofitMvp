package org.seraph.mvprxjavaretrofit.data.network.rx;


import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;

import org.reactivestreams.Publisher;
import org.seraph.mvprxjavaretrofit.data.network.exception.ServerErrorException;
import org.seraph.mvprxjavaretrofit.ui.module.base.BaseDataResponse;
import org.seraph.mvprxjavaretrofit.ui.module.base.IBaseContract;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 进行线程切换（业务和程序逻辑的调度），
 * 使用{@link com.trello.rxlifecycle2.LifecycleProvider } 接口的实现类进行RxJava的生命周期管理。
 *
 * @see Flowable#compose(FlowableTransformer) 操作符进行使用
 * date：2017/7/24 14:21
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class RxSchedulers {


    /**
     * 业务成功code
     */
    private static final int SUCCESS_STATUS = 0;


    public static <T> FlowableTransformer<BaseDataResponse<T>, T> io_main_business() {
        return io_main_business(null);
    }


    /**
     * io线程转main线程，同时包含了业务逻辑的封装转换(返回对应的T对象)
     *
     * @param view IBaseContract.IBaseView的实现类，实现了bindToLifecycle方法用来管理Rxjava生命周期
     */
    public static <T> FlowableTransformer<BaseDataResponse<T>, T> io_main_business(final IBaseContract.IBaseView view) {

        return new FlowableTransformer<BaseDataResponse<T>, T>() {
            @Override
            public Publisher<T> apply(@NonNull Flowable<BaseDataResponse<T>> upstream) {
                Flowable<BaseDataResponse<T>> tempUpstream = upstream.delay(2, TimeUnit.SECONDS);//延时2秒
                //如果有传递过来需要管理绑定rxjava生命周期的view，则使用新的Transformer
                if (view != null) {
                    if (view instanceof IBaseContract.IBaseFragmentView) {//在对应的生命周期进行关闭
                        tempUpstream = (Flowable<BaseDataResponse<T>>) ((IBaseContract.IBaseFragmentView) view).<BaseDataResponse<T>>bindUntilEvent(FragmentEvent.DETACH).apply(tempUpstream);
                    } else if (view instanceof IBaseContract.IBaseActivityView) {
                        tempUpstream = (Flowable<BaseDataResponse<T>>) ((IBaseContract.IBaseActivityView) view).<BaseDataResponse<T>>bindUntilEvent(ActivityEvent.DESTROY).apply(tempUpstream);
                    }
//                    else {
//                        tempUpstream = (Flowable<BaseDataResponse<T>>) view.<BaseDataResponse<T>>bindToLifecycle().apply(tempUpstream);
//                    }
                }
                return tempUpstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).flatMap(new Function<BaseDataResponse<T>, Flowable<T>>() {
                    @Override
                    public Flowable<T> apply(BaseDataResponse<T> tBaseDataResponse) throws Exception {
                        switch (tBaseDataResponse.status) {
                            case SUCCESS_STATUS: //成功
                                T t = tBaseDataResponse.data;
                                if (t == null) {
                                    //约束后台，在有对象的时候不允许返回null，最好的办法是在 tBaseDataResponse.data 泛型T 如果没有返回值则返回空对象data{}
                                    t = (T) "";
                                }
                                return Flowable.just(t);
                            default://业务失败
                                return Flowable.error(new ServerErrorException(tBaseDataResponse.msg, tBaseDataResponse.status));
                        }
                    }
                });
            }
        };
    }


    /**
     * io线程转main线程
     */
    public static <T> FlowableTransformer<T, T> io_main() {
        return io_main(null);
    }


    /**
     * io线程转main线程
     */
    public static <T> FlowableTransformer<T, T> io_main(final IBaseContract.IBaseView view) {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(@NonNull Flowable<T> upstream) {
                Flowable<T> tempUpstream = upstream;
                //如果有传递过来需要管理绑定rxjava生命周期的view，则使用新的Transformer
                if (view != null) {
                    tempUpstream = (Flowable<T>) view.<T>bindToLifecycle().apply(tempUpstream);
                }
                return tempUpstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


}
