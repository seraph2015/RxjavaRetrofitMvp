package org.seraph.mvprxjavaretrofit.ui.module.test;

import org.seraph.mvprxjavaretrofit.ui.module.base.IABaseContract;
import org.seraph.mvprxjavaretrofit.ui.module.main.model.ImageBaiduBean;

import java.util.List;

/**
 * DesignLayout契约类
 * date：2017/4/14 15:54
 * author：xiongj
 * mail：417753393@qq.com
 **/
interface DesignLayoutTestContract extends IABaseContract {

    interface View extends IBaseActivityView {

        void setImageListData(List<ImageBaiduBean.BaiduImage> baiduImages, boolean isMore);

        void onLoadErr();

    }

    abstract class Presenter extends ABaseActivityPresenter<View> {

    }
}
