package org.seraph.mvprxjavaretrofit.request.exception;

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import org.seraph.mvprxjavaretrofit.utlis.Tools;

import java.net.ConnectException;

/**
 * 业务逻辑信息词义
 * date：2017/2/17 09:48
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class ServerErrorCode {


    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;


    /**
     * serverErrorCode解析（服务器返回的自定义消息转换）
     */
    private static String errorCodeToMessage(String errorCode) {
        String tempError;
        switch (errorCode) {

            default:
                tempError = errorCode;
        }
        return tempError;
    }

    /**
     * 显示错误信息
     */
    public static String errorCodeToMessageShow(Throwable e) {
        String message = e.getMessage();
        if (e instanceof ServerErrorException) {
            message = errorCodeToMessage(message);
        } else if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            message = "网络错误:code " + httpException.code();
        } else if (e instanceof ConnectException) {
            message = "连接失败";
        } else if (e instanceof java.net.SocketTimeoutException) {
            message = "连接服务器超时";
        } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
            message = "https证书验证失败";
        } else if (e instanceof com.google.gson.stream.MalformedJsonException) {
            message = "Json转换异常";
        } else if (Tools.isNull(message)) {
            message = "未知错误";
        }
        return message;
    }

}
