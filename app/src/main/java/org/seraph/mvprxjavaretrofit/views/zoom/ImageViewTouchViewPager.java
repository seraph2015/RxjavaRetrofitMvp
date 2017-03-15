package org.seraph.mvprxjavaretrofit.views.zoom;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class ImageViewTouchViewPager extends ViewPager {

    private static final String TAG = "ImageViewTouchViewPager";

    public static final String VIEW_PAGER_OBJECT_TAG = "imageView#";

    private int previousPosition;

    private OnPageSelectedListener onPageSelectedListener;

    public ImageViewTouchViewPager(Context context) {
        super(context);
        init();
    }

    public ImageViewTouchViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void setOnPageSelectedListener(OnPageSelectedListener listener) {
        onPageSelectedListener = listener;
    }

    @Override
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        if (v instanceof ImageViewTouch) {
            return ((ImageViewTouch) v).canScroll(dx);
        } else {
            return super.canScroll(v, checkV, dx, x, y);
        }
    }

    // 引入原 OnPageChangeListener 的 onPageSelected 方法
    public interface OnPageSelectedListener {
        void onPageSelected(int position);
    }

    private void init() {
        previousPosition = getCurrentItem();
        addOnPageChangeListener(simpleOnPageChangeListener);
    }

    private SimpleOnPageChangeListener simpleOnPageChangeListener = new SimpleOnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            if (onPageSelectedListener != null) {
                onPageSelectedListener.onPageSelected(position);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == SCROLL_STATE_SETTLING && previousPosition != getCurrentItem()) {
                try {
                    ImageViewTouch imageViewTouch = (ImageViewTouch) findViewWithTag(VIEW_PAGER_OBJECT_TAG + previousPosition);
                    if (imageViewTouch != null) {
                        imageViewTouch.zoomTo(imageViewTouch.getMinScale(), 100);
                    }
                    previousPosition = getCurrentItem();
                } catch (ClassCastException ex) {
                    Log.e(TAG, "This view pager should have only ImageViewTouch as a children.", ex);
                }
            }
        }
    };
}