package org.seraph.mvprxjavaretrofit.ui.views;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListAdapter;

import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.data.network.ImageLoad.picasso.PicassoTool;
import org.seraph.mvprxjavaretrofit.ui.module.base.adapter.BaseListAdapter;
import org.seraph.mvprxjavaretrofit.ui.module.base.adapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 仿微博图片排列
 * 使用注意控件宽度需要与屏幕宽度一致
 */
public class WeiBoGridView extends GridView {


    //  android:stretchMode="none"

    /**
     * 重写该方法，达到使GridView适应ScrollView的效果
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                View.MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }


    private List<String> mData = new ArrayList<>();

    private int mHorizontalSpacing, mVerticalSpacing;

    public WeiBoGridView(Context context) {
        super(context);
        setStretchMode(GridView.NO_STRETCH);
    }

    public WeiBoGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setStretchMode(GridView.NO_STRETCH);
        TypedArray array = getContext().obtainStyledAttributes(attrs,
                R.styleable.WeiBoGridView);
        mHorizontalSpacing = array.getDimensionPixelSize(R.styleable.WeiBoGridView_hSpacing, 0);
        mVerticalSpacing = array.getDimensionPixelSize(R.styleable.WeiBoGridView_vSpacing, 0);
        setStretchMode(GridView.NO_STRETCH);
        array.recycle();
    }

    public WeiBoGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private int mColumnNum;

    public void setPhotoAdapter(List<String> imgList, AdapterView.OnItemClickListener onItemClickListener) {
        mData.clear();
        mData.addAll(imgList);
        int count = mData.size();
        //根据图片来设置列数
        switch (count) {
            case 1:
                mColumnNum = 1;
                setNumColumns(1);
                break;
            case 2:
            case 4:
                mColumnNum = 2;
                setNumColumns(2);
                break;
            default:
                mColumnNum = 3;
                setNumColumns(3);
                break;
        }

        setHorizontalSpacing(mHorizontalSpacing);
        setVerticalSpacing(mVerticalSpacing);
        int width = calculateColumnWidth();
        setColumnWidth(width);
        FeedPhotoAdapter photoAdapter = new FeedPhotoAdapter(getContext(), mData, width);
        this.setAdapter(photoAdapter);
        if (onItemClickListener != null) {
            this.setOnItemClickListener(onItemClickListener);
        }
        photoAdapter.notifyDataSetChanged();
        setGridViewWidthBasedOnChildren(this, mData.size());
    }

    private int calculateColumnWidth() {
        //获取view宽
        int width = getScreenWidth((Activity) getContext());
        switch (mColumnNum) {
            case 0:
                //width = width;
                break;
            case 1:
                width = width - getPaddingRight() - getPaddingLeft();
                break;
            case 2:
                width = (width - getPaddingRight() - getPaddingLeft() - mHorizontalSpacing) / mColumnNum;
                break;
            case 3:
                width = (width - getPaddingRight() - getPaddingLeft() - mHorizontalSpacing * 2) / mColumnNum;
                break;
        }
        return width;
    }

    private int getScreenWidth(Activity context) {
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }


    /**
     * 动态计算gridView的宽度
     */
    public void setGridViewWidthBasedOnChildren(GridView gridView, int count) {
        // 获取gridview的adapter
        ListAdapter listAdapter = gridView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        // 固定列宽，有多少列
        int col = count;
        if (listAdapter.getCount() < count) {
            col = listAdapter.getCount();
        }
        if (count == 4) {
            col = 2;
        }
        int totalWidth = 0;
        for (int i = 0; i < col; i++) {
            // 获取gridview的每一个item
            View listItem = listAdapter.getView(i, null, gridView);
            listItem.measure(0, 0);
            // 获取item的宽度和
            totalWidth += listItem.getMeasuredWidth() + mHorizontalSpacing * 2;
        }
        // 获取gridview的布局参数
        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        // 设置宽度
        params.width = totalWidth + getPaddingRight() + getPaddingLeft();
        // 设置参数
        gridView.setLayoutParams(params);
    }

    private class FeedPhotoAdapter extends BaseListAdapter<String> {

        private int mColumnWidth;

        FeedPhotoAdapter(Context context, List<String> mData, int width) {
            super(context, R.layout.common_view_weibo_item_image,mData);
            this.mColumnWidth = width;
        }


        @Override
        public void bindView(int position, View view, String imageUrl) {
            CustomSquareImageView photo =  ViewHolder.get(view,R.id.iv_photo);
            ViewGroup.LayoutParams params = photo.getLayoutParams();
            params.width = mColumnWidth;
            photo.setLayoutParams(params);
            PicassoTool.loadCache(mContext,imageUrl,photo,mColumnWidth,mColumnWidth);
        }
    }
}
