package org.seraph.mvprxjavaretrofit.mvp.view;

import android.widget.ListAdapter;

/**
 * 第二页的v
 * date：2017/2/21 17:08
 * author：xiongj
 * mail：417753393@qq.com
 **/
public interface MainTwoFragmentView extends BaseView {

    void setTextView(CharSequence charSequence);

    void setImageAdapter(ListAdapter adapter);

    String getSearchKeyWord();

    /**
     * 0是没有更多，1是加载更多
     */
    void setListFootText(int type);
}
