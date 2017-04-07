package org.seraph.mvprxjavaretrofit.ui.module.common.photopreview;

import android.content.Context;

import org.seraph.mvprxjavaretrofit.ui.module.base.IBasePresenter;
import org.seraph.mvprxjavaretrofit.ui.module.base.IBaseView;

import java.util.List;

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

        Context getContext();

        void setPagerAdapter(PhotoPreviewAdapter photoPreviewAdapter);

        /**
         * 获取照片列表
         */
        List<PhotoPreviewBean> getPhotoList();

        /**
         * 当前第几张照片
         */
        int getCurrentPosition();

        /**
         * 跳转授权
         */
        void startPermissionsActivity(String[] permissions);

        /**
         * 切换头部的隐藏和显示
         */
        void switchToolBarVisibility();

        /**
         * 跳转到指定页和保存当前
         */
        void onPageSelected(int currentPosition);
    }

    interface Presenter extends IBasePresenter<View> {


        void saveImage();

        void onActivityResult(int requestCode);

        void unSubscribe();

    }


}
