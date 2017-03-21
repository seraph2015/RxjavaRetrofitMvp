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
     * 更新toolbar透明度
     */
    void upDataToolbarAlpha(float percentScroll);

    void setTitleAndLogo(String title, int logoIcon);
}
