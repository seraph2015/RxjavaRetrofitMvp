package org.seraph.mvprxjavaretrofit.mvp.view;

import android.content.Context;

import org.seraph.mvprxjavaretrofit.adapter.PhotoPreviewAdapter;

/**
 * 图片预览
 * date：2017/2/27 14:24
 * author：xiongj
 * mail：417753393@qq.com
 **/
public interface PhotoPreviewView extends BaseActivityView{

    Context getContext();

    void setPagerAdapter(PhotoPreviewAdapter mPhotoPreviewAdapter);

    void setCurrentItem(int currentPosition);

    void setMenuClick();

}
