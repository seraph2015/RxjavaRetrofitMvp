package org.seraph.mvprxjavaretrofit.data.network.picasso;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import org.seraph.mvprxjavaretrofit.R;

import javax.inject.Inject;

/**
 * PicassoTool工具，常用的一些加载配置
 * date：2017/4/14 17:06
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class PicassoTool {

    private final Context mContext;

    @Inject
    public PicassoTool(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * 不使用内存加载图片，直接读取磁盘和网络（建议多图片列表使用此配置）
     */
    public void loadNoCache(String path, ImageView target) {
        loadNoCache(path, target, 0, 0);
    }

    /**
     * 不使用内存加载图片，直接读取磁盘和网络（建议多图片列表使用此配置）
     * Picasso默认会使用设备的15%的内存作为内存图片缓存，且现有的api无法清空内存缓存。
     * 我们可以在查看大图时放弃使用内存缓存，图片从网络下载完成后会缓存到磁盘中，加载会从磁盘中加载，这样可以加速内存的回收。
     * NO_CACHE是指图片加载时放弃在内存缓存中查找，NO_STORE是指图片加载完不缓存在内存中。
     * @param targetWidth   裁剪的宽
     * @param targetHeight  裁剪的高
     */
    public void loadNoCache(String path, ImageView target, int targetWidth, int targetHeight) {
        RequestCreator requestCreator = Picasso.with(mContext)
                .load(path)
                .placeholder(R.mipmap.icon_placeholder)
                .error(R.mipmap.icon_error)
                .config(Bitmap.Config.RGB_565) //对于不透明的图片可以使用RGB_565来优化内存。RGB_565呈现结果与ARGB_8888接近
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE);
        if (targetWidth != 0 && targetHeight != 0) {
            requestCreator.resize(targetWidth, targetHeight).centerCrop();
        }
        requestCreator.into(target);
    }

}
