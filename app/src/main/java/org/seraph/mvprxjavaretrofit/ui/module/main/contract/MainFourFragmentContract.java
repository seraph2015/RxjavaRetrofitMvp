package org.seraph.mvprxjavaretrofit.ui.module.main.contract;

import android.support.v4.app.Fragment;

import org.seraph.mvprxjavaretrofit.ui.module.base.IABaseContract;

import java.util.ArrayList;

/**
 * 第4个界面
 * date：2017/2/20 17:06
 * author：xiongj
 * mail：417753393@qq.com
 **/
public interface MainFourFragmentContract extends IABaseContract {

    interface View extends IBaseFragmentView {

        void setImageList(ArrayList<String> imageList);

        void startLocalImageActivity(ArrayList<String> imageList);

        void startPhotoPreview(ArrayList<String> imageList, int position);

        Fragment getFragment();
    }

    abstract class Presenter extends ABaseFragmentPresenter<View> {


    }
}
