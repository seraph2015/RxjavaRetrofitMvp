package org.seraph.mvprxjavaretrofit.mvp.model;

import java.io.Serializable;
import java.util.List;

/**
 * 百度图片
 * date：2017/2/22 15:06
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class BaiduImageBean implements Serializable {

    public List<BaiduImage> imgs;

    public class BaiduImage implements Serializable {

        public String thumbURL;
        public String middleURL;
        public String hoverURL;
        public String objURL;

        public int width;
        public int height;

        public String fromPageTitle;

        /**
         * 图片类型
         */
        public String type;

        public boolean isShowTitle = false;
    }
}
