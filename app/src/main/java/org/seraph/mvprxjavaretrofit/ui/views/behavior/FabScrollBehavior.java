package org.seraph.mvprxjavaretrofit.ui.views.behavior;

import android.animation.Animator;
import android.content.Context;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.core.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * FloatingActionButton 跟随滑动视图实现上滑隐藏，下滑显示按钮。（在设置的到达一定的垂直（Y距离）高度之后）
 * date：2017/4/13 10:27
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class FabScrollBehavior extends FloatingActionButton.Behavior {

    private final String TAG = "FabScrollBehavior#";

    /**
     * 预改变的距离
     */
    private int mDySinceDirectionChange = 0;


    public FabScrollBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 想要初始化一个滑动的时候调用(处理垂直方向上的滚动事件)
     */
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View directTargetChild, View target, int nestedScrollAxes) {
        //在垂直方向滑动的时候做出反应
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL || super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
    }


//    /**
//     * 滑动之前(AppBarLayout使用的，可能会消费掉部分滚动事件)
//     */
//    @Override
//    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View target, int dx, int dy, int[] consumed) {
//        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
//        Log.i(TAG, "onNestedPreScroll-> dy：" + dy);
//    }


    /**
     * 在实际关注的滚动区
     */
    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
       // Log.i(TAG, "onNestedScroll:" + "dyConsumed:" + dyConsumed + "-----dyUnconsumed" + dyUnconsumed);
        //dyUnconsumed 代表没有移动的距离
        //改变方向之后，重置预改变的方向
        if (dyConsumed > 0 && mDySinceDirectionChange < 0 || dyConsumed < 0 && mDySinceDirectionChange > 0) {
            cancelAnimate(child);
            mDySinceDirectionChange = 0;
        }
        mDySinceDirectionChange += dyConsumed;
        //如果预改变的方向向下,而且改变距离大于当前控件的高度，则隐藏（此高度可以自己定义）
        if (mDySinceDirectionChange > 200 && child.isShown()) {
            hide(child);
        } else if (mDySinceDirectionChange < 0 && !child.isShown()) {
            show(child);
        }

    }

    private void cancelAnimate(View view) {
        view.animate().cancel();
    }


    private void hide(final View view) {
        view.animate()
                .translationY(180f)
                .setDuration(500).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                show(view);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        })
                .start();
    }

    private void show(final View view) {
        view.animate()
                .translationY(0f)
                .setDuration(500).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                hide(view);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        })
                .start();
    }


}
