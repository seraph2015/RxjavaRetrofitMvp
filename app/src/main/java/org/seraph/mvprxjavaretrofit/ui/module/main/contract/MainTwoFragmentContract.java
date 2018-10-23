package org.seraph.mvprxjavaretrofit.ui.module.main.contract;

import org.seraph.mvprxjavaretrofit.ui.module.base.IABaseContract;
import org.seraph.mvprxjavaretrofit.ui.module.common.photopreview.PhotoPreviewBean;
import org.seraph.mvprxjavaretrofit.ui.module.main.model.ImageBaiduBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 第2页的契约类
 * date：2017/2/21 17:08
 * author：xiongj
 * mail：417753393@qq.com
 **/
public interface MainTwoFragmentContract extends IABaseContract {


    interface View extends IBaseView {

        void setTextView(CharSequence charSequence);

        String getSearchKeyWord();

        /**
         * 设置搜索关键字
         */
        void setSearchInput(String item);

        /**
         * 跳转图片预览
         */
        void startPhotoPreview(ArrayList<PhotoPreviewBean> photoList, int position);

        void setListDate(List<ImageBaiduBean.BaiduImage> baiduImages);
    }
}
