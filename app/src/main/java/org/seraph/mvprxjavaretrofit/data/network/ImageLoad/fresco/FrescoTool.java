package org.seraph.mvprxjavaretrofit.data.network.ImageLoad.fresco;

import android.content.Context;
import android.net.Uri;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import org.seraph.mvprxjavaretrofit.R;

/**
 * picasso图片工具，一些常用加载配置
 * 需要先在{@link org.seraph.mvprxjavaretrofit.AppApplication}中，
 * 使用{@link org.seraph.mvprxjavaretrofit.data.network.ImageLoad.ImageFactory#initFresco(Context)}方法进行初始化
 * date：2017/9/11 17:26
 * author：Seraph
 * mail：417753393@qq.com
 **/
public class FrescoTool {

    /**
     * 默认加载
     */
    public static void load(SimpleDraweeView simpleDraweeView, Uri uri) {
        load(simpleDraweeView, uri, -1);
    }

    /**
     * 自定义加载中图片
     * @param placeholderResourceId 加载中图片
     */
    public static void load(SimpleDraweeView simpleDraweeView, Uri uri, int placeholderResourceId) {
        //图片请求，功能支持
        ImageRequestBuilder imageRequestBuilder = ImageRequestBuilder.newBuilderWithSource(uri);
        int width = simpleDraweeView.getMeasuredWidth();
        int height = simpleDraweeView.getMeasuredHeight();
        if (width != 0 && height != 0) {
            //图片尺寸使用界面显示控件尺寸
            imageRequestBuilder.setResizeOptions(new ResizeOptions(width, height));
        }
        //控制器
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(imageRequestBuilder.build())
                .setTapToRetryEnabled(true)//设置图片加载失败，点击重试
                .setOldController(simpleDraweeView.getController())
                .build();
        //设置改变一些占位符
        GenericDraweeHierarchy hierarchy = simpleDraweeView.getHierarchy();
        //加载中图片
        hierarchy.setPlaceholderImage(placeholderResourceId == -1 ? R.mipmap.icon_placeholder : placeholderResourceId, ScalingUtils.ScaleType.FIT_CENTER);
        //加载失败图片
        hierarchy.setFailureImage(placeholderResourceId == -1 ? R.mipmap.icon_error : placeholderResourceId, ScalingUtils.ScaleType.FIT_CENTER);
        //加载失败，设置点击重试后，显示的图片
        hierarchy.setRetryImage(R.mipmap.icon_error, ScalingUtils.ScaleType.FIT_CENTER);
        //加载成功显示图片的ScaleType
        hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP);
        //设置加载的进度条
        hierarchy.setProgressBarImage(new FrescoLoadingDrawable());
        simpleDraweeView.setController(controller);
    }


}
