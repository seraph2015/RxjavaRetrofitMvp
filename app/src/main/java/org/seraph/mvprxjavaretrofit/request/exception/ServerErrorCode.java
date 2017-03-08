package org.seraph.mvprxjavaretrofit.request.exception;

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
    public static String errorCodeToMessageShow(Throwable e) {
        String message = e.getMessage();
        if (e instanceof ServerErrorException) {
            message = errorCodeToMessage(e.getMessage());
        } else if (e instanceof java.net.SocketTimeoutException) {
            message = "连接服务器超时";
        }else if(e instanceof javax.net.ssl.SSLHandshakeException){
            message = "缺少https证书";
        }
        return message;
    }

}
