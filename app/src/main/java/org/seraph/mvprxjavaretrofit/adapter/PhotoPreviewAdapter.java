package org.seraph.mvprxjavaretrofit.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.mvp.model.PhotoPreviewBean;
import org.seraph.mvprxjavaretrofit.views.zoom.ImageViewTouch;
import org.seraph.mvprxjavaretrofit.views.zoom.ImageViewTouchBase;
import org.seraph.mvprxjavaretrofit.views.zoom.ImageViewTouchViewPager;

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
    public Object instantiateItem(ViewGroup container, int position) {
        ImageViewTouch imageView = new ImageViewTouch(mContext, null);
        imageView.setTag(ImageViewTouchViewPager.VIEW_PAGER_OBJECT_TAG + position);
        imageView.setMaxScale(3.0f);
        imageView.setMinScale(1.0f);
        imageView.setDisplayType(ImageViewTouchBase.DisplayType.FIT_TO_SCREEN);
        if (onImageClickListener != null) {
            imageView.setSingleTapListener(() ->
                    onImageClickListener.onImageClick(position)
            );
        }
        Picasso.with(mContext).load(listData.get(position).objURL).placeholder(R.mipmap.icon_placeholder).error(R.mipmap.icon_error).into(imageView);
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
