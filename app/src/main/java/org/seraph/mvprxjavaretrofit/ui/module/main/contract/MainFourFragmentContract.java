package org.seraph.mvprxjavaretrofit.ui.module.main.contract;

import androidx.fragment.app.Fragment;

import org.seraph.mvprxjavaretrofit.ui.module.base.IABaseContract;

import java.util.ArrayList;

/**
 * 第4个界面
 * date：2017/2/20 17:06
 * author：xiongj
 * mail：417753393@qq.com
 **/
public interface MainFourFragmentContract extends IABaseContract {

    interface View extends IBaseView {

        void setImageList(ArrayList<String> imageList);

        void startLocalImageActivity(ArrayList<String> imageList);

        void startPhotoPreview(ArrayList<String> imageList, int position);

        Fragment getFragment();
    }

    abstract class Presenter extends ABasePresenter<View> {


    }
}
