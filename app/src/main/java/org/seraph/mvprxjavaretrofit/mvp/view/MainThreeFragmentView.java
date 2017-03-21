package org.seraph.mvprxjavaretrofit.mvp.view;

/**
 * 第3个界面
 * date：2017/2/20 17:06
 * author：xiongj
 * mail：417753393@qq.com
 **/
public interface MainThreeFragmentView extends BaseView {


    void setTextView(CharSequence charSequence);

    String getInputValue();

    void showEmojiValue(CharSequence tempInput);

    void upDataToolbarAlpha(float percentScroll);

    void setTitleAndLogo(String title, int logoIcon);
}
