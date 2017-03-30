package org.seraph.mvprxjavaretrofit.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.ListView;

import org.seraph.mvprxjavaretrofit.R;


/**
 * 滑动一键向上
 * date：2016/8/5 15:34
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class GoTopListView extends ListView implements View.OnClickListener {

    private View goTopBtn;

    /**
     * 多少个Item
     */
    private final int screenItem = 5;

    private boolean isShow = false;

    private Animation showAnimation;

    private Animation goneAnimation;

    public GoTopListView(Context context) {
        this(context, null);
    }

    public GoTopListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public void setScrollListener(View goTopBtn) {
        this.goTopBtn = goTopBtn;
        this.goTopBtn.setOnClickListener(this);

        showAnimation = AnimationUtils.loadAnimation(this.getContext(), R.anim.go_top_gone_to_show);
        goneAnimation = AnimationUtils.loadAnimation(this.getContext(), R.anim.go_top_show_to_gone);
        showAnimation.setAnimationListener(animationListener);
        goneAnimation.setAnimationListener(animationListener);

        setOnScrollListener(onScrollListener);
        if (getFirstVisiblePosition() <= screenItem) {
            goTopBtn.setVisibility(View.GONE);
            isShow = false;
        } else {
            goTopBtn.setVisibility(View.VISIBLE);
            isShow = true;
        }

    }


    /**
     * 向上滚动smoothScrollToPosition
     */
    public void onClick(View v) {
        this.post(new Runnable() {
            @Override
            public void run() {
                setSelection(0);
            }
        });
    }

    /**
     * 动画效果
     */
    private void translateAnimation(boolean isShow) {
        this.isShow = isShow;
        //指定显示
        if (isShow) {
            goTopBtn.startAnimation(showAnimation);
        } else {
            goTopBtn.startAnimation(goneAnimation);
        }

    }

    private Animation.AnimationListener animationListener = new Animation.AnimationListener() {


        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            if (animation == showAnimation) {
                goTopBtn.setVisibility(View.VISIBLE);
            } else if (animation == goneAnimation) {
                goTopBtn.setVisibility(View.GONE);
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };

    private OnScrollListener onScrollListener = new OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            /**
             * 滑动距离超过5个元素后,出现向上按钮,可以做为自定义属性
             */
            //如果大于指定高度，并且没显示则显示
            if (firstVisibleItem >= screenItem && !isShow) {
                translateAnimation(true);
            } else if (firstVisibleItem < screenItem && isShow) {
                translateAnimation(false);
            }
        }
    };
}
