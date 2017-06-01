package org.seraph.mvprxjavaretrofit.ui.module.base.adapter;

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
public abstract class BaseListAdapter<T> extends BaseAdapter {

    protected LayoutInflater inflater;
    protected List<T> data;
    protected Context mContext;
    protected int mLayoutId;

    public BaseListAdapter(Context context, int layoutId, List<T> data) {
        inflater = LayoutInflater.from(context);
        this.mContext = context;
        this.data = data;
        this.mLayoutId = layoutId;

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
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(mLayoutId, parent, false);
        }
        bindView(position, new ViewHolder(convertView), getItem(position));
        return convertView;
    }


    public abstract void bindView(int position, ViewHolder viewHolder, T t);

    public class ViewHolder extends org.seraph.mvprxjavaretrofit.utlis.ViewHolder {

        private View view;

        public ViewHolder(View view) {
            this.view = view;
        }

        public <T extends View> T getView(int id) {
            return get(view, id);
        }
    }

    public void addAllListData(List<T> list) {
        data.clear();
        addListData(list);
    }

    public void addListData(List<T> list) {
        data.addAll(list);
        notifyDataSetChanged();
    }
}
