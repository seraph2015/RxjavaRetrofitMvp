package org.seraph.mvprxjavaretrofit.ui.module.common.photopreview;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.StringUtils;

import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.data.network.ImageLoad.glide.GlideApp;
import org.seraph.mvprxjavaretrofit.ui.views.zoom.ImageViewTouch;
import org.seraph.mvprxjavaretrofit.ui.views.zoom.ImageViewTouchBase;

import java.io.File;
import java.util.List;

import javax.inject.Inject;


/**
 * 图片预览适配器
 */
class PhotoPreviewAdapter extends PagerAdapter {


    interface OnImageClickListener {
        void onImageClick(int position);
    }

    private Activity mContext;

    private LayoutInflater inflater;

    private List<PhotoPreviewBean> mListData;


    private OnImageClickListener onImageClickListener;

    @Inject
    PhotoPreviewAdapter(Activity activity) {
        this.mContext = activity;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mListData == null ? 0 : mListData.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    @NonNull
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View view = inflater.inflate(R.layout.common_activity_photo_preview_item, container, false);

        ImageViewTouch imageView = view.findViewById(R.id.ivt_image);
        //imageView.setTag(ImageViewTouchViewPager.VIEW_PAGER_OBJECT_TAG + position);
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
        PhotoPreviewBean previewBean = mListData.get(position);
        if (StringUtils.equals(previewBean.fromType, PhotoPreviewActivity.IMAGE_TYPE_LOCAL)) {
            GlideApp.with(mContext).load(new File(mListData.get(position).objURL)).skipMemoryCache(true).into(imageView);
            // PicassoTool.loadNoCache(mContext, new File(mListData.get(position).objURL), imageView);
        } else {
            GlideApp.with(mContext).load(mListData.get(position).objURL).skipMemoryCache(true).into(imageView);
            //  PicassoTool.loadNoCache(mContext, mListData.get(position).objURL, imageView);
        }
        container.addView(view, ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.MATCH_PARENT);
        return view;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
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
