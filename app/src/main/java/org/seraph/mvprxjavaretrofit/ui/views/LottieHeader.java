package org.seraph.mvprxjavaretrofit.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.StringUtils;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.internal.InternalAbstract;

import androidx.annotation.NonNull;

/**
 * 刷新头部加载动画
 * date：2018/11/13 10:25
 * author：xiongj
 **/
public class LottieHeader extends InternalAbstract implements RefreshHeader {

    private LottieAnimationView mLottieAnimationView;

    //默认的动画
    private String animationJson = "dino_dance.json";

    //头高度dp
    private int LayoutDPH = 60;

    public LottieHeader(Context context) {
        this(context, null);
    }

    protected LottieHeader(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    protected LottieHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LinearLayout rootLayout = new LinearLayout(context);
        rootLayout.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, SizeUtils.dp2px(LayoutDPH)));
        rootLayout.setGravity(Gravity.BOTTOM);
        rootLayout.setOrientation(LinearLayout.VERTICAL);
        if (mLottieAnimationView == null) {
            mLottieAnimationView = new LottieAnimationView(context);
            mLottieAnimationView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0));
            mLottieAnimationView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            mLottieAnimationView.setRepeatCount(-1);
            mLottieAnimationView.setScale(0.01f);
            rootLayout.addView(mLottieAnimationView);
        }
        setAnimationViewJson(animationJson);
        this.addView(rootLayout);
    }

    //设置动画
    public LottieHeader setAnimationViewJson(String animationJson) {
        if (mLottieAnimationView != null && !StringUtils.isEmpty(animationJson)) {
            mLottieAnimationView.setAnimation(animationJson);
        }
        return this;
    }


    @Override
    public void onMoving(boolean isDragging, float percent, int offset, int height, int maxDragHeight) {
        super.onMoving(isDragging, percent, offset, height, maxDragHeight);
        //如果是手拖动则设置动画进度
        if (mLottieAnimationView != null) {
            //设置动画大小尺寸（如果偏移大于高度则使用最大高度），为了使动画中心点始终位于显示区域的中心
            int hpx = offset > height ? height : offset;
            mLottieAnimationView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, hpx));
            if (isDragging) {
                float progress = (float) offset / height > 1f ? 1f : (float) offset / height;
                mLottieAnimationView.setScale(progress / 20);
                mLottieAnimationView.setProgress(progress);
                // LogUtils.i("onMoving:offset=" + offset + " height=" + height);
            }
        }

    }


    @Override
    public void onStartAnimator(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {
        super.onStartAnimator(refreshLayout, height, maxDragHeight);
        if (mLottieAnimationView != null) {
            mLottieAnimationView.playAnimation();
        }
    }

    @Override
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean success) {
        if (mLottieAnimationView != null) {
            mLottieAnimationView.cancelAnimation();
        }
        return super.onFinish(refreshLayout, success);
    }
}
