package org.seraph.mvprxjavaretrofit.ui.module.test;

import com.blankj.utilcode.util.SizeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.data.network.ImageLoad.picasso.PicassoTool;
import org.seraph.mvprxjavaretrofit.ui.module.main.model.ImageBaiduBean;
import org.seraph.mvprxjavaretrofit.ui.views.CustomSelfProportionImageView;

import javax.inject.Inject;

/**
 * 测试布局的适配器
 * date：2017/4/13 17:54
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class DesignLayoutAdapter extends BaseQuickAdapter<ImageBaiduBean.BaiduImage, BaseViewHolder> {

    private int targetWidth;
    private int targetHeight;

    @Inject
    DesignLayoutAdapter() {
        super(R.layout.test_item_design);
        targetWidth = SizeUtils.dp2px(200);
        targetHeight = SizeUtils.dp2px(150);
    }


    @Override
    protected void convert(BaseViewHolder helper, ImageBaiduBean.BaiduImage item) {
        CustomSelfProportionImageView imageView = helper.getView(R.id.imageView);
        PicassoTool.loadCache(mContext, item.objURL, imageView, targetWidth, targetHeight);
    }
}
