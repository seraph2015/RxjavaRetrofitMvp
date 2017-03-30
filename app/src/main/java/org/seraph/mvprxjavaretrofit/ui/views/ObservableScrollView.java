package org.seraph.mvprxjavaretrofit.ui.views;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ScrollView;

import org.seraph.mvprxjavaretrofit.utli.Tools;

/**
 * 增加了滑动监听接口
 * date：2017/2/20 11:58
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class ObservableScrollView extends ScrollView {

    private int pxH = 0;

    /**
     * 当前进度值
     */
    private float percentScroll = 0;

    public interface ScrollViewListener {
        /**
         * @param percentScroll 默认高度到顶部的距离滚动的百分比 0-1
         */
        void onScrollChanged(float percentScroll);
    }

    private ScrollViewListener scrollViewListener = null;

    public ObservableScrollView(Context context) {
        this(context, null);
    }

    public ObservableScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ObservableScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //默认最高高度为toolbar高度的1.5倍
        int tempH = 87;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            tempH = 120;
        }
        pxH = Tools.dip2px(context, tempH);
    }


    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (scrollViewListener != null && pxH > 0) {
            float tempPercentScroll = ((float) t / (float) pxH) > 1 ? 1 : ((float) t / (float) pxH);
            //tempPercentScroll = percentScroll > 1 ? 1 : percentScroll;
            //如果新的进度和之前的进度不一致，则更新进度(控制是否调用接口，防止性能损失)
            if (percentScroll != tempPercentScroll) {
                percentScroll = tempPercentScroll;
                scrollViewListener.onScrollChanged(percentScroll);
            }
        }
    }
}
