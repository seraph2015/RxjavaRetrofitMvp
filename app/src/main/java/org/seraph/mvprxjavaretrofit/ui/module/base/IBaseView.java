package org.seraph.mvprxjavaretrofit.ui.module.base;

import android.content.Context;

/**
 * MVP框架V层父类接口
 * date：2017/3/29 15:39
 * author：xiongj
 * mail：417753393@qq.com
 **/
public interface IBaseView {

    void showToast(String str);

    void showLoading(String str);

    void hideLoading();

    Context getContext();
}
