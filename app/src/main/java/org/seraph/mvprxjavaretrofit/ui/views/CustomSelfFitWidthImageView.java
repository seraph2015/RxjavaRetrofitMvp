package org.seraph.mvprxjavaretrofit.ui.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 根据图片宽度自适应高度的ImageView
 * 
 * @since 2015-03-19
 * 
 */
public class CustomSelfFitWidthImageView extends ImageView {

	public CustomSelfFitWidthImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = MeasureSpec.getSize(widthMeasureSpec);
		Drawable drawable = getDrawable();
		if (drawable != null) {
			int diw = drawable.getIntrinsicWidth();
			int dih = drawable.getIntrinsicHeight();
			if (diw > 0) {
				// 根据图片宽度自适应高度
				int height = width * dih / diw;
				setMeasuredDimension(width, height);
			} else {
				super.onMeasure(widthMeasureSpec, heightMeasureSpec);
			}
		} else {
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		}

	}



}
