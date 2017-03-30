package org.seraph.mvprxjavaretrofit.mvp.view;

import org.seraph.mvprxjavaretrofit.ui.adapter.PhotoPreviewAdapter;
import org.seraph.mvprxjavaretrofit.mvp.model.PhotoPreviewBean;

import java.util.List;

/**
 * 图片预览
 * date：2017/2/27 14:24
 * author：xiongj
 * mail：417753393@qq.com
 **/
public interface PhotoPreviewView extends BaseActivityView{

    void setPagerAdapter(PhotoPreviewAdapter mPhotoPreviewAdapter);

    void setCurrentItem(int currentPosition);

    void setMenuClick();

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
}
