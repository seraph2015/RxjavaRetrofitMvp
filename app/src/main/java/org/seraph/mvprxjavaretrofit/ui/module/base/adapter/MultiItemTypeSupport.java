package org.seraph.mvprxjavaretrofit.ui.module.base.adapter;

/**
 * 多种ItemViewType
 * date：2017/4/12 11:39
 * author：xiongj
 * mail：417753393@qq.com
 **/
public interface MultiItemTypeSupport<T> {

    /**
     * 根据item类型返回对应的布局
     */
    int getLayoutId(int itemType);

    /**
     * 获取对应位置tiem的类型
     *
     * @param position 位置
     * @param t        对应的数据Bean
     */
    int getItemViewType(int position, T t);

}
