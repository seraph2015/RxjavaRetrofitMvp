package org.seraph.mvprxjavaretrofit.ui.module.base.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 通用RecyclerView.ViewHolder
 * date：2017/4/12 11:00
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class BaseRecyclerViewHolder extends RecyclerView.ViewHolder {

    //那么对于不同的ItemType肯定没有办法确定创建哪些成员变量View，取而代之的只能是个集合来存储
    private SparseArray<View> mViews;

    private View mConvertView;

    private BaseRecyclerViewHolder(Context context, View itemView, ViewGroup parent) {
        super(itemView);
        mConvertView = itemView;
        mViews = new SparseArray<>();
    }

    public static BaseRecyclerViewHolder get(Context context, ViewGroup parent, int layoutId) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        return new BaseRecyclerViewHolder(context, itemView, parent);
    }


    /**
     * 通过viewId获取控件
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    // 一些通用的控件操作
    public BaseRecyclerViewHolder setText(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    public BaseRecyclerViewHolder setImageResource(int viewId, int resId) {
        ImageView view = getView(viewId);
        view.setImageResource(resId);
        return this;
    }

    public BaseRecyclerViewHolder setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

}
