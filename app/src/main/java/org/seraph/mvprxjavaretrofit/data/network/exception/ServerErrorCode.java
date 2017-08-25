package org.seraph.mvprxjavaretrofit.data.network.exception;

import android.content.Context;
import android.content.Intent;

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import org.seraph.mvprxjavaretrofit.data.local.db.help.UserBeanHelp;
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
