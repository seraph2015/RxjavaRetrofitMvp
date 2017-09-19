package org.seraph.mvprxjavaretrofit.data.network.exception;

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import org.seraph.mvprxjavaretrofit.ui.module.base.IBaseContract;
import org.seraph.mvprxjavaretrofit.utlis.Tools;

import java.net.ConnectException;

/**
 * 业务逻辑异常信息处理
 * date：2017/2/17 09:48
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class ServerErrorCode {

    private IBaseContract.IBaseView mView;

    public ServerErrorCode() {
    }

    public ServerErrorCode(IBaseContract.IBaseView view) {
        this.mView = view;
    }


    public final static String HTTP_EXCEPTION = "网络异常";
    public final static String CONNECT_EXCEPTION = "连接失败";
    public final static String SOCKET_TIMEOUT_EXCEPTION = "连接服务器超时";
    public final static String SSL_HANDSHAKE_EXCEPTION = "证书验证失败";
    public final static String MALFORMED_JSON_EXCEPTION = "Json转换异常";
    public final static String OTHER_EXCEPTION = "未知异常";


    /**
     * 根据对应的code进行不同的处理
     */
    private void errorCodeToMessage(ServerErrorException errorCode) {
        if (mView == null) {
            return;
        }
        //通过view的持有，获取对应的对象进行业务处理
        switch (errorCode.getCode()) {
            case ServerErrorException.CODE_NET_ERR://网络连接失败
                break;
            case ServerErrorException.CODE_STATUS_ERR://业务失败
                break;
            case ServerErrorException.CODE_TOKEN_ERR://token 失效
                break;
        }
    }

    /**
     * 显示错误信息
     *
     * @see ServerErrorException 自定义异常可以配合 {@link #errorCodeToMessage(ServerErrorException)} 进行异常处理
     */
    public String errorCodeToMessageShow(Throwable e) {
        String message = e.getMessage();
        if (e instanceof ServerErrorException) { //自定义异常，进行自定义词典进行转义（包含自定义和业务逻辑失败）
            errorCodeToMessage((ServerErrorException) e);
        } else if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            message = HTTP_EXCEPTION + " " + httpException.code();
        } else if (e instanceof ConnectException) {
            message = CONNECT_EXCEPTION;
        } else if (e instanceof java.net.SocketTimeoutException) {
            message = SOCKET_TIMEOUT_EXCEPTION;
        } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
            message = SSL_HANDSHAKE_EXCEPTION;
        } else if (e instanceof com.google.gson.stream.MalformedJsonException) {
            message = MALFORMED_JSON_EXCEPTION;
        } else if (Tools.isNull(message)) {
            message = OTHER_EXCEPTION;
        }
        return message;
    }

}
