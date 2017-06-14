package org.seraph.mvprxjavaretrofit.ui.module.base.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.seraph.mvprxjavaretrofit.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adde on 16/2/23.
 * 适配器全局基类(适用于listview)
 */
public abstract class BaseListAdapter<T> extends BaseAdapter {

    public interface LoadMoreListener {
        void onLoadMore();
    }

    private LoadMoreListener loadMoreListener;

    private ProgressBar pbMore;
    private TextView tvMore;

    protected LayoutInflater inflater;
    protected List<T> data;
    protected Context mContext;
    protected int mLayoutId;

    protected final int ITEM_DATA_VIEW = 0;
    protected final int ITEM_FOOT_VIEW = 1;



    public BaseListAdapter(Context context, int layoutId) {
        this(context,layoutId,new ArrayList<T>());
    }

    public BaseListAdapter(Context context,  int layoutId,List<T> data) {
        inflater = LayoutInflater.from(context);
        this.mContext = context;
        this.data = data;
        this.mLayoutId = layoutId;
    }

    @Override
    public int getCount() {
        if (null == data || data.size() == 0) {
            return 0;
        } else {
            return data.size() + 1;
        }
    }


    @Override
    public int getItemViewType(int position) {
        //添加自动加载更多view
        if (position == data.size()) {
            return ITEM_FOOT_VIEW;
        } else {
            return ITEM_DATA_VIEW;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public T getItem(int position) {
        if (position == data.size()) {
            return null;
        }
        return data.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int itemType = getItemViewType(position);
        switch (itemType) {
            case ITEM_DATA_VIEW: //数据布局
                if (convertView == null) {
                    convertView = inflater.inflate(mLayoutId, parent, false);
                }
                bindView(position, convertView, getItem(position));
                break;
            case ITEM_FOOT_VIEW: //底部
                if (convertView == null) {
                    convertView = inflater.inflate(R.layout.default_foot_more_loading, parent, false);
                }
                tvMore = (TextView) convertView.findViewById(R.id.tv_more);
                pbMore = (ProgressBar) convertView.findViewById(R.id.pb_more);
                pbMore.setVisibility(View.VISIBLE);
                tvMore.setText("正在加载更多");
                if (loadMoreListener != null) {
                    loadMoreListener.onLoadMore();
                }
                break;
        }
        return convertView;
    }


    public abstract void bindView(int position, View view, T t);


    public void addAllListData(List<T> list) {
        data.clear();
        addListData(list);
    }

    public void addListData(List<T> list) {
        data.addAll(list);
        notifyDataSetChanged();
    }

    public void setLoadMoreListener(LoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }

    public void onNoMoreData() {
        if (tvMore != null) {
            tvMore.setText("没有更多");
        }
        if (pbMore != null) {
            pbMore.setVisibility(View.GONE);
        }
    }
}
