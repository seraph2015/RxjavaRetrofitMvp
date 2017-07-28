package org.seraph.mvprxjavaretrofit.data.network.rx;


import org.reactivestreams.Publisher;
import org.seraph.mvprxjavaretrofit.data.network.exception.ServerErrorException;
import org.seraph.mvprxjavaretrofit.ui.module.base.BaseDataResponse;
import org.seraph.mvprxjavaretrofit.ui.module.base.IBaseContract;

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
                Flowable<BaseDataResponse<T>> tempUpstream = upstream;
                //如果有传递过来需要管理绑定rxjava生命周期的view，则使用新的Transformer
                if (view != null) {
                    tempUpstream = (Flowable<BaseDataResponse<T>>) view.<BaseDataResponse<T>>bindToLifecycle().apply(tempUpstream);
                }
                return tempUpstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).flatMap(new Function<BaseDataResponse<T>, Flowable<T>>() {
                    @Override
                    public Flowable<T> apply(BaseDataResponse<T> tBaseDataResponse) throws Exception {
                        if (tBaseDataResponse.status != SUCCESS_STATUS) { //业务逻辑失败
                            return Flowable.error(new ServerErrorException(tBaseDataResponse.msg));
                        }
                        return Flowable.just(tBaseDataResponse.data);
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