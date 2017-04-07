package org.seraph.mvprxjavaretrofit.ui.views;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * 正方形的ImageView
 */
public class CustomSquareImageView extends AppCompatImageView {

	public CustomSquareImageView(Context context) {
		super(context);
	}

	public CustomSquareImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CustomSquareImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = MeasureSpec.getSize(widthMeasureSpec);
		// 强制设置为正方形
		setMeasuredDimension(width, width);
	}

}
