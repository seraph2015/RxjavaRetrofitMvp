package org.seraph.mvprxjavaretrofit.ui.module.base;

/**
 * mvp框架P层父类接口
 * date：2017/3/29 15:38
 * author：xiongj
 * mail：417753393@qq.com
 **/
public interface IBasePresenter<T> {

    void start();
    void setView(T t);
}
