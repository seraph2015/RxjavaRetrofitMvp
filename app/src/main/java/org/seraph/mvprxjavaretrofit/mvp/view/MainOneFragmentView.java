package org.seraph.mvprxjavaretrofit.mvp.view;

/**
 * 第1个界面
 * date：2017/2/20 17:06
 * author：xiongj
 * mail：417753393@qq.com
 **/
public interface MainOneFragmentView extends BaseView {

    void setTextViewValue(CharSequence charSequence);

    void setUserTextViewValue(CharSequence charSequence);

    /**
     * 设置标题头
     */
    void setTitle(String title);

    /**
     * 更新toolbar透明度
     */
    void upDataToolbarAlpha(float percentScroll);
}
