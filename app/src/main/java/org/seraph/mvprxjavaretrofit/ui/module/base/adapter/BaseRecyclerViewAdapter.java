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

    public BaseRecyclerViewAdapter(Context mContext, int layoutId, List<T> mDataList) {
        this.mContext = mContext;
        this.mDataList = mDataList;
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
        return mDataList.size();
    }

    /**
     * 添加数据（默认替换数据）
     */
    public void addDataList(List<T> dataList) {
        mDataList.clear();
        mDataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public List<T> getDataList() {
        return mDataList;
    }

}
