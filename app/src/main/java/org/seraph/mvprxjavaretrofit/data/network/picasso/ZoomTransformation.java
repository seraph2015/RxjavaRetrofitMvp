package org.seraph.mvprxjavaretrofit.data.network.picasso;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.squareup.picasso.Transformation;

/**
 * picasso图形变换，如果图片大于目标宽，则等比缩小图片
 * <p>
 * date：2017/8/29 11:54
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class ZoomTransformation implements Transformation {


    private int mTargetWidth;

    private ImageView mTargetImageView;

    public ZoomTransformation(int targetWidth) {
        this.mTargetWidth = targetWidth;
    }

    public ZoomTransformation(ImageView targetImageView) {
        this.mTargetImageView = targetImageView;
    }

    @Override
    public Bitmap transform(Bitmap source) {
        if (mTargetWidth == 0) {
            mTargetWidth = mTargetImageView.getWidth();
        }
        if (source.getWidth() == 0) {
            return source;
        }
        //如果图片小于设置的宽度，则返回原图
        if (source.getWidth() < mTargetWidth) {
            return source;
        } else {
            //如果图片大小大于等于设置的宽度，则按照设置的宽度比例来缩放
            double aspectRatio = (double) source.getHeight() / (double) source.getWidth();
            int targetHeight = (int) (mTargetWidth * aspectRatio);
            if (targetHeight != 0 && mTargetWidth != 0) {
                Bitmap result = Bitmap.createScaledBitmap(source, mTargetWidth, targetHeight, false);
                if (result != source) {
                    // Same bitmap is returned if sizes are the same
                    source.recycle();
                }
                return result;
            } else {
                return source;
            }
        }
    }

    @Override
    public String key() {
        if (mTargetWidth == 0) {
            mTargetWidth = mTargetImageView.getWidth();
        }
        return "zoomTransformation" + mTargetWidth;
    }
}
