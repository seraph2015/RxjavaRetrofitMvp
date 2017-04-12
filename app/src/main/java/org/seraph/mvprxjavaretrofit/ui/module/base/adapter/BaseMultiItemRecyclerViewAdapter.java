package org.seraph.mvprxjavaretrofit.ui.module.base.adapter;

import android.content.Context;
import android.view.ViewGroup;

import java.util.List;

/**
 * 多类型的通用适配器
 * date：2017/4/12 11:41
 * author：xiongj
 * mail：417753393@qq.com
 **/
public abstract class BaseMultiItemRecyclerViewAdapter<T> extends BaseRecyclerViewAdapter<T> {

    protected MultiItemTypeSupport<T> mMultiItemTypeSupport;

    public BaseMultiItemRecyclerViewAdapter(Context mContext, List<T> mDataList, MultiItemTypeSupport<T> multiItemTypeSupport) {
        super(mContext, -1, mDataList);
        this.mMultiItemTypeSupport = multiItemTypeSupport;
    }

    @Override
    public int getItemViewType(int position) {
        return mMultiItemTypeSupport.getItemViewType(position, mDataList.get(position));
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId = mMultiItemTypeSupport.getLayoutId(viewType);
        return BaseRecyclerViewHolder.get(mContext, parent, layoutId);
    }
}
