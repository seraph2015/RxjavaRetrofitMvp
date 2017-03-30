package org.seraph.mvprxjavaretrofit.mvp.view;

import org.seraph.mvprxjavaretrofit.utli.FragmentController;

/**
 * 主界面view
 * date：2017/2/15 15:41
 * author：xiongj
 * mail：417753393@qq.com
 **/
public interface MainActivityView extends BaseActivityView {


    /**
     * 获取碎片管理器
     */
    FragmentController getFragmentController();
}
