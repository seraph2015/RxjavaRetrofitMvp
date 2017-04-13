package org.seraph.mvprxjavaretrofit.ui.module.test;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.jakewharton.rxbinding2.support.v7.widget.RxToolbar;
import com.squareup.picasso.Picasso;

import org.reactivestreams.Subscription;
import org.seraph.mvprxjavaretrofit.AppApplication;
import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.data.network.ApiManager;
import org.seraph.mvprxjavaretrofit.di.component.test.DaggerDesignLayoutComponent;
import org.seraph.mvprxjavaretrofit.di.module.DesignLayoutModule;
import org.seraph.mvprxjavaretrofit.ui.module.base.BaseActivity;
import org.seraph.mvprxjavaretrofit.ui.module.common.photopreview.PhotoPreviewActivity;
import org.seraph.mvprxjavaretrofit.ui.module.common.photopreview.PhotoPreviewBean;
import org.seraph.mvprxjavaretrofit.ui.module.main.ImageBaiduBean;
import org.seraph.mvprxjavaretrofit.utlis.Tools;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Design风格布局效果测试
 * date：2017/4/12 10:21
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class DesignLayoutTestActivity extends BaseActivity implements DesignLayoutAdapter.OnItemClickListener {

    @BindView(R.id.app_bar_image)
    ImageView appBarImage;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.rv_data)
    RecyclerView mRecyclerView;
    @BindView(R.id.nsv)
    NestedScrollView scrollView;


    @Override
    public int getContextView() {
        return R.layout.test_design_layout;
    }


    @Inject
    DesignLayoutAdapter mDesignLayoutAdapter;

    @Inject
    ApiManager mApiManager;

    private Subscription mSubscription;

    @Override
    public void initCreate(@Nullable Bundle savedInstanceState) {
        toolbar.setTitle("Tomia相册");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        setSupportActionBar(toolbar);
        RxToolbar.navigationClicks(toolbar).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                finish();
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDesignLayoutAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mDesignLayoutAdapter);
        mLoadingDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (mSubscription != null) {
                    mSubscription.cancel();
                }
            }
        });

        mApiManager.doBaiduImage(Tools.getBaiduImagesUrl("tomia", 1)).doOnSubscribe(new Consumer<Subscription>() {
            @Override
            public void accept(Subscription subscription) throws Exception {
                mSubscription = subscription;
                showLoading("正在获取数据");
            }
        }).map(new Function<ImageBaiduBean, List<ImageBaiduBean.BaiduImage>>() {
            @Override
            public List<ImageBaiduBean.BaiduImage> apply(ImageBaiduBean imageBaiduBean) throws Exception {
                return imageBaiduBean.imgs;
            }
        }).subscribe(new Consumer<List<ImageBaiduBean.BaiduImage>>() {
            @Override
            public void accept(List<ImageBaiduBean.BaiduImage> baiduImages) throws Exception {
                mLoadingDialog.dismiss();
                mDesignLayoutAdapter.addDataList(baiduImages);
                Picasso.with(getContext()).load(baiduImages.get(31).objURL)
                        .placeholder(R.mipmap.icon_placeholder)
                        .error(R.mipmap.icon_error)
                        .config(Bitmap.Config.RGB_565) //对于不透明的图片可以使用RGB_565来优化内存。RGB_565呈现结果与ARGB_8888接近
                        .into(appBarImage);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                mLoadingDialog.dismiss();
            }
        });

    }

    @Override
    public void setupActivityComponent() {
        DaggerDesignLayoutComponent.builder().appComponent(AppApplication.getAppComponent()).designLayoutModule(new DesignLayoutModule(this)).build().inject(this);
    }

    @OnClick(value = {R.id.fab})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                scrollView.smoothScrollTo(0, 0);
                break;
        }
    }


    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(this, PhotoPreviewActivity.class);
        ArrayList<PhotoPreviewBean> photoList = new ArrayList<>();
        List<ImageBaiduBean.BaiduImage> list = mDesignLayoutAdapter.getDataList();
        for (ImageBaiduBean.BaiduImage baiduImage : list) {
            PhotoPreviewBean photoPreviewBean = new PhotoPreviewBean();
            photoPreviewBean.objURL = baiduImage.objURL;
            photoPreviewBean.height = baiduImage.height;
            photoPreviewBean.width = baiduImage.width;
            photoList.add(photoPreviewBean);
        }
        intent.putExtra(PhotoPreviewActivity.PHOTO_LIST, photoList);
        intent.putExtra(PhotoPreviewActivity.CURRENT_POSITION, position);
        startActivity(intent);
    }
}
