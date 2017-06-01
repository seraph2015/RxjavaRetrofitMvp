package org.seraph.mvprxjavaretrofit.ui.module.base.adapter;

import android.content.Context;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * baseRecyclerView适配器
 * date：2017/5/8 09:31
 * author：xiongj
 * mail：417753393@qq.com
 **/
public abstract class BaseRvListAdapter<T> extends CommonAdapter<T> {

    @Override
    protected void convert(ViewHolder holder, T t, int position) {
        bindData(holder, t, position);
    }

    protected abstract void bindData(ViewHolder holder, T t, int position);


    public BaseRvListAdapter(Context context, int layoutId) {
        super(context, layoutId, new ArrayList<T>());
    }

    public void addAllListData(List<T> listData) {
        mDatas.clear();
        addListData(listData);
    }

    public void addListData(List<T> listData) {
        mDatas.addAll(listData);
        notifyDataSetChanged();
    }

}
