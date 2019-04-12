package org.seraph.mvprxjavaretrofit.ui.module.main.contract;

import android.net.Uri;

import org.seraph.mvprxjavaretrofit.ui.module.base.IABaseContract;

import androidx.fragment.app.Fragment;

/**
 * 第1个界面
 * date：2017/2/20 17:06
 * author：xiongj
 * mail：417753393@qq.com
 **/
public interface MainOneFragmentContract extends IABaseContract {

    interface View extends IBaseView {

        void setUserTextViewValue(CharSequence charSequence);

        void onUCropImage(Uri data, Uri fromFile);

        Fragment getFragment();

    }

    abstract class Presenter extends ABasePresenter<View> {


    }
}
