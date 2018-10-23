package org.seraph.mvprxjavaretrofit.ui.module.main;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.StringUtils;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.databinding.ActMainFrg2Binding;
import org.seraph.mvprxjavaretrofit.databinding.ActMainFrg3ListHeadBinding;
import org.seraph.mvprxjavaretrofit.di.scope.ActivityScoped;
import org.seraph.mvprxjavaretrofit.ui.module.base.ABaseFragment;
import org.seraph.mvprxjavaretrofit.ui.module.common.photopreview.PhotoPreviewActivity;
import org.seraph.mvprxjavaretrofit.ui.module.common.photopreview.PhotoPreviewBean;
import org.seraph.mvprxjavaretrofit.ui.module.main.adapter.ImageListBaiduAdapter;
import org.seraph.mvprxjavaretrofit.ui.module.main.contract.MainTwoFragmentContract;
import org.seraph.mvprxjavaretrofit.ui.module.main.model.ImageBaiduBean;
import org.seraph.mvprxjavaretrofit.ui.module.main.presenter.MainTwoFragmentPresenter;
import org.seraph.mvprxjavaretrofit.utlis.FontUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * 第二页
 * date：2017/2/21 17:06
 * author：xiongj
 * mail：417753393@qq.com
 **/
@ActivityScoped
public class MainTwoFragment extends ABaseFragment<MainTwoFragmentContract.ABasePresenter> implements MainTwoFragmentContract.View {


    private ActMainFrg2Binding listBinding;

    private ActMainFrg3ListHeadBinding headBinding;

    @Inject
    public MainTwoFragment() {
    }

    @Override
    protected View initDataBinding(LayoutInflater inflater, ViewGroup container) {
        listBinding = DataBindingUtil.inflate(inflater, R.layout.act_main_frg2, container, false);
        return listBinding.getRoot();
    }

    @Inject
    MainTwoFragmentPresenter presenter;

    @Override
    protected MainTwoFragmentContract.ABasePresenter getMVPPresenter() {
        return presenter;
    }

    @Inject
    ImageListBaiduAdapter mAdapter;

    @Inject
    StaggeredGridLayoutManager staggeredGridLayoutManager;

    @Override
    public void initCreate(@Nullable Bundle savedInstanceState) {
        listBinding.refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                presenter.doLoadMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if (StringUtils.isEmpty(getSearchKeyWord())) {
                    refreshLayout.finishRefresh(false);
                    return;
                }
                presenter.getBaiduImageList(getSearchKeyWord(), 1);
            }
        });
        listBinding.rvImages.setLayoutManager(staggeredGridLayoutManager);
        mAdapter.bindToRecyclerView(listBinding.rvImages);
        mAdapter.addHeaderView(getHeadView());
        mAdapter.setOnItemClickListener((adapter, view, position) -> presenter.onPhotoPreview(position));

        rxBinding();
    }

    private void rxBinding() {
        RxTextView.textChanges(headBinding.etSearchKeyword)
                .as(bindLifecycle())
                .subscribe(charSequence -> {
                    if (charSequence.length() > 0) {
                        headBinding.btnSearchImage.setEnabled(true);
                    } else {
                        headBinding.btnSearchImage.setEnabled(false);
                    }
                });
    }


    @Override
    public void setTextView(CharSequence charSequence) {
        headBinding.tvCache.setText(charSequence);
    }


    @Override
    public String getSearchKeyWord() {
        return headBinding.etSearchKeyword.getText().toString().trim();
    }


    @Override
    public void setSearchInput(String item) {
        headBinding.etSearchKeyword.setText(item);
    }


    @Override
    public void startPhotoPreview(ArrayList<PhotoPreviewBean> photoList, int position) {
        PhotoPreviewActivity.startPhotoPreview(getActivity(), photoList, position, PhotoPreviewActivity.IMAGE_TYPE_NETWORK);
    }

    @Override
    public void setListDate(List<ImageBaiduBean.BaiduImage> baiduImages) {
        listBinding.refreshLayout.finishRefresh();
        listBinding.refreshLayout.finishLoadMore();
        if (baiduImages != null) {
            mAdapter.replaceData(baiduImages);
        }
    }

    private View getHeadView() {
        headBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.act_main_frg3_list_head, listBinding.rvImages, false);
        headBinding.setTwo(this);
        //加载新添加的布局字体
        FontUtils.injectFont(headBinding.getRoot());
        return headBinding.getRoot();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_get_cache:
                presenter.showCacheFilePath();
                break;
            case R.id.btn_search_history:
                presenter.searchHistory();
                break;
            case R.id.btn_search_image:
                presenter.startPicassoToImage();
                break;
        }
    }

}
