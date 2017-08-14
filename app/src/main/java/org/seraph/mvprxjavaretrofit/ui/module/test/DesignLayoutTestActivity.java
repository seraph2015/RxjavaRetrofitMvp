package org.seraph.mvprxjavaretrofit.ui.module.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.data.network.picasso.PicassoTool;
import org.seraph.mvprxjavaretrofit.di.component.DaggerDesignLayoutComponent;
import org.seraph.mvprxjavaretrofit.di.component.base.AppComponent;
import org.seraph.mvprxjavaretrofit.di.module.DesignLayoutModule;
import org.seraph.mvprxjavaretrofit.di.module.base.ActivityModule;
import org.seraph.mvprxjavaretrofit.ui.module.base.ABaseActivity;
import org.seraph.mvprxjavaretrofit.ui.module.common.photopreview.PhotoPreviewActivity;
import org.seraph.mvprxjavaretrofit.ui.module.common.photopreview.PhotoPreviewBean;
import org.seraph.mvprxjavaretrofit.ui.module.main.model.ImageBaiduBean;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Design风格布局效果测试
 * date：2017/4/12 10:21
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class DesignLayoutTestActivity extends ABaseActivity<DesignLayoutTestContract.View, DesignLayoutTestContract.Presenter> implements DesignLayoutTestContract.View {

    @BindView(R.id.app_bar_image)
    ImageView appBarImage;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.rv_data)
    RecyclerView mRecyclerView;

    @Override
    public int getContextView() {
        return R.layout.test_design_layout;
    }

    @Inject
    DesignLayoutTestPresenter mPresenter;

    @Override
    protected DesignLayoutTestContract.Presenter getMVPPresenter() {
        return mPresenter;
    }

    @Inject
    LinearLayoutManager layoutManager;

    @Inject
    DesignLayoutAdapter mDesignLayoutAdapter;

    @Override
    public void setupActivityComponent(AppComponent appComponent, ActivityModule activityModule) {
        DaggerDesignLayoutComponent.builder()
                .appComponent(appComponent)
                .activityModule(activityModule)
                .designLayoutModule(new DesignLayoutModule())
                .build()
                .inject(this);
    }

    @Override
    public void initCreate(@Nullable Bundle savedInstanceState) {
        toolbar.setTitle("Tomia相册");
        initListener();
        mPresenter.start();
    }

    /**
     * 初始化加载更多
     */
    public void initListener() {
        mRecyclerView.setLayoutManager(layoutManager);
        mDesignLayoutAdapter.bindToRecyclerView(mRecyclerView);
        mDesignLayoutAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startPhotoPreview(position);
            }
        });
        mDesignLayoutAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mPresenter.requestNextPage();
            }
        }, mRecyclerView);
    }


    @OnClick(value = {R.id.fab})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                if (layoutManager.findFirstVisibleItemPosition() > 5) {
                    mRecyclerView.scrollToPosition(5);
                }
                mRecyclerView.smoothScrollToPosition(0);
                break;
        }
    }


    private void startPhotoPreview(int position) {
        ArrayList<PhotoPreviewBean> photoList = new ArrayList<>();
        List<ImageBaiduBean.BaiduImage> list = mDesignLayoutAdapter.getData();
        for (ImageBaiduBean.BaiduImage baiduImage : list) {
            PhotoPreviewBean photoPreviewBean = new PhotoPreviewBean();
            photoPreviewBean.objURL = baiduImage.objURL;
            photoPreviewBean.height = baiduImage.height;
            photoPreviewBean.width = baiduImage.width;
            photoPreviewBean.type = baiduImage.type;
            photoList.add(photoPreviewBean);
        }
        PhotoPreviewActivity.startPhotoPreview(this, photoList, position, PhotoPreviewActivity.IMAGE_TYPE_NETWORK);
    }

    @Override
    public void setImageListData(List<ImageBaiduBean.BaiduImage> baiduImages, boolean isMore) {
        PicassoTool.loadNoCache(this, baiduImages.get((int) (Math.random() * baiduImages.size())).objURL, appBarImage);
        mDesignLayoutAdapter.replaceData(baiduImages);
        if (isMore) {
            mDesignLayoutAdapter.loadMoreComplete();
        } else {
            mDesignLayoutAdapter.loadMoreEnd();
        }
    }

    @Override
    public void onLoadErr() {
        mDesignLayoutAdapter.loadMoreFail();
    }

}
