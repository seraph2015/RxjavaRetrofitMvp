package org.seraph.mvprxjavaretrofit.ui.module.common.photopreview;

import org.seraph.mvprxjavaretrofit.ui.module.base.IBasePresenter;
import org.seraph.mvprxjavaretrofit.ui.module.base.IBaseView;

/**
 * 图片预览契约类
 * date：2017/2/27 14:24
 * author：xiongj
 * mail：417753393@qq.com
 **/
interface PhotoPreviewContract{

    /**
     * 操作ui
     */
    interface View extends IBaseView{

        /**
         * 跳转授权
         */
        void startPermissionsActivity(String[] permissions);


    }

    interface Presenter extends IBasePresenter<View> {

        void saveImage(PhotoPreviewBean savePhoto);

        void onActivityResult(int requestCode);

        void unSubscribe();

    }


}
