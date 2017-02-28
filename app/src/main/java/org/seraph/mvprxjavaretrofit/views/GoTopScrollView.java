package org.seraph.mvprxjavaretrofit.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ScrollView;

import org.seraph.mvprxjavaretrofit.R;


/**
 * 滑动一键向上
 * date：2016/8/5 15:34
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class GoTopScrollView extends ScrollView implements View.OnClickListener {

    private View goTopBtn;

    /**
     * 多少距离后显示
     */
    private final int screenHeight = 500;

    private boolean isShow = false;


    public GoTopScrollView(Context context) {
        this(context, null);
    }

    public GoTopScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public void setScrollListener(View goTopBtn) {
        this.goTopBtn = goTopBtn;
        this.goTopBtn.setOnClickListener(this);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        /**
         * 滑动距离超过500px,出现向上按钮,可以做为自定义属性
         */
        if (goTopBtn == null) {
            return;
        }
        //如果大于指定高度，并且没显示则显示
        if (t >= screenHeight && !isShow) {
            isShow = true;
            translateAnimation();
        } else if (t < screenHeight && isShow) {
            isShow = false;
            translateAnimation();
        }
    }


    @Override
    public void onClick(View v) {
        this.post(() -> smoothScrollTo(0, 0));
    }

    /**
     * 动画效果
     */
    private void translateAnimation() {
        Animation animation;
        if (isShow) {
            animation = AnimationUtils.loadAnimation(this.getContext(), R.anim.go_top_gone_to_show);
        } else {
            animation = AnimationUtils.loadAnimation(this.getContext(), R.anim.go_top_show_to_gone);
        }
        animation.setAnimationListener(animationListener);
        goTopBtn.startAnimation(animation);
    }


    private Animation.AnimationListener animationListener = new Animation.AnimationListener() {


        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            if (isShow) {
                goTopBtn.setVisibility(View.VISIBLE);
            } else {
                goTopBtn.setVisibility(View.GONE);
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };

}
