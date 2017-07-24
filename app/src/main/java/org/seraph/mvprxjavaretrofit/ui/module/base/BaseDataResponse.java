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
    public int status = -1;
    /**
     * 提示信息
     */
    public String msg;

//    /* 总数据结构 */
//    public BaseData<T> data;
//
//    public List<T> list;

    public T data;

}
