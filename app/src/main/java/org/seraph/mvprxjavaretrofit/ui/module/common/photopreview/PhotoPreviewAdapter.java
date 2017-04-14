package org.seraph.mvprxjavaretrofit.ui.module.common.photopreview;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import org.seraph.mvprxjavaretrofit.data.network.picasso.PicassoTool;
import org.seraph.mvprxjavaretrofit.ui.views.zoom.ImageViewTouch;
import org.seraph.mvprxjavaretrofit.ui.views.zoom.ImageViewTouchBase;
import org.seraph.mvprxjavaretrofit.ui.views.zoom.ImageViewTouchViewPager;

import java.util.List;

import javax.inject.Inject;


/**
 * 图片预览适配器
 */
class PhotoPreviewAdapter extends PagerAdapter {


    interface OnImageClickListener {
        void onImageClick(int position);
    }

    private Context mContext;

    private List<PhotoPreviewBean> mListData;


    private OnImageClickListener onImageClickListener;

    @Inject
    PhotoPreviewAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mListData == null ? 0 : mListData.size();
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
        PicassoTool.loadNoCache(mContext,mListData.get(position).objURL, imageView);
        container.addView(imageView, ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.MATCH_PARENT);
        return imageView;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (container != null && object != null) {
            container.removeView((View) object);
        }
    }


    void setOnImageClickListener(OnImageClickListener onImageClickListener) {
        this.onImageClickListener = onImageClickListener;
    }

    /**
     * 设置数据
     */
     void setListData(List<PhotoPreviewBean> listData) {
        if (mListData == null) {
            mListData = listData;
        }
        notifyDataSetChanged();

    }
}
