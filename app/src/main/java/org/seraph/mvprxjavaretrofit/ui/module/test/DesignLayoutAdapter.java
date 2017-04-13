package org.seraph.mvprxjavaretrofit.ui.module.test;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.ui.module.base.adapter.BaseRecyclerViewAdapter;
import org.seraph.mvprxjavaretrofit.ui.module.base.adapter.BaseRecyclerViewHolder;
import org.seraph.mvprxjavaretrofit.ui.module.main.ImageBaiduBean;
import org.seraph.mvprxjavaretrofit.utlis.Tools;

import java.util.ArrayList;

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

    @Inject
    DesignLayoutAdapter(Context mContext) {
        this(mContext, null);
    }

    DesignLayoutAdapter(Context mContext, OnItemClickListener onItemClickListener) {
        super(mContext, R.layout.item_design, new ArrayList<ImageBaiduBean.BaiduImage>());
        targetWidth = Tools.dip2px(mContext, 200);
        targetHeight = Tools.dip2px(mContext, 150);
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    protected void convert(final BaseRecyclerViewHolder holder, ImageBaiduBean.BaiduImage baiduImage) {
        ImageView imageView = holder.getView(R.id.imageView);
        Picasso.with(mContext).load(baiduImage.objURL)
                .placeholder(R.mipmap.icon_placeholder)
                .error(R.mipmap.icon_error)
                .resize(targetWidth, targetHeight)
                .centerCrop()
                .config(Bitmap.Config.RGB_565) //对于不透明的图片可以使用RGB_565来优化内存。RGB_565呈现结果与ARGB_8888接近
                .into(imageView);
        holder.setOnClickListener(R.id.imageView, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, holder.getViewPosition());
                }
            }
        });
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
