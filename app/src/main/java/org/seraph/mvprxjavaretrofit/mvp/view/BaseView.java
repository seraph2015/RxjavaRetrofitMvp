package org.seraph.mvprxjavaretrofit.mvp.view;

import android.app.Activity;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.view.View;

/**
 * MVP中View的全局接口
 * date：2017/2/15 09:24
 * author：xiongj
 * mail：417753393@qq.com
 **/
public interface BaseView {

    /**
     * 获取上下文
     */
    Activity getContext();

    /**
     * 显示Loading框
     */
    void showLoading();

    /**
     * 隐藏Loading框
     */
    void hideLoading();

    /**
     * 显示toast
     */
    void showToast(String str);

    void showToast(int strId);

    /**
     * 显示SnackBar
     */
    void showSnackBar(String str);

    void showSnackBar(int strId);

    /**
     * 设置标题
     */
    void setTitle(String title);

    /**
     * 设置返回监听
     */
    void setBackListener(View.OnClickListener onClickListener);

    /**
     * 设置返回按钮icon
     */
    void setBackIcon(@DrawableRes int resId);

    /**
     * 设置整个界面的背景颜色
     */
    void setBackgroundColor(@ColorInt int backgroundColor);

    /**
     * 设置整个界面的背景图片
     */
    void setBackgroundResource(@DrawableRes int resId);

    /**
     * 设置toolbar背景颜色
     */
    void setToolBarBackgroundColor(@ColorInt int color);

    /**
     * 设置toolbar背景图片
     */
    void setToolBarBackgroundResource(@DrawableRes int resId);


    /**
     * 显示左侧按钮
     */
    void showToolBarNavigation();

    void showToolBar();

    void hideToolBar();

    /**
     * 状态栏是否沉浸模式
     */
    boolean isImmersionMode();


    /**
     * 关闭当前界面
     */
    void viewFinish();

    /**
     * 1.初始化固定配置
     */
     void initConfig();

    /**
     * 5.设置状态栏模式
     */
    void setStatusBarImmersionMode(boolean isImmersion);
}
