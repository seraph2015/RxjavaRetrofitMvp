package org.seraph.mvprxjavaretrofit.ui.module.common.photolist;

import android.content.Intent;

import org.seraph.mvprxjavaretrofit.ui.module.base.IBasePresenter;
import org.seraph.mvprxjavaretrofit.ui.module.base.IBaseView;

import java.util.ArrayList;
import java.util.List;

/**
 * 本地图库
 * date：2017/5/18 16:01
 * author：xiongj
 * mail：417753393@qq.com
 **/
public interface LocalImageListContract {


    interface View extends IBaseView {

        void setQueryImageList(List<LocalImageBean> localImageBeen);

        void setResult(ArrayList<String> arrayList);

        void setSelectedPath(ArrayList<String> arrayList);
    }

    interface Presenter extends IBasePresenter<View> {

        void save(ArrayList<String> arrayList);

        void setIntent(Intent intent);
    }

}
