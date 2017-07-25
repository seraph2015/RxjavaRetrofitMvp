package org.seraph.mvprxjavaretrofit.ui.module.main.contract;

import android.content.Intent;

import org.seraph.mvprxjavaretrofit.ui.module.base.IBaseContract;

import java.util.ArrayList;

/**
 * 第4个界面
 * date：2017/2/20 17:06
 * author：xiongj
 * mail：417753393@qq.com
 **/
public interface MainFourFragmentContract extends IBaseContract {

    interface View extends IBaseFragmentView {

        void setImageList(ArrayList<String> imageList);

        void startLocalImageActivity(ArrayList<String> imageList);

        void startPhotoPreview(ArrayList<String> imageList, int position);

    }

    interface Presenter extends IBaseFragmentPresenter<View> {


        void doTakePhoto();

        void doSelectedLocalImage();

        void removePath(String path);

        void onLocalImageResult(Intent data);

        void photoPreview(int position);

        void onCameraComplete();

        void uploadFile();
    }
}
