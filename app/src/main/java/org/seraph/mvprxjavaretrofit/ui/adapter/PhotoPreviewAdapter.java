package org.seraph.mvprxjavaretrofit.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.mvp.model.PhotoPreviewBean;
import org.seraph.mvprxjavaretrofit.ui.views.zoom.ImageViewTouch;
import org.seraph.mvprxjavaretrofit.ui.views.zoom.ImageViewTouchBase;
import org.seraph.mvprxjavaretrofit.ui.views.zoom.ImageViewTouchViewPager;

import java.util.List;


/**
 * 图片预览适配器
 */
public class PhotoPreviewAdapter extends PagerAdapter {

    private Context mContext;
    private List<PhotoPreviewBean> listData;

    public interface OnImageClickListener {
        void onImageClick(int position);
    }

    private OnImageClickListener onImageClickListener;


    public PhotoPreviewAdapter(Context context, List<PhotoPreviewBean> list) {
        this.mContext = context;
        this.listData = list;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ImageViewTouch imageView = new ImageViewTouch(mContext, null);
        imageView.setTag(ImageViewTouchViewPager.VIEW_PAGER_OBJECT_TAG + position);
        imageView.setMaxScale(3.0f);
        imageView.setMinScale(1.0f);
        imageView.setDisplayType(ImageViewTouchBase.DisplayType.FIT_TO_SCREEN);
        if (onImageClickListener != null) {
            imageView.setSingleTapListener(new ImageViewTouch.OnImageViewTouchSingleTapListener() {
                @Override
                public void onSingleTapConfirmed() {
                    onImageClickListener.onImageClick(position);
                }
            });
        }
        Picasso.with(mContext)
                .load(listData.get(position).objURL)
                .placeholder(R.mipmap.icon_placeholder)
                .error(R.mipmap.icon_error)
                .config(Bitmap.Config.RGB_565) //对于不透明的图片可以使用RGB_565来优化内存。RGB_565呈现结果与ARGB_8888接近
                //Picasso默认会使用设备的15%的内存作为内存图片缓存，且现有的api无法清空内存缓存。我们可以在查看大图时放弃使用内存缓存，图片从网络下载完成后会缓存到磁盘中，加载会从磁盘中加载，这样可以加速内存的回收。
                //NO_CACHE是指图片加载时放弃在内存缓存中查找，NO_STORE是指图片加载完不缓存在内存中。
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .into(imageView);
        container.addView(imageView, ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.MATCH_PARENT);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (container != null && object != null) {
            container.removeView((View) object);
        }
    }


    public void setOnImageClickListener(OnImageClickListener onImageClickListener) {
        this.onImageClickListener = onImageClickListener;
    }
}
