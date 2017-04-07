package org.seraph.mvprxjavaretrofit.data.network.exception;

/**
 * 业务逻辑失败
 * date：2017/2/17 09:39
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class ServerErrorException extends Exception{

    public ServerErrorException(String message) {
        super(message);
    }

}
