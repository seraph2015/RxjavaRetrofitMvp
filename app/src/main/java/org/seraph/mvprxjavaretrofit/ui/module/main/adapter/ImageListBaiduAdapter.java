package org.seraph.mvprxjavaretrofit.ui.module.main.adapter;

import android.net.Uri;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;

import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.data.network.ImageLoad.fresco.FrescoTool;
import org.seraph.mvprxjavaretrofit.data.network.ImageLoad.picasso.PicassoTool;
import org.seraph.mvprxjavaretrofit.ui.module.main.model.ImageBaiduBean;
import org.seraph.mvprxjavaretrofit.ui.views.CustomSelfProportionImageView;

import javax.inject.Inject;

/**
 * 图片列表
 * date：2017/2/22 13:43
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class ImageListBaiduAdapter extends BaseQuickAdapter<ImageBaiduBean.BaiduImage, BaseViewHolder> {


    @Inject
    public ImageListBaiduAdapter() {
        super(R.layout.test_item_image);
    }


    @Override
    protected void convert(BaseViewHolder helper, ImageBaiduBean.BaiduImage item) {
        CustomSelfProportionImageView imageView = helper.getView(R.id.image);
        imageView.setSize(item.width, item.height);
        //按照控件的大小来缩放图片的尺寸
//        int width = item.width;
//        int height = item.height;
//        if (width != 0) {
//            height = Tools.getNewHeight(width, height, screenWidth);
//            width = screenWidth;
//        }
        PicassoTool.loadCache(mContext, item.objURL, imageView);

//        SimpleDraweeView mSimpleDraweeView = helper.getView(R.id.sdv_image);
//        float ratio =  (float) item.width / (float) item.height;
//        mSimpleDraweeView.setAspectRatio(ratio);
//        FrescoTool.load(Uri.parse(item.objURL),mSimpleDraweeView);
    }
}
