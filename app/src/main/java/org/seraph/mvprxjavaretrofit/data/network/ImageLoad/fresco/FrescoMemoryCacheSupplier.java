package org.seraph.mvprxjavaretrofit.data.network.ImageLoad.fresco;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;

import com.facebook.common.internal.Supplier;
import com.facebook.common.util.ByteConstants;
import com.facebook.imagepipeline.cache.MemoryCacheParams;

/**
 * fresco内存优化
 * date：2017/9/12 11:08
 * author：Seraph
 * mail：417753393@qq.com
 **/
public class FrescoMemoryCacheSupplier implements Supplier<MemoryCacheParams> {

    //可以存储在缓存中的最大项目数
    private static final int MAX_CACHE_ENTRIES = 56;
    //驱逐队列是存储项目准备就绪的内存区域（被驱逐，但尚未被删除。 这是最大的，该队列的大小（以字节为单位））
    private static final int MAX_CACHE_ASHM_ENTRIES = 128;
    //驱逐队列中的最大条目数
    private static final int MAX_CACHE_EVICTION_SIZE = 5;
    //单个缓存条目的最大大小
    private static final int MAX_CACHE_EVICTION_ENTRIES = 5;

    private final ActivityManager mActivityManager;

    public FrescoMemoryCacheSupplier(Context context) {
        mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
    }

    @Override
    public MemoryCacheParams get() {
        //控制缓存在构造函数中的行为
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return new MemoryCacheParams(getMaxCacheSize(), MAX_CACHE_ENTRIES, MAX_CACHE_EVICTION_SIZE, MAX_CACHE_EVICTION_ENTRIES, 1);
        } else {
            return new MemoryCacheParams(
                    getMaxCacheSize(),
                    MAX_CACHE_ASHM_ENTRIES,
                    Integer.MAX_VALUE,
                    Integer.MAX_VALUE,
                    Integer.MAX_VALUE);
        }
    }

    /**
     * 获取最大的缓存（以字节为单位）
     */
    private int getMaxCacheSize() {
        final int maxMemory = Math.min(mActivityManager.getMemoryClass() * ByteConstants.MB, Integer.MAX_VALUE);
        if (maxMemory < 32 * ByteConstants.MB) {
            return 4 * ByteConstants.MB;
        } else if (maxMemory < 64 * ByteConstants.MB) {
            return 6 * ByteConstants.MB;
        } else {
            return maxMemory / 5;
        }
    }
}
