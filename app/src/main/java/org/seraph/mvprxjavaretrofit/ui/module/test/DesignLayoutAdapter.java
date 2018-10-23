package org.seraph.mvprxjavaretrofit.ui.module.test;

import android.app.Activity;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.data.network.glide.GlideApp;
import org.seraph.mvprxjavaretrofit.ui.module.main.model.ImageBaiduBean;
import org.seraph.mvprxjavaretrofit.ui.views.CustomSelfProportionImageView;

import javax.inject.Inject;

/**
 * 测试布局的适配器
 * date：2017/4/13 17:54
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class DesignLayoutAdapter extends BaseQuickAdapter<ImageBaiduBean.BaiduImage, BaseViewHolder> {


    private Activity activity;

    @Inject
    DesignLayoutAdapter(DesignLayoutTestActivity activity) {
        super(R.layout.test_item_design);
        this.activity = activity;
    }


    @Override
    protected void convert(BaseViewHolder helper, ImageBaiduBean.BaiduImage item) {
        CustomSelfProportionImageView imageView = helper.getView(R.id.imageView);
        GlideApp.with(activity).load(item.objURL).into(imageView);
    }
}
