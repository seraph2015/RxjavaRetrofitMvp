package org.seraph.mvprxjavaretrofit.mvp.model;

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


}
