package org.seraph.mvprxjavaretrofit.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import org.seraph.mvprxjavaretrofit.R;


/**
 * 横向动态流布局
 */
public class DynamicLayout extends ViewGroup {

    private int columnSpacing;
    private int rowSpacing;

    public DynamicLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.DynamicLayout);
        columnSpacing = array.getDimensionPixelSize(R.styleable.DynamicLayout_columnSpacing, 0);
        rowSpacing = array.getDimensionPixelSize(R.styleable.DynamicLayout_rowSpacing, 0);
        array.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = 0;
        int tempWidth = 0;
        int tempHeight = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == View.GONE) {
                child.measure(
                        MeasureSpec.makeMeasureSpec(0, MeasureSpec.EXACTLY),
                        MeasureSpec.makeMeasureSpec(0, MeasureSpec.EXACTLY));
            } else {
                child.measure(MeasureSpec.makeMeasureSpec(
                        MeasureSpec.getSize(widthMeasureSpec),
                        MeasureSpec.AT_MOST), MeasureSpec.makeMeasureSpec(
                        MeasureSpec.getSize(heightMeasureSpec),
                        MeasureSpec.UNSPECIFIED));
            }

            int srcWidth = child.getMeasuredWidth() >= width ? width : child
                    .getMeasuredWidth();
            tempWidth += srcWidth + (i == 0 ? 0 : columnSpacing);
            if (tempWidth > width) {
                height += tempHeight + rowSpacing;
                tempHeight = child.getMeasuredHeight();
                tempWidth = srcWidth;
            } else if (child.getMeasuredHeight() > tempHeight)
                tempHeight = child.getMeasuredHeight();
        }
        height += tempHeight + rowSpacing;
        if (getLayoutParams() != null && getLayoutParams().height == -2)
            setMeasuredDimension(width, height);
        else
            setMeasuredDimension(width, MeasureSpec.getSize(heightMeasureSpec));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int tempHeight = 0;
        int tempT = 0;
        int tempL = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            int width = child.getMeasuredWidth() >= getWidth() ? getWidth()
                    : child.getMeasuredWidth();
            tempL += i == 0 ? 0 : columnSpacing;
            if (tempL + width <= getWidth()) {
                child.layout(tempL, tempT, tempL + width,
                        tempT + child.getMeasuredHeight());
                tempL += width;
                if (tempHeight < child.getMeasuredHeight())
                    tempHeight = child.getMeasuredHeight();
            } else {
                tempL = 0;
                tempT += tempHeight + rowSpacing;
                tempHeight = child.getMeasuredHeight();
                child.layout(tempL, tempT, tempL + width,
                        tempT + child.getMeasuredHeight());
                tempL = width;
            }
        }
    }
}
