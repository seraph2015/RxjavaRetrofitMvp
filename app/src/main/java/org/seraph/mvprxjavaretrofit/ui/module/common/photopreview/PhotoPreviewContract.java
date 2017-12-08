package org.seraph.mvprxjavaretrofit.ui.module.common.photopreview;

import org.seraph.mvprxjavaretrofit.ui.module.base.IABaseContract;

import java.util.ArrayList;

/**
 * 图片预览契约类
 * date：2017/2/27 14:24
 * author：xiongj
 * mail：417753393@qq.com
 **/
interface PhotoPreviewContract extends IABaseContract {

    /**
     * 操作ui
     */
    interface View extends IBaseActivityView {

        void finish();

        void setPhotoList(ArrayList<PhotoPreviewBean> mPhotoList);

        void showPageSelected(int position, int size);

        void hideSaveBtn();

    }

    abstract class Presenter extends ABaseActivityPresenter<View> {


    }


}
