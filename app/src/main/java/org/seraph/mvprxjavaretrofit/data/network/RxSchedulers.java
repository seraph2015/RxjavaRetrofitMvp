package org.seraph.mvprxjavaretrofit.data.network;


import org.reactivestreams.Publisher;
import org.seraph.mvprxjavaretrofit.data.network.exception.ServerErrorException;
import org.seraph.mvprxjavaretrofit.ui.module.base.BaseDataResponse;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 进行线程切换（业务和程序逻辑的调度，包括rxjava的生命周期管理）
 * date：2017/7/24 14:21
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class RxSchedulers {


    /**
     * 业务成功code
     */
    private static final int SUCCESS_STATUS = 1;


    /**
     * io线程转main线程，同时包含了业务逻辑的封装转换(返回对应的T对象)
     */
    public static <T> FlowableTransformer<BaseDataResponse<T>, T> io_main_business() {
        return new FlowableTransformer<BaseDataResponse<T>, T>() {
            @Override
            public Publisher<T> apply(@NonNull Flowable<BaseDataResponse<T>> upstream) {
                return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).flatMap(new Function<BaseDataResponse<T>, Flowable<T>>() {
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
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(@NonNull Flowable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }






}
