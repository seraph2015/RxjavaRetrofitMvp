package org.seraph.mvprxjavaretrofit.ui.views.addImage;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import org.seraph.mvprxjavaretrofit.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 所有图片的容器
 */
public class CustomImageViewGroup extends ViewGroup implements OnLongClickListener {
    private int margin;
    private OnClickPicListener onClickPicListener;

    //图片路径
    private List<String> selectedPaths = new ArrayList<>();
    private OnContentChangeListener contentChangeListener;

    //限制的最大数量
    private int maxSize = 9;

    public CustomImageViewGroup(Context context) {
        super(context);
        init();
    }

    public CustomImageViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init() {
        removeAllViews();
        showAddIcon();
    }

    public void showAddIcon() {
        CustomImageLayout mAddIconView = new CustomImageLayout(getContext());
        mAddIconView.setTag(0);
        mAddIconView.setOnClickListener(new ClickListener(getChildCount()));
        addView(mAddIconView);
        requestLayout();
    }

    /**
     * 设定图片数据源
     */
    public void setItemPaths(List<String> pathList) {
        if (isMaxSize()) {
            return;
        }
        if (pathList == null) {
            return;
        }
        selectedPaths.clear();
        selectedPaths.addAll(pathList);
        refreshLayout();

    }

    /**
     * 刷新布局
     */
    private void refreshLayout() {
        removeAllViews();
        for (int i = 0; i < (selectedPaths.size() > maxSize ? maxSize : selectedPaths.size()); i++) {
            addImageView(selectedPaths.get(i));
        }

        if (!isMaxSize()) {
            showAddIcon();
        }
    }

    /**
     * 是否已经是最大的限制数量（有包含+按钮需要加1）
     */
    private boolean isMaxSize() {
        return selectedPaths.size() >= maxSize;
    }


    private void addImageView(String path) {
        CustomImageLayout view = new CustomImageLayout(getContext());
        ImageView ivDelete = view.getDeleteView();
        view.setImagePath(path);
        ivDelete.setTag(path);
        view.setOnDeleteListener(deleteListener);
        view.setTag(1);
        view.setOnClickListener(new ClickListener(getChildCount()));
        view.setOnLongClickListener(this);
        addView(view);
    }

    @Override
    protected void onLayout(boolean flag, int l, int t, int r, int b) {
        int childCount = getChildCount();
        if (childCount == 0) {
            return;
        }
        for (int i = 0; i < childCount; i++) {
            final View child = this.getChildAt(i);
            if (child.getVisibility() != View.GONE) {
                int width = child.getMeasuredWidth();
                int height = child.getMeasuredHeight();
                int left = (margin + width) * (i % 3) + margin;
                int top = (margin + height) * (i / 3) + margin;
                int right = left + width;
                int bottom = top + height;
                child.layout(left, top, right, bottom);
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int maxWidth = MeasureSpec.getSize(widthMeasureSpec);
        int childCount = getChildCount();

        int childheight = 0;
        int childWidth = 0;

        for (int index = 0; index < childCount; index++) {
            final View child = getChildAt(index);
            if (child.getVisibility() != View.GONE) {
                child.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
                // 此处增加onlayout中的换行判断，用于计算所需的高度
                childWidth = child.getMeasuredWidth();
                childheight = child.getMeasuredHeight();
            }
        }
        margin = (maxWidth - 3 * childWidth) / 4;
        int line = childCount / 3;
        if (childCount % 3 != 0) {
            line += 1;
        }

        // 设置容器所需的宽度和高度
        setMeasuredDimension(maxWidth, margin + (margin + childheight) * line);
    }

    private class ClickListener implements OnClickListener {
        private int position;

        ClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            if (onClickPicListener != null) {
                onClickPicListener.OnPicClick(v, position);
            }
        }

    }

    public interface OnClickPicListener {
        void OnPicClick(View v, int position);
    }

    public void setOnClickPicListener(OnClickPicListener listener) {
        onClickPicListener = listener;
    }

    public interface OnContentChangeListener {
        void OnContentChanged(String path);
    }

    public void setOnContentChangeListener(OnContentChangeListener listener) {
        contentChangeListener = listener;
    }

    @Override
    public boolean onLongClick(View v) {
        if (onClickPicListener != null) {
            CustomImageLayout layout = (CustomImageLayout) v;
            layout.showDeleteIcon();
            layout.setDeleteMode(true);
            // 添加抖动的动画
            Animation shake = AnimationUtils.loadAnimation(getContext(), R.anim.common_add_image_shake_anim);
            shake.reset();
            shake.setFillAfter(true);
            layout.startAnimation(shake);
        }
        return true; // 此处返回true表示事件不再向下传递,否则会出发onClick事件
    }

    public void resetLongClick(int position) {
        CustomImageLayout layout = (CustomImageLayout) getChildAt(position);
        layout.clearAnimation();
        layout.cancelDelete();
        layout.setDeleteMode(false);
    }

    private CustomImageLayout.OnDeleteListener deleteListener = new CustomImageLayout.OnDeleteListener() {

        @Override
        public void onDelete(String path) {
            int index = selectedPaths.indexOf(path);
            CustomImageLayout layout = (CustomImageLayout) getChildAt(index);
            layout.clearAnimation();
            layout.cancelDelete();
            selectedPaths.remove(path);
            if (contentChangeListener != null) {
                contentChangeListener.OnContentChanged(path);
            }
            //刷新布局
            refreshLayout();

        }

    };


}
