package org.seraph.mvprxjavaretrofit.ui.module.main.adapter;

import android.app.Activity;
import android.view.View;

import com.blankj.utilcode.util.ScreenUtils;

import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.data.network.picasso.PicassoTool;
import org.seraph.mvprxjavaretrofit.ui.module.base.adapter.BaseListAdapter;
import org.seraph.mvprxjavaretrofit.ui.module.main.model.ImageBaiduBean;
import org.seraph.mvprxjavaretrofit.ui.views.CustomSelfProportionImageView;
import org.seraph.mvprxjavaretrofit.utlis.Tools;
import org.seraph.mvprxjavaretrofit.ui.module.base.adapter.ViewHolder;

import javax.inject.Inject;

/**
 * 图片列表
 * date：2017/2/22 13:43
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class ImageListBaiduAdapter extends BaseListAdapter<ImageBaiduBean.BaiduImage> {

    private int screenWidth;

    @Inject
    public ImageListBaiduAdapter(Activity context) {
        super(context, R.layout.test_item_image);
        screenWidth = ScreenUtils.getScreenWidth();
    }

    @Override
    public void bindView(int position, View view, ImageBaiduBean.BaiduImage baiduImage) {
        CustomSelfProportionImageView imageView = ViewHolder.get(view,R.id.image);
        imageView.setSize(baiduImage.width, baiduImage.height);
        //按照控件的大小来缩放图片的尺寸
        int width = baiduImage.width;
        int height = baiduImage.height;
        //直接使用屏幕宽
        //  int imageViewWidth = imageView.getMeasuredWidth();
        if (width != 0) {
            height = Tools.getNewHeight(width, height, screenWidth);
            width = screenWidth;
        }
        PicassoTool.loadCache(mContext, baiduImage.objURL, imageView, width, height);
    }


}
