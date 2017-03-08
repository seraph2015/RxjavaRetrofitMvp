package org.seraph.mvprxjavaretrofit.request;

import org.seraph.mvprxjavaretrofit.mvp.model.BaseResponse;
import org.seraph.mvprxjavaretrofit.request.exception.ServerErrorException;

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
    private static final int SUCCESS_STATUS = 0;


    /**
     * 线程处理和部分业务处理
     */
    static <T> Flowable<BaseResponse<T>> getPublicDataProcessing(Flowable<BaseResponse<T>> flowable) {
        return flowable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).flatMap(new Function<BaseResponse<T>, Flowable<BaseResponse<T>>>() {
            @Override
            public Flowable<BaseResponse<T>> apply(BaseResponse<T> tBaseResponse) throws Exception {
                if (tBaseResponse.status != SUCCESS_STATUS) { //业务逻辑失败
                    Flowable.error(new ServerErrorException(tBaseResponse.msg));
                }
                return Flowable.just(tBaseResponse).subscribeOn(AndroidSchedulers.mainThread());
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
