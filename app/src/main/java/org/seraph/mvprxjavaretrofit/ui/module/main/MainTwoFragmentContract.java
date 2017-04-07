package org.seraph.mvprxjavaretrofit.ui.module.main;

import android.widget.ListAdapter;

import org.seraph.mvprxjavaretrofit.ui.module.common.photopreview.PhotoPreviewBean;
import org.seraph.mvprxjavaretrofit.ui.module.base.IBasePresenter;
import org.seraph.mvprxjavaretrofit.ui.module.base.IBaseView;

import java.util.ArrayList;

/**
 * 第2页的契约类
 * date：2017/2/21 17:08
 * author：xiongj
 * mail：417753393@qq.com
 **/
interface MainTwoFragmentContract {

    interface Presenter extends IBasePresenter<View> {

        void showCacheFilePath();

        void searchHistory();

        void startPicassoToImage();

        void loadMoreImage();

        void onItemClick(int position);

        void unSubscriber();
    }

    interface View extends IBaseView {

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

        /**
         * 跳转图片预览
         */
        void startPhotoPreview(ArrayList<PhotoPreviewBean> photoList, int position);

    }
}
