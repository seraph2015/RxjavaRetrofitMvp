package org.seraph.mvprxjavaretrofit.ui.views.addImage;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;

import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.data.network.ImageLoad.glide.GlideApp;

import java.io.File;


/**
 * 单个图片布局
 */
public class CustomImageLayout extends LinearLayout {
    private Context mContext;
    private ImageView imageView;
    private ImageView ivDeleteView;
    private OnDeleteListener onDeleteListener;
    private boolean deleteMode = false;

    public CustomImageLayout(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public CustomImageLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {
        LayoutInflater.from(mContext).inflate(R.layout.common_add_image_layout, this);
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.fl_image_root);
        imageView = (ImageView) findViewById(R.id.iv_add_image_item);
        ivDeleteView = (ImageView) findViewById(R.id.iv_add_image_item_delete);
        //设置大小为屏幕宽 - 4* 10dp / 3
        int size = (ScreenUtils.getScreenWidth() - 4 * SizeUtils.dp2px(10)) / 3;
        frameLayout.setLayoutParams(new LinearLayout.LayoutParams(size, size));
        ivDeleteView.setOnClickListener(listener);
    }

    private OnClickListener listener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            if (onDeleteListener != null) {
                if (!TextUtils.isEmpty((String) v.getTag())) {
                    onDeleteListener.onDelete((String) v.getTag());
                }
            }
        }
    };

    public void setImageResource(int resId) {
        imageView.setImageResource(resId);
    }

    public void setImageBitmap(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }

    public void setImagePath(String path) {
        GlideApp.with(mContext).load(path.contains("http://") ? path : new File(path)).centerCrop().into(imageView);
        //PicassoTool.loadCache(mContext, new File(path), imageView, SizeUtils.dp2px(180), SizeUtils.dp2px(180));
    }

    // 显示删除图标
    public void showDeleteIcon() {
        if (ivDeleteView.getVisibility() == View.GONE) {
            ivDeleteView.setVisibility(View.VISIBLE);
        }
    }

    // 撤销删除图标
    public void cancelDelete() {
        if (ivDeleteView.getVisibility() == View.VISIBLE) {
            ivDeleteView.setVisibility(View.GONE);
        }
    }

    public void setDeleteMode(boolean mode) {
        deleteMode = mode;
    }

    public boolean getDeleteMode() {
        return deleteMode;
    }

    public interface OnDeleteListener {
        void onDelete(String path);
    }

    public void setOnDeleteListener(OnDeleteListener l) {
        onDeleteListener = l;
    }

    public ImageView getDeleteView() {
        return ivDeleteView;
    }

}
