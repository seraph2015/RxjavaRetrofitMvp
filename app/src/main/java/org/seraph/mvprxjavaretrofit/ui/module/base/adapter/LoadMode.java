package org.seraph.mvprxjavaretrofit.ui.module.base.adapter;

/**
 * 加载模式
 * date：2017/10/30 19:33
 * author：Seraph
 * mail：417753393@qq.com
 **/
public enum LoadMode {
    COMPLETE, //加载成功，还有更多
    END,       //加载成功，没有更多
    FAIL        //加载失败，网络异常
}
