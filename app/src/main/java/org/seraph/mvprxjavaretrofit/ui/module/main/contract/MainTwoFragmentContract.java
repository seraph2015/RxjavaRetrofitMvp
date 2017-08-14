package org.seraph.mvprxjavaretrofit.ui.module.main.contract;

import android.support.v7.widget.RecyclerView;

import org.seraph.mvprxjavaretrofit.ui.module.base.IBaseContract;
import org.seraph.mvprxjavaretrofit.ui.module.common.photopreview.PhotoPreviewBean;

import java.util.ArrayList;

/**
 * 第2页的契约类
 * date：2017/2/21 17:08
 * author：xiongj
 * mail：417753393@qq.com
 **/
public interface MainTwoFragmentContract extends IBaseContract {

    interface Presenter extends IBaseFragmentPresenter<View> {

        void showCacheFilePath();

        void searchHistory();

        void startPicassoToImage();
    }

    interface View extends IBaseFragmentView {

        void setTextView(CharSequence charSequence);

        String getSearchKeyWord();

        /**
         * 设置搜索关键字
         */
        void setSearchInput(String item);

        /**
         * 跳转图片预览
         */
        void startPhotoPreview(ArrayList<PhotoPreviewBean> photoList, int position);

        RecyclerView getRecyclerView();

        android.view.View getHeadView();
    }
}
