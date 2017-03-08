package org.seraph.mvprxjavaretrofit.mvp.view;

import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;

import org.seraph.mvprxjavaretrofit.utlis.FragmentController;

/**
 * 主界面view
 * date：2017/2/15 15:41
 * author：xiongj
 * mail：417753393@qq.com
 **/
public interface MainActivityView extends BaseActivityView {

    /**
     * * 设置导航栏元素属性
     * @param position  位置
     * @param bgColor   背景颜色
     * @param resId     图标
     * @param textColor 字体颜色
     */
    void setMenuItem(int position, @ColorInt int bgColor, @DrawableRes int resId, @ColorInt int textColor);

    /**
     * 获取下菜单选项个数
     */
    int getMenuChildCount();

    /**
     * 获取碎片管理器
     */
    FragmentController getFragmentController();
}
