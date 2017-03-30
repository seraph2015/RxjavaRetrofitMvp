package org.seraph.mvprxjavaretrofit.ui.views;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * 解决输入法弹框问题 属性失效布局
 * date：2015/12/1 13:24
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class CustomInsetsFrameLayout extends FrameLayout {
    private int[] mInsets = new int[4];

    public CustomInsetsFrameLayout(Context context) {
        super(context);
    }

    public CustomInsetsFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomInsetsFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public final int[] getInsets() {
        return mInsets;
    }

    @Override
    protected final boolean fitSystemWindows(Rect insets) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 故意不要修改底部插图。 因为某些原因，如果底部插入被修改，窗口调整大小停止工作。
            mInsets[0] = insets.left;
            mInsets[1] = insets.top;
            mInsets[2] = insets.right;

            insets.left = 0;
            insets.top = 0;
            insets.right = 0;
        }

        return super.fitSystemWindows(insets);
    }
}
