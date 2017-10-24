package org.seraph.mvprxjavaretrofit.ui.module.base;

/**
 * 获取通用Component（为依赖activity的子fragment获取Component而设计）
 * date：2017/7/28 10:54
 * author：xiongj
 * mail：417753393@qq.com
 **/
public interface IComponent<C> {

    C getComponent();
}
