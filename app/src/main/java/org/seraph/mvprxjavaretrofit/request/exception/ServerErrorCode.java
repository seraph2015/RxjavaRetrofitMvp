package org.seraph.mvprxjavaretrofit.request.exception;

import org.seraph.mvprxjavaretrofit.mvp.view.BaseView;

/**
 * 业务逻辑信息词义
 * date：2017/2/17 09:48
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class ServerErrorCode {

    /**
     * serverErrorCode解析
     */
    public static String errorCodeToMessage(String errorCode) {



        return errorCode;
    }

    /**
     * 显示错误信息
     */
    public static String errorCodeToMessageShow(Throwable e, BaseView baseView) {
        baseView.hideLoading();
        String message = e.getMessage();
        if (e instanceof ServerErrorException) {
            message = errorCodeToMessage(e.getMessage());
            baseView.showSnackBar(message);
            return message;
        }
        baseView.showSnackBar(message);
        return message;
    }

}
