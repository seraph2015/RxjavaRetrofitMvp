package org.seraph.mvprxjavaretrofit.data.network;

import org.seraph.mvprxjavaretrofit.ui.module.base.BaseDataResponse;
import org.seraph.mvprxjavaretrofit.data.network.exception.ServerErrorException;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 处理线程和部分服务器公共业务数据
 * date：2017/2/17 10:45
 * author：xiongj
 * mail：417753393@qq.com
 **/
class RxServerData {

    /**
     * 业务成功code
     */
    private static final int SUCCESS_STATUS = 1;


    /**
     * 线程处理和部分业务处理
     */
    static <T> Flowable<BaseDataResponse<T>> getPublicDataProcessing(Flowable<BaseDataResponse<T>> flowable) {
        return flowable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).flatMap(new Function<BaseDataResponse<T>, Flowable<BaseDataResponse<T>>>() {
            @Override
            public Flowable<BaseDataResponse<T>> apply(BaseDataResponse<T> tBaseDataResponse) throws Exception {
                if (tBaseDataResponse.status != SUCCESS_STATUS) { //业务逻辑失败
                    return Flowable.error(new ServerErrorException(tBaseDataResponse.msg));
                }
                return Flowable.just(tBaseDataResponse);
            }
        });
    }

    /**
     * 线程切换
     */
    static <T> Flowable<T> getDataProcessing(Flowable<T> flowable) {
        return flowable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

}
