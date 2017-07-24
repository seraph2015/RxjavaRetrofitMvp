package org.seraph.mvprxjavaretrofit.ui.module.test;

import android.content.Context;
import android.widget.ImageView;

import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.data.network.picasso.PicassoTool;
import org.seraph.mvprxjavaretrofit.ui.module.base.adapter.BaseRvListAdapter;
import org.seraph.mvprxjavaretrofit.ui.module.main.model.ImageBaiduBean;
import org.seraph.mvprxjavaretrofit.utlis.Tools;
import org.seraph.mvprxjavaretrofit.ui.module.base.adapter.ViewHolderRv;

import javax.inject.Inject;

/**
 * 测试布局的适配器
 * date：2017/4/13 17:54
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class DesignLayoutAdapter extends BaseRvListAdapter<ImageBaiduBean.BaiduImage> {

    private int targetWidth;
    private int targetHeight;

    @Inject
    DesignLayoutAdapter(Context mContext) {
        super(mContext, R.layout.test_item_design);
        targetWidth = Tools.dip2px(mContext, 200);
        targetHeight = Tools.dip2px(mContext, 150);
    }

    @Override
    protected void bindData(ViewHolderRv holder, ImageBaiduBean.BaiduImage baiduImage, int position) {
        ImageView imageView = holder.getView(R.id.imageView);
        PicassoTool.loadCache(mContext, baiduImage.objURL, imageView, targetWidth, targetHeight);
    }


}
