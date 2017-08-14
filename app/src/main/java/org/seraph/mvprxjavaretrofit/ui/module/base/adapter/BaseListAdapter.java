package org.seraph.mvprxjavaretrofit.ui.module.base.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import org.seraph.mvprxjavaretrofit.R;

import java.util.ArrayList;
import java.util.List;

/**
 * baseListView适配器(添加自动加载更多)
 * 如使用RecyclerView加载布局，使用BaseRecyclerViewAdapterHelper框架，见{@link BaseQuickAdapter}
 * 详细文档见：http://www.jianshu.com/p/b343fcff51b0
 * date：2017/5/8 09:31
 * author：xiongj
 * mail：417753393@qq.com
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
    protected List<T> mDatas;
    protected Context mContext;
    protected int mLayoutId;

    protected final int ITEM_DATA_VIEW = 0;
    protected final int ITEM_FOOT_VIEW = 1;

    private boolean mIsAutoLoad = true;
    private boolean mIsMoreData = true;

    public BaseListAdapter(Context context, int layoutId) {
        this(context, layoutId, new ArrayList<T>());
    }

    public BaseListAdapter(Context context, int layoutId, List<T> data) {
        this(context, layoutId, data, true);
    }

    public BaseListAdapter(Context context, int layoutId, List<T> data, boolean isAutoLoad) {
        inflater = LayoutInflater.from(context);
        this.mContext = context;
        this.mDatas = data;
        this.mLayoutId = layoutId;
        mIsAutoLoad = isAutoLoad;
    }


    @Override
    public int getCount() {
        if (null == mDatas || mDatas.size() == 0) {
            return 0;
        } else {
            return mIsAutoLoad ? mDatas.size() + 1 : mDatas.size();
        }
    }


    @Override
    public int getItemViewType(int position) {
        //添加自动加载更多view
        if (position == mDatas.size() && mIsAutoLoad) {
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
        if (position == mDatas.size()) {
            return null;
        }
        return mDatas.get(position);
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
                if (loadMoreListener != null && mIsMoreData) {
                    loadMoreListener.onLoadMore();
                }
                break;
        }
        return convertView;
    }


    public abstract void bindView(int position, View view, T t);


    public void addAllListData(List<T> listData) {
        mDatas.clear();
        addListData(listData);
    }

    public void addListData(List<T> listData) {
        if (listData != null) {
            mDatas.addAll(listData);
        }
        notifyDataSetChanged();
    }

    public List<T> getDatas() {
        return mDatas;
    }

    public void setLoadMoreListener(LoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }

    public void setIsMoreData(boolean isMoreData) {
        if (tvMore != null && pbMore != null) {
            mIsMoreData = isMoreData;
            if (mIsMoreData) {
                tvMore.setText("正在加载更多");
                pbMore.setVisibility(View.VISIBLE);
            } else {
                tvMore.setText("没有更多");
                pbMore.setVisibility(View.GONE);
            }
        }
    }
}
