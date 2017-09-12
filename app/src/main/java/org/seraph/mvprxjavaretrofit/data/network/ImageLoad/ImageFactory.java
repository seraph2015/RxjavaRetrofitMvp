package org.seraph.mvprxjavaretrofit.data.network.ImageLoad;

import android.content.Context;
import android.graphics.Bitmap;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.squareup.picasso.Picasso;

import org.seraph.mvprxjavaretrofit.AppConfig;
import org.seraph.mvprxjavaretrofit.data.network.ImageLoad.fresco.FrescoMemoryCacheSupplier;
import org.seraph.mvprxjavaretrofit.utlis.FileUtils;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * 图片加载配置工作
 * date：2017/2/22 12:50
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class ImageFactory {

    /**
     * picasso加载初始化配置
     */
    public static void initPicasso(Context context) {
        OkHttpClient client = new OkHttpClient
                .Builder()
                .cache(new Cache(FileUtils.getCacheDirectory(context, null), AppConfig.CACHE_MAX_SIZE))//配置缓存
                .build();
        Picasso picasso = new Picasso
                .Builder(context)
                .downloader(new OkHttpDownLoader(client))//使用okhttp进行图片的加载
                .build();
        Picasso.setSingletonInstance(picasso);
    }

    /**
     * Fresco加载初始化配置
     */
    public static void initFresco(Context context) {
        //磁盘缓存配置
        DiskCacheConfig diskCacheConfig = DiskCacheConfig.newBuilder(context)
                .setMaxCacheSize(AppConfig.CACHE_MAX_SIZE)
                .setBaseDirectoryPath(FileUtils.getCacheDirectory(context, null))
                .build();
        //清理缓存
//        NoOpMemoryTrimmableRegistry.getInstance().registerMemoryTrimmable(new MemoryTrimmable() {
//            @Override
//            public void trim(MemoryTrimType trimType) {
//                final double suggestedTrimRatio = trimType.getSuggestedTrimRatio();
//                LogUtils.d(String.format(Locale.getDefault(),"onCreate suggestedTrimRatio : %f", suggestedTrimRatio));
//                if (MemoryTrimType.OnCloseToDalvikHeapLimit.getSuggestedTrimRatio() == suggestedTrimRatio
//                        || MemoryTrimType.OnSystemLowMemoryWhileAppInBackground.getSuggestedTrimRatio() == suggestedTrimRatio
//                        || MemoryTrimType.OnSystemLowMemoryWhileAppInForeground.getSuggestedTrimRatio() == suggestedTrimRatio
//                        ) {
//                    ImagePipelineFactory.getInstance().getImagePipeline().clearMemoryCaches();
//                }
//            }
//        });
        ImagePipelineConfig.Builder builder = ImagePipelineConfig.newBuilder(context)
                .setBitmapsConfig(Bitmap.Config.RGB_565)//在没有透明图层的时候，显示效果与rgb_8888一致，优化内存
                .setDownsampleEnabled(true)//Resize对jpg格式有效，对于png等其他格式的图片也支持这个属性，需要设置Downsample
                .setBitmapMemoryCacheParamsSupplier(new FrescoMemoryCacheSupplier(context))
                // .setMemoryTrimmableRegistry(NoOpMemoryTrimmableRegistry.getInstance())
                .setMainDiskCacheConfig(diskCacheConfig);
        Fresco.initialize(context, builder.build());
    }

}
