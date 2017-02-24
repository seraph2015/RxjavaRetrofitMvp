package org.seraph.mvprxjavaretrofit.adapter;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.mvp.model.BaiduImageBean;
import org.seraph.mvprxjavaretrofit.utlis.Tools;
import org.seraph.mvprxjavaretrofit.utlis.ViewHolder;
import org.seraph.mvprxjavaretrofit.views.CustomSelfProportionImageView;

import java.util.List;

/**
 * 图片列表
 * date：2017/2/22 13:43
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class ImageListAdapter extends BaseListAdapter<BaiduImageBean.BaiduImage> {


    public ImageListAdapter(Context context, List<BaiduImageBean.BaiduImage> data) {
        super(context, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaiduImageBean.BaiduImage baiduImage = data.get(position);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_image, parent, false);
        }
        CustomSelfProportionImageView imageView = ViewHolder.get(convertView, R.id.image);
        imageView.setSize(baiduImage.width, baiduImage.height);
        TextView textTitle = ViewHolder.get(convertView, R.id.tv_title);
        textTitle.setText(Html.fromHtml(baiduImage.fromPageTitle + " " + baiduImage.width + "x" + baiduImage.height));
        if (baiduImage.isShowTitle) {
            textTitle.setVisibility(View.VISIBLE);
        } else {
            textTitle.setVisibility(View.GONE);
        }
        //按照控件的大小来缩放图片的尺寸
        int width = baiduImage.width;
        int height = baiduImage.height;
        int imageViewWidth = imageView.getMeasuredWidth();
        if (imageViewWidth != 0) {
            width = imageViewWidth;
            height = Tools.getNewHeight(baiduImage.width, baiduImage.height, imageViewWidth);
        }
        Picasso.with(context).load(baiduImage.objURL)
                .placeholder(R.mipmap.icon_placeholder)
                .error(R.mipmap.icon_error)
                .resize(width, height)
                .centerInside()
                .into(imageView);
        return convertView;
    }
}
