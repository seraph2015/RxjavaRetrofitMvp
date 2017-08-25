package org.seraph.mvprxjavaretrofit.data.network.exception;

/**
 * 业务逻辑失败以及部分自定义异常
 * date：2017/2/17 09:39
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class ServerErrorException extends Exception{

    /**
     * 业务失败
     */
    public final static int CODE_STATUS_ERR = 1;
    /**
     * token失效
     */
    public final static int CODE_TOKEN_ERR = 2;
    /**
     * 网络异常
     */
    public final static int CODE_NET_ERR = 3;

    /**
     * 错误码{@link #CODE_TOKEN_ERR,#CODE_STATUS_ERR,#CODE_NET_ERR}
     */
    private int code = -1;


    public ServerErrorException(String message, int code) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
