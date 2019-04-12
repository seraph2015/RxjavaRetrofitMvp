package org.seraph.mvprxjavaretrofit.ui.module.base;

/**
 * 网络返回数据结构最外层
 * date：2017/2/15 10:38
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class BaseDataResponse<T> {

    /**
     * 业务逻辑标识
     */
    public int code = -1;
    /**
     * 提示信息
     */
    public String message;


    public T result;

}
