package org.seraph.mvprxjavaretrofit.ui.module.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.ui.module.base.BaseListAdapter;
import org.seraph.mvprxjavaretrofit.ui.views.CustomSelfProportionImageView;
import org.seraph.mvprxjavaretrofit.utlis.Tools;
import org.seraph.mvprxjavaretrofit.utlis.ViewHolder;

import java.util.List;

/**
 * 图片列表
 * date：2017/2/22 13:43
 * author：xiongj
 * mail：417753393@qq.com
 **/
class ImageListBaiduAdapter extends BaseListAdapter<ImageBaiduBean.BaiduImage> {


    ImageListBaiduAdapter(Context context, List<ImageBaiduBean.BaiduImage> data) {
        super(context, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ImageBaiduBean.BaiduImage baiduImage = data.get(position);
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
            height = Tools.getNewHeight(width, height, imageViewWidth);
            width = imageViewWidth;
        }
        loadingImage(imageView, baiduImage.objURL, width, height);
        return convertView;
    }

    private void loadingImage(ImageView imageView, String imageUrl, int imageWidth, int imageHeight) {
        Picasso.with(mContext).load(imageUrl)
                .placeholder(R.mipmap.icon_placeholder)
                .error(R.mipmap.icon_error)
                .resize(imageWidth, imageHeight)
                .config(Bitmap.Config.RGB_565) //对于不透明的图片可以使用RGB_565来优化内存。RGB_565呈现结果与ARGB_8888接近
                .centerInside()
                .into(imageView);
    }

}
