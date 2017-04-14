package org.seraph.mvprxjavaretrofit.ui.module.base.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * 适配器全局基类(适用于RecyclerView)
 */
public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewHolder> {

    protected Context mContext;

    protected List<T> mDataList;

    private int mLayoutId;

    public BaseRecyclerViewAdapter(Context mContext, int layoutId) {
        this.mContext = mContext;
        this.mLayoutId = layoutId;
    }


    protected abstract void convert(BaseRecyclerViewHolder holder, T t);

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return BaseRecyclerViewHolder.get(mContext, parent, mLayoutId);
    }


    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder holder, int position) {
        holder.setViewPosition(position);
        convert(holder, mDataList.get(position));
    }


    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    /**
     * 添加数据（默认替换数据）
     */
    public void addDataList(List<T> dataList) {
        //如果没有使用的数据的内存，则直接使用此数据源地址（之后直接刷即可）
        if (mDataList == null) {
            mDataList = dataList;
        }
        notifyDataSetChanged();
    }

    public List<T> getDataList() {
        return mDataList;
    }

}
