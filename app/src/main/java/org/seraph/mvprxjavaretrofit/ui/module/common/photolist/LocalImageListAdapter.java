package org.seraph.mvprxjavaretrofit.ui.module.common.photolist;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.blankj.utilcode.util.SizeUtils;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.data.network.picasso.PicassoTool;
import org.seraph.mvprxjavaretrofit.ui.module.base.adapter.BaseRvListAdapter;
import org.seraph.mvprxjavaretrofit.ui.views.CustomSquareImageView;

import java.io.File;
import java.util.ArrayList;

import javax.inject.Inject;


/**
 * 显示并选择本地图片的适配器
 */
class LocalImageListAdapter extends BaseRvListAdapter<LocalImageBean> {

    private ArrayList<String> mSelectedPathList = new ArrayList<>();

    private int size;

    @Inject
    LocalImageListAdapter(Context context) {
        super(context, R.layout.common_activity_loacl_image_item);
        size = SizeUtils.dp2px(120);
    }


    @Override
    protected void bindData(ViewHolder holder, final LocalImageBean localImageBean, final int position) {
        CustomSquareImageView imageView = holder.getView(R.id.iv_image_item);
        final ImageView tagView = holder.getView(R.id.iv_image_item_tag);
        PicassoTool.loadCache(mContext, new File(localImageBean.path), imageView, size, size);
        if (mSelectedPathList.contains(localImageBean.path)) {
            tagView.setVisibility(View.VISIBLE);
        } else {
            tagView.setVisibility(View.GONE);
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (localImageBean.isSelected) { //取消选中
                    mDatas.get(position).isSelected = false;
                    if (mSelectedPathList.contains(localImageBean.path)) {
                        mSelectedPathList.remove(localImageBean.path);
                    }
                    tagView.setVisibility(View.GONE);
                } else {
                    if (mSelectedPathList.size() >= 1) {
                        Toast.makeText(mContext, "最多选择9张图片！", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (!mSelectedPathList.contains(localImageBean.path)) {
                        mSelectedPathList.add(localImageBean.path);
                    }
                    mDatas.get(position).isSelected = true;
                    tagView.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public ArrayList<String> getSelectedPathList() {
        return mSelectedPathList;
    }


    public void setSelectedPathList(ArrayList<String> selectedPathList) {
        if (selectedPathList != null && selectedPathList.size() > 0) {
            this.mSelectedPathList = selectedPathList;
            notifyDataSetChanged();
        }
    }
}
