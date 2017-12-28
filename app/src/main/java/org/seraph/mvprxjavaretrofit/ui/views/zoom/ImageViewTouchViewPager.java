package org.seraph.mvprxjavaretrofit.ui.views.zoom;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

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

//            if (state == SCROLL_STATE_SETTLING && previousPosition != getCurrentItem()) {
//                try {
//                    ImageViewTouch imageViewTouch = (ImageViewTouch) findViewWithTag(VIEW_PAGER_OBJECT_TAG + previousPosition);
//                    if (imageViewTouch != null) {
//                        imageViewTouch.zoomTo(imageViewTouch.getMinScale(), 100);
//                    }
//                    previousPosition = getCurrentItem();
//                } catch (ClassCastException ex) {
//                    Log.e(TAG, "This view pager should have only ImageViewTouch as a children.", ex);
//                }
//            }
            //获取当前view，判断子元素是否有ImageViewTouch，有这进行缩放
//            if (state == SCROLL_STATE_SETTLING) {
//                View view = getChildAt(getCurrentItem() % getChildCount());
//                ImageViewTouch imageViewTouch = null;
//                if (view instanceof ImageViewTouch) {
//                    imageViewTouch = ((ImageViewTouch) view);
//
//                } else if (view instanceof ViewGroup) {
//                    imageViewTouch = getTouchView((ViewGroup) view);
//                }
//                if (imageViewTouch != null) {
//                    imageViewTouch.zoomTo(imageViewTouch.getMinScale(), 100);
//                }
//            }
        }
    };


    //获取子元素中第一个ImageViewTouch
    private ImageViewTouch getTouchView(ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View view = viewGroup.getChildAt(i);
            if (view instanceof ImageViewTouch) {
                return (ImageViewTouch) view;
            } else if (view instanceof ViewGroup) {
                getTouchView((ViewGroup) view);
            }
        }
        return null;
    }

}