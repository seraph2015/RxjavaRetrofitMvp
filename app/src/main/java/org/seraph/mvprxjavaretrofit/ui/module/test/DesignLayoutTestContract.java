package org.seraph.mvprxjavaretrofit.ui.module.test;

import org.seraph.mvprxjavaretrofit.ui.module.base.IBaseContract;
import org.seraph.mvprxjavaretrofit.ui.module.main.model.ImageBaiduBean;

import java.util.List;

/**
 * DesignLayout契约类
 * date：2017/4/14 15:54
 * author：xiongj
 * mail：417753393@qq.com
 **/
interface DesignLayoutTestContract extends IBaseContract{

    interface View extends IBaseView {

        void setImageListData(List<ImageBaiduBean.BaiduImage> baiduImages, boolean isMore);
    }

    interface Presenter extends IBasePresenter<View> {

        void requestNextPage();

        void requestRefresh();

    }
}
