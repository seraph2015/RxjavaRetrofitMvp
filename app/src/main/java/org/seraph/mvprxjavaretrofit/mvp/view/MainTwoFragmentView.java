package org.seraph.mvprxjavaretrofit.mvp.view;

import android.widget.ListAdapter;

import org.seraph.mvprxjavaretrofit.mvp.model.PhotoPreviewBean;

import java.util.ArrayList;

/**
 * 第2页的v
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

    /**
     * 设置搜索关键字
     */
    void setSearchInput(String item);

    void setTitle(String title);

    /**
     * 更新头部toolbar透明度百分比
     */
    void upDataToolbarAlpha(int i);

    /**
     * 跳转图片预览
     */
    void startPhotoPreview(ArrayList<PhotoPreviewBean> photoList, int position);
}
