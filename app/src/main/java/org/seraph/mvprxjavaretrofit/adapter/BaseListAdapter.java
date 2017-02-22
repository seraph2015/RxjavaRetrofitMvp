package org.seraph.mvprxjavaretrofit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by adde on 16/2/23.
 * 适配器全局基类(适用于listview)
 */
abstract class BaseListAdapter<T> extends BaseAdapter {

    LayoutInflater inflater;
    protected List<T> data;
    public Context context;

    BaseListAdapter(List<T> data, Context context) {
        inflater = LayoutInflater.from(context);
        this.data = data;
        this.context = context;
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
