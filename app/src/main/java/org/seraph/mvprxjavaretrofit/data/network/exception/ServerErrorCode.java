package org.seraph.mvprxjavaretrofit.data.network.exception;

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


    public static final String NETWORK_ERR = "NETWORK_ERR";

    /**
     * serverErrorCode解析（服务器返回的自定义消息转换）
     */
    private static String errorCodeToMessage(String errorCode) {
        String tempError;
        switch (errorCode) {
            case NETWORK_ERR:
                tempError = "当前网络不可用，请检查网络情况";
                break;
            default:
                tempError = errorCode;
        }
        return tempError;
    }

    /**
     * 显示错误信息
     *@see ServerErrorException 自定义异常可以配合 {@link #errorCodeToMessage(String)} 进行转义
     */
    public static String errorCodeToMessageShow(Throwable e) {
        String message = e.getMessage();
        if (e instanceof ServerErrorException) { //自定义异常，进行自定义词典进行转义（包含自定义和业务逻辑失败）
            message = errorCodeToMessage(message);
        } else if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            message = "网络异常:code " + httpException.code();
        } else if (e instanceof ConnectException) {
            message = "连接失败";
        } else if (e instanceof java.net.SocketTimeoutException) {
            message = "连接服务器超时";
        } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
            message = "https证书验证失败";
        } else if (e instanceof com.google.gson.stream.MalformedJsonException) {
            message = "Json转换异常";
        } else if (Tools.isNull(message)) {
            message = "未知异常";
        }
        return message;
    }

}
