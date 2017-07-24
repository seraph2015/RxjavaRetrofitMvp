package org.seraph.mvprxjavaretrofit.data.network;


import org.reactivestreams.Publisher;
import org.seraph.mvprxjavaretrofit.ui.module.base.BaseDataResponse;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.annotations.NonNull;

/**
 * 进行线程切换（业务和程序逻辑的调度，包括rxjava的生命周期管理）
 * date：2017/7/24 14:21
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class RxSchedulers {

    /**
     * io线程转main线程
     */
    public static <T> FlowableTransformer<T, T> io_main() {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(@NonNull Flowable<T> upstream) {
                return RxServerData.getDataProcessing(upstream);
            }
        };
    }

    /**
     * io线程转main线程，同时包含了业务逻辑的封装转换
     */
    public static <T> FlowableTransformer<BaseDataResponse<T>, BaseDataResponse<T>> io_main_business() {
        return new FlowableTransformer<BaseDataResponse<T>, BaseDataResponse<T>>() {
            @Override
            public Publisher<BaseDataResponse<T>> apply(@NonNull Flowable<BaseDataResponse<T>> upstream) {
                return RxServerData.getPublicDataProcessing(upstream);
            }
        };
    }


}
