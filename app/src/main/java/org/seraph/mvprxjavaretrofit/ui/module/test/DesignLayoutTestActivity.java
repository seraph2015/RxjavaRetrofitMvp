package org.seraph.mvprxjavaretrofit.ui.module.test;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.View;

import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.data.network.glide.GlideApp;
import org.seraph.mvprxjavaretrofit.databinding.TestDesignLayoutBinding;
import org.seraph.mvprxjavaretrofit.ui.module.base.ABaseActivity;
import org.seraph.mvprxjavaretrofit.ui.module.common.photopreview.PhotoPreviewActivity;
import org.seraph.mvprxjavaretrofit.ui.module.common.photopreview.PhotoPreviewBean;
import org.seraph.mvprxjavaretrofit.ui.module.main.model.ImageBaiduBean;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Design风格布局效果测试
 * date：2017/4/12 10:21
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class DesignLayoutTestActivity extends ABaseActivity<DesignLayoutTestContract.Presenter> implements DesignLayoutTestContract.View {


    TestDesignLayoutBinding binding;

    @Override
    protected void initContextView() {
        binding = DataBindingUtil.setContentView(this, R.layout.test_design_layout);
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
    public void initCreate(@Nullable Bundle savedInstanceState) {
        initListener();
        mPresenter.start();
    }

    /**
     * 初始化加载更多
     */
    public void initListener() {
        binding.rvData.setLayoutManager(layoutManager);
        mDesignLayoutAdapter.bindToRecyclerView(binding.rvData);
        mDesignLayoutAdapter.setOnItemClickListener((adapter, view, position) -> startPhotoPreview(position));
        mDesignLayoutAdapter.setOnLoadMoreListener(() -> mPresenter.requestNextPage(), binding.rvData);
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                if (layoutManager.findFirstVisibleItemPosition() > 5) {
                    binding.rvData.scrollToPosition(5);
                }
                binding.rvData.smoothScrollToPosition(0);
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
        GlideApp.with(this).load(baiduImages.get((int) (Math.random() * baiduImages.size())).objURL).into(binding.appBarImage);
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
