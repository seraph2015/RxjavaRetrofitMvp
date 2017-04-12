package org.seraph.mvprxjavaretrofit.di.module;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.di.ActivityScope;
import org.seraph.mvprxjavaretrofit.ui.module.base.adapter.BaseRecyclerViewAdapter;
import org.seraph.mvprxjavaretrofit.ui.module.base.adapter.BaseRecyclerViewHolder;
import org.seraph.mvprxjavaretrofit.ui.module.main.ImageBaiduBean;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

/**
 * 布局测试类模型
 * date：2017/4/12 11:56
 * author：xiongj
 * mail：417753393@qq.com
 **/
@Module
public class DesignLayoutModule {

    private final Activity mActivity;

    public DesignLayoutModule(Activity activity) {
        this.mActivity = activity;
    }

    @Provides
    @ActivityScope
    Activity provideActivity() {
        return mActivity;
    }

    @Provides
    @ActivityScope
    Context provideContext() {
        return mActivity;
    }

    @Provides
    @ActivityScope
    BaseRecyclerViewAdapter<ImageBaiduBean.BaiduImage> provideBaseRecyclerViewAdapter() {
        return new BaseRecyclerViewAdapter<ImageBaiduBean.BaiduImage>(mActivity, R.layout.item_design, new ArrayList<ImageBaiduBean.BaiduImage>()) {
            @Override
            protected void convert(BaseRecyclerViewHolder holder, ImageBaiduBean.BaiduImage imageBaiduBean) {
                ImageView imageView = holder.getView(R.id.imageView);
                Picasso.with(mContext).load(imageBaiduBean.objURL)
                        .placeholder(R.mipmap.icon_placeholder)
                        .error(R.mipmap.icon_error)
                        .config(Bitmap.Config.RGB_565) //对于不透明的图片可以使用RGB_565来优化内存。RGB_565呈现结果与ARGB_8888接近
                        .into(imageView);
            }
        };
    }

}
