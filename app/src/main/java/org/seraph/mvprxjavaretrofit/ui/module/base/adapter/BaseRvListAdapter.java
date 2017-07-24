package org.seraph.mvprxjavaretrofit.ui.module.base.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.seraph.mvprxjavaretrofit.R;

import java.util.ArrayList;
import java.util.List;

/**
 * baseRecyclerView适配器
 * date：2017/5/8 09:31
 * author：xiongj
 * mail：417753393@qq.com
 **/
public abstract class BaseRvListAdapter<T> extends RecyclerView.Adapter<ViewHolderRv> {


    public interface OnItemClickListener {
        void onItemClick(View view, RecyclerView.ViewHolder holder, int position);

        boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position);
    }


    public interface LoadMoreListener {
        void onLoadMore();
    }

    protected OnItemClickListener mOnItemClickListener;

    private LoadMoreListener loadMoreListener;

    protected final int ITEM_DATA_VIEW = 0;
    protected final int ITEM_FOOT_VIEW = 1;

    protected Context mContext;
    protected int mLayoutId;
    protected LayoutInflater mInflater;
    private TextView tvMore;
    private ProgressBar pbMore;
    protected List<T> mDatas;

    private boolean mIsAutoLoad = true;
    private boolean mIsMoreData = true;

    //默认显示加载更多
    public BaseRvListAdapter(Context context, int layoutId) {
        this(context, layoutId, true);
    }

    //默认显示加载更多
    public BaseRvListAdapter(Context context, final int layoutId, boolean isAutoLoad) {
        mContext = context;
        mDatas = new ArrayList<>();
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
        mIsAutoLoad = isAutoLoad;
    }


    @Override
    public int getItemCount() {
        if (mDatas.size() == 0) {
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
    public ViewHolderRv onCreateViewHolder(ViewGroup parent, int viewType) {
        //默认的数据view
        //尾部的加载更多view
        switch (viewType) {
            case ITEM_DATA_VIEW: //数据布局
                ViewHolderRv holder = ViewHolderRv.createViewHolder(mContext, parent, mLayoutId);
                setListener(parent, holder, viewType);
                return holder;
            case ITEM_FOOT_VIEW: //加载更多view
                ViewHolderRv mFooterHolder = ViewHolderRv.createViewHolder(mContext, parent, R.layout.default_foot_more_loading);
                tvMore = mFooterHolder.getView(R.id.tv_more);
                pbMore = mFooterHolder.getView(R.id.pb_more);
                return mFooterHolder;
        }
        return null;
    }

    private void setListener(ViewGroup parent, final ViewHolderRv viewHolder, int viewType) {
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = viewHolder.getAdapterPosition();
                    mOnItemClickListener.onItemClick(v, viewHolder, position);
                }
            }
        });

        viewHolder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = viewHolder.getAdapterPosition();
                    return mOnItemClickListener.onItemLongClick(v, viewHolder, position);
                }
                return false;
            }
        });
    }


    protected abstract void bindData(ViewHolderRv holder, T t, int position);

    @Override
    public void onBindViewHolder(ViewHolderRv holder, int position) {
        int itemType = getItemViewType(position);
        switch (itemType) {
            case ITEM_DATA_VIEW: //数据布局
                bindData(holder, mDatas.get(position), position);
                break;
            case ITEM_FOOT_VIEW: //底部
                if (loadMoreListener != null && mIsMoreData) {
                    loadMoreListener.onLoadMore();
                }
                break;
        }
    }


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


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
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
