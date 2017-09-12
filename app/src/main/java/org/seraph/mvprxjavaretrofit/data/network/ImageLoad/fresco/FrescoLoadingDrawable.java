package org.seraph.mvprxjavaretrofit.data.network.ImageLoad.fresco;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

/**
 * fresco图片加载进度条（仿mac微信下载小视频进度条）
 * date：2017/9/11 18:23
 * author：Seraph
 * mail：417753393@qq.com
 **/
public class FrescoLoadingDrawable extends Drawable {

    //画笔
    private Paint mPaint;
    //背景颜色
    private int mBgColor;
    // 外圈圆环以及扇形进度颜色
    private int mColor;
    // 扇形半径
    private float mRadius;
    // 画笔宽度
    private float mStrokeWidth;
    // 总进度
    private int mTotalProgress;
    // 当前进度
    private int mProgress;

    public FrescoLoadingDrawable() {
        //初始化参数
        mBgColor = 0x99cccccc;
        mColor = 0xffffffff;
        mStrokeWidth = 4;
        mRadius = 60;
        mTotalProgress = 10000;
        mProgress = 0;
        //初始化画笔
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(mColor);
        mPaint.setStrokeWidth(mStrokeWidth);
    }


    @Override
    public void draw(@NonNull Canvas canvas) {
        if (mProgress > 0) {
            //画背景
            canvas.drawColor(mBgColor);
            // 圆心坐标
            Rect bound = getBounds();
            int xCenter = bound.centerX();
            int yCenter = bound.centerY();
            RectF oval = new RectF();
            oval.left = (xCenter - mRadius);
            oval.top = (yCenter - mRadius);
            oval.right = mRadius * 2 + (xCenter - mRadius);
            oval.bottom = mRadius * 2 + (yCenter - mRadius);
            //画当前进度扇形
            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawArc(oval, -90, ((float) mProgress / mTotalProgress) * 360, true, mPaint);
            //画外圈
            mPaint.setStyle(Paint.Style.STROKE);
            canvas.drawCircle(xCenter, yCenter, mRadius + 5, mPaint);
        }

    }

    @Override
    protected boolean onLevelChange(int level) {
        mProgress = level;
        if (level > 0 && level < 10000) {
            invalidateSelf();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        mPaint.setColorFilter(cf);
    }

    @Override
    public int getOpacity() {
        int colorAlpha = mPaint.getColor() >>> 24;
        if (colorAlpha == 255) {
            return PixelFormat.OPAQUE;
        } else if (colorAlpha == 0) {
            return PixelFormat.TRANSPARENT;
        } else {
            return PixelFormat.TRANSLUCENT;
        }
    }
}
