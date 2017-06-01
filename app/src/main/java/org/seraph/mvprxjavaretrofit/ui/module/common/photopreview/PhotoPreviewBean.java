package org.seraph.mvprxjavaretrofit.ui.module.common.photopreview;

import java.io.Serializable;

/**
 * 图片预览数据
 * date：2017/2/28 09:06
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class PhotoPreviewBean implements Serializable{


    public String objURL;

    public int width;
    public int height;

    public String fromPageTitle;

    /**
     * 图片类型
     */
    public String type;

    /**
     * 图片的来源类型（本地或者网络）默认网络
     */
    public String fromType;
}
