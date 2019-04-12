package org.seraph.mvprxjavaretrofit.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.blankj.utilcode.util.BarUtils;

import androidx.annotation.Nullable;

/**
 * 状态栏等高的view
 * date：2019/3/28 16:21
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class StatusBarView extends View {


    private int statusBarHeight;

    public StatusBarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public StatusBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }


    public StatusBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (statusBarHeight == 0) {
            statusBarHeight = BarUtils.getStatusBarHeight();
        }
        setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec), statusBarHeight);
    }
}
