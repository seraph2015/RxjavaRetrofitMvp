package org.seraph.mvprxjavaretrofit.utlis;

import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.seraph.mvprxjavaretrofit.AppConfig;

/**
 * 设置字体工具
 * date：2017/7/31 15:04
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class FontUtils {


    /**
     * 设置默认的字体路径
     */
    public static void injectFont(View rootView) {
        if (Tools.isNull(AppConfig.FONTS_ASSETS_DIR)) {
            return;
        }
        injectFont(rootView, Typeface.createFromAsset(rootView.getContext().getAssets(), AppConfig.FONTS_ASSETS_DIR));
    }

    /**
     * 循环递归查找布局的TextView控件，设置字体
     *
     * @param rootView 需要设置字体的根布局
     * @param typeface 字体
     */
    public static void injectFont(View rootView, Typeface typeface) {
        if (rootView instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) rootView;
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                injectFont(viewGroup.getChildAt(i), typeface);
            }
        } else if (rootView instanceof TextView) {
            ((TextView) rootView).setTypeface(typeface);
        }


    }
}
