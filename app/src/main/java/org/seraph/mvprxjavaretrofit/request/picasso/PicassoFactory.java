package org.seraph.mvprxjavaretrofit.request.picasso;

import android.content.Context;

import com.squareup.picasso.Picasso;

import org.seraph.mvprxjavaretrofit.preference.AppConstant;
import org.seraph.mvprxjavaretrofit.utlis.FileTools;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * picasso图片加载
 * date：2017/2/22 12:50
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class PicassoFactory {

    public static void initPicassoToOkHttp(Context context) {
        OkHttpClient client = new OkHttpClient
                .Builder()
                .cache(new Cache(FileTools.getCacheDirectory(context, null), AppConstant.CACHE_IMAGE_MAX_SIZE))
                .build();
        Picasso picasso = new Picasso.Builder(context).downloader(new PicassoToOkHttpDownLoader(client)).build();
        Picasso.setSingletonInstance(picasso);
    }

}
