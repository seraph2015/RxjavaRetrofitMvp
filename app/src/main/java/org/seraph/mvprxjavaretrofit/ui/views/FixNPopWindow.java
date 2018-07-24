package org.seraph.mvprxjavaretrofit.ui.views;

import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import javax.inject.Inject;

/**
 * 通用popweindow在6.0以上显示位置问题
 * date：2017/6/28 14:41
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class FixNPopWindow extends PopupWindow {

    @Inject
    public FixNPopWindow() {
        this(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    public FixNPopWindow(int w, int h) {
        super(w, h);
        setBackgroundDrawable(new ColorDrawable(0xFFFFFFFF));
        setOutsideTouchable(true);
        setTouchable(true);
        setFocusable(true);
    }


    @Override
    public void showAsDropDown(View anchor) {
        if(Build.VERSION.SDK_INT == 24) {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
            setHeight(h);
        }
        super.showAsDropDown(anchor);
    }
}
