package org.seraph.mvprxjavaretrofit.ui.module.common.photopreview;

import android.content.Intent;


import org.seraph.mvprxjavaretrofit.ui.module.base.IBaseContract;

import java.util.ArrayList;

/**
 * 图片预览契约类
 * date：2017/2/27 14:24
 * author：xiongj
 * mail：417753393@qq.com
 **/
interface PhotoPreviewContract extends IBaseContract {

    /**
     * 操作ui
     */
    interface View extends IBaseActivityView {

        void finish();

        void setPhotoList(ArrayList<PhotoPreviewBean> mPhotoList);

        void showPageSelected(int position, int size);

        void hideSaveBtn();

    }

    interface Presenter extends IBaseActivityPresenter<View> {

        void saveImage();

        void unSubscribe();

        void setIntent(Intent intent);

        void upDataCurrentPosition(int position);

    }


}
