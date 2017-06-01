package org.seraph.mvprxjavaretrofit.ui.module.common.photolist;

import java.io.Serializable;

/**
 * 本地选择图片bean
 * date：2017/5/18 14:42
 * author：xiongj
 * mail：417753393@qq.com
 **/
class LocalImageBean implements Serializable{

    public int id; // 图片Id
    public String path; // 图片绝对路径
    public boolean isSelected = false;

    public LocalImageBean(int id, String path) {
        this.id = id;
        this.path = path;
    }

}
