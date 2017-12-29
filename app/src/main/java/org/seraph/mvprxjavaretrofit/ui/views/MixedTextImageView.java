package org.seraph.mvprxjavaretrofit.ui.views;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.StringUtils;

import org.seraph.mvprxjavaretrofit.data.network.ImageLoad.glide.GlideApp;
import org.seraph.mvprxjavaretrofit.utlis.HtmlUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 图文混排视图
 * date：2017/5/19 15:31
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class MixedTextImageView extends LinearLayout {


    public interface ImageItemClickListener {
        void onItemImageClick(List<String> listImg, int clickItem);
    }


    public ImageItemClickListener imageItemClickListener;


    private int textSize = 18;

    /**
     * 当前控件包含的图片数据源
     */
    private List<String> listImg = new ArrayList<>();

    public MixedTextImageView(Context context) {
        super(context);
        setOrientation(VERTICAL);
    }

    public MixedTextImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
    }

    public MixedTextImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setOrientation(VERTICAL);
    }


    /**
     * 设置图文混排控件要显示的内容
     */
    public void setHtmlContent(String htmlStr) {
        listImg.clear();
        removeAllViews();
        List<HtmlUtils.HtmlBean> list = HtmlUtils.htmlToListData(htmlStr);

        int imgIndex = 0;
        for (HtmlUtils.HtmlBean htmlBean : list) {
            if (htmlBean == null || StringUtils.isEmpty(htmlBean.str)) {
                continue;
            }
            switch (htmlBean.type) {
                case HtmlUtils.TEXT:
                    //去掉文字首位的/n
                    //appendTextView(htmlBean.str);
                    appendTextView(trimFirstAndLastStr(htmlBean.str, "\n"));
                    break;
                case HtmlUtils.IMG:
                    appendImageView(htmlBean.str, imgIndex);
                    imgIndex++;
                    break;
            }
        }
    }

    /**
     * 动态添加文本内容
     */
    private void appendTextView(String content) {
        if (!TextUtils.isEmpty(content)) {
            TextView textView = new TextView(getContext());
           // textView.setTextIsSelectable(true);
            textView.setText(content);
            textView.setTextColor(Color.parseColor("#333333"));
            textView.setTextSize(textSize);
            textView.setGravity(Gravity.START);
            textView.setLineSpacing(0, 1.4f);
            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.bottomMargin = SizeUtils.dp2px(12);
            params.leftMargin = SizeUtils.dp2px(10);
            params.rightMargin = SizeUtils.dp2px(10);
            textView.setLayoutParams(params);
            addView(textView);
        }
    }

    /**
     * 动态添加图片
     */
    private void appendImageView(String imageUrl, int imgIndex) {
        ImageView imageView = new ImageView(getContext());
        final int screenWidth = getDeviceScreenWidth();
        final int screenHeight = (int) (screenWidth * 2.0 / 3);
        LayoutParams params = new LayoutParams(screenWidth, screenHeight);
        params.bottomMargin = SizeUtils.dp2px(12);
        imageView.setLayoutParams(params);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        GlideApp.with(getContext()).load(imageUrl).override(screenWidth,screenHeight).into(imageView);
        //PicassoTool.loadCache(getContext(), imageUrl, imageView, screenWidth, screenHeight);
        listImg.add(imageUrl);
        imageView.setTag(imgIndex);
        imageView.setOnClickListener(onClickListener);
        addView(imageView);
    }


    /**
     * 获取屏幕宽度
     */
    public int getDeviceScreenWidth() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int w = dm.widthPixels;
        int h = dm.heightPixels;
        return w > h ? h : w;
    }

    /**
     * 去除字符串首尾出现的某个字符.
     *
     * @param source  源字符串.
     * @param element 需要去除的字符.
     * @return String.
     */
    public static String trimFirstAndLastStr(String source, String element) {
        source = source.trim();
        //先去掉首部
        boolean beginIndexFlag = true;
        while (beginIndexFlag) {
            beginIndexFlag = source.indexOf(element) == 0;
            if (beginIndexFlag) {
                source = source.replaceFirst(element, "");
            }
        }
        //去掉尾部
        boolean endIndexFlag = true;
        while (endIndexFlag) {
            int index = source.lastIndexOf(element);
            endIndexFlag = (index != -1 && index + element.length() == source.length());
            if (endIndexFlag) {
                source = source.substring(0, source.length() - element.length());
            }
        }

        return source;
    }


    private OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (imageItemClickListener != null) {
                int index = (int) v.getTag();
                imageItemClickListener.onItemImageClick(listImg, index);
            }
        }
    };


    public void setImageItemClickListener(ImageItemClickListener imageItemClickListener) {
        this.imageItemClickListener = imageItemClickListener;
    }
}
