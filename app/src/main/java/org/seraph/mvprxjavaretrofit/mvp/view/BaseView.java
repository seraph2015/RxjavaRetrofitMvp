package org.seraph.mvprxjavaretrofit.mvp.view;

import android.content.Context;

/**
 * MVP中View的全局接口
 * date：2017/2/15 09:24
 * author：xiongj
 * mail：417753393@qq.com
 **/
public interface BaseView {


    /**
     * 显示Loading框
     */
    void showLoading();

    void showLoading(String str);

    /**
     * 隐藏Loading框
     */
    void hideLoading();

    /**
     * 显示toast
     */
    void showToast(String str);

    void showToast(int strId);

    Context getContext();

}
