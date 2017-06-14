package org.seraph.mvprxjavaretrofit.ui.module.main;

import android.content.Intent;

import org.seraph.mvprxjavaretrofit.ui.module.base.IBasePresenter;
import org.seraph.mvprxjavaretrofit.ui.module.base.IBaseView;

import java.util.ArrayList;

/**
 * 第4个界面
 * date：2017/2/20 17:06
 * author：xiongj
 * mail：417753393@qq.com
 **/
public interface MainFourFragmentContract {

    interface View extends IBaseView {

        void setImageList(ArrayList<String> imageList);

        void startLocalImageActivity(ArrayList<String> imageList);

        void startPhotoPreview(ArrayList<String> imageList, int position);

    }

    interface Presenter extends IBasePresenter<View> {


        void doTakePhoto();

        void doSelectedLocalImage();

        void removePath(String path);

        void onLocalImageResult(Intent data);

        void photoPreview(int position);

        void onCameraComplete();
    }
}
