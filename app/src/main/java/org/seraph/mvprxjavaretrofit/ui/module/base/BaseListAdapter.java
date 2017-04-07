package org.seraph.mvprxjavaretrofit.ui.module.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by adde on 16/2/23.
 * 适配器全局基类(适用于listview)
 */public abstract class BaseListAdapter<T> extends BaseAdapter {

    protected LayoutInflater inflater;
    protected List<T> data;
    protected Context mContext;

    public BaseListAdapter(Context context, List<T> data) {
        inflater = LayoutInflater.from(context);
        this.mContext = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        if (null == data) {
            return 0;
        } else {
            return data.size();
        }
    }

    @Override
    public T getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public abstract View getView(int position, View convertView, ViewGroup parent);


}
