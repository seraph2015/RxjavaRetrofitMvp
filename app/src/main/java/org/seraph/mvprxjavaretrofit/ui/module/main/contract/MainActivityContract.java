package org.seraph.mvprxjavaretrofit.ui.module.main.contract;

import android.graphics.drawable.Drawable;

import org.seraph.mvprxjavaretrofit.ui.module.base.IABaseContract;

/**
 * main契约类
 * date：2017/4/6 15:11
 * author：xiongj
 * mail：417753393@qq.com
 **/
public interface MainActivityContract extends IABaseContract {

    interface View extends IBaseView {

        void setTitle(String title);

        void setBackgroundResource(Drawable drawable);

    }
    //使用接口进行中间调用设计，关闭直接使用View类的完全持有
   abstract class Presenter extends ABasePresenter<View> {



   }

}
