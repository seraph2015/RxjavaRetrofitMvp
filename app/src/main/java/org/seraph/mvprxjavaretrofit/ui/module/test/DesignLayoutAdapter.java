package org.seraph.mvprxjavaretrofit.ui.module.test;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.data.network.picasso.PicassoTool;
import org.seraph.mvprxjavaretrofit.ui.module.base.adapter.BaseRecyclerViewAdapter;
import org.seraph.mvprxjavaretrofit.ui.module.base.adapter.BaseRecyclerViewHolder;
import org.seraph.mvprxjavaretrofit.ui.module.main.ImageBaiduBean;
import org.seraph.mvprxjavaretrofit.utlis.Tools;

import javax.inject.Inject;

/**
 * 测试布局的适配器
 * date：2017/4/13 17:54
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class DesignLayoutAdapter extends BaseRecyclerViewAdapter<ImageBaiduBean.BaiduImage> {

    interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private int targetWidth;
    private int targetHeight;

    private OnItemClickListener onItemClickListener;

    private PicassoTool mPicassoTool;

    @Inject
    DesignLayoutAdapter(PicassoTool picassoTool, Context mContext) {
        this(mContext, picassoTool, null);
    }

    DesignLayoutAdapter(Context mContext, PicassoTool picassoTool, OnItemClickListener onItemClickListener) {
        super(mContext, R.layout.item_design);
        targetWidth = Tools.dip2px(mContext, 200);
        targetHeight = Tools.dip2px(mContext, 150);
        this.mPicassoTool = picassoTool;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    protected void convert(final BaseRecyclerViewHolder holder, ImageBaiduBean.BaiduImage baiduImage) {
        ImageView imageView = holder.getView(R.id.imageView);
        mPicassoTool.loadNoCache(baiduImage.objURL, imageView, targetWidth, targetHeight);

        holder.setOnClickListener(R.id.imageView, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, holder.getViewPosition());
                }
            }
        });
    }

    void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}