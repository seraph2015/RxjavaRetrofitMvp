package org.seraph.mvprxjavaretrofit.ui.module.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.databinding.TestFragmentTwoBinding;
import org.seraph.mvprxjavaretrofit.databinding.TestFragmentTwoListHeadBinding;
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

import io.reactivex.functions.Consumer;

/**
 * 第二页
 * date：2017/2/21 17:06
 * author：xiongj
 * mail：417753393@qq.com
 **/
@ActivityScoped
public class MainTwoFragment extends ABaseFragment<MainTwoFragmentContract.Presenter> implements MainTwoFragmentContract.View {


    TestFragmentTwoBinding listBinding;
    TestFragmentTwoListHeadBinding headBinding;

    @Inject
    public MainTwoFragment(){}

    @Override
    protected View initDataBinding(LayoutInflater inflater, ViewGroup container) {
        listBinding = DataBindingUtil.inflate(inflater, R.layout.test_fragment_two, container, false);
        return listBinding.getRoot();
    }

    @Inject
    MainTwoFragmentPresenter mPresenter;

    @Override
    protected MainTwoFragmentContract.Presenter getMVPPresenter() {
        return mPresenter;
    }

    @Inject
    ImageListBaiduAdapter mAdapter;

    @Inject
    StaggeredGridLayoutManager staggeredGridLayoutManager;

    @Override
    public void initCreate(@Nullable Bundle savedInstanceState) {
        listBinding.refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                mPresenter.doLoadMore();
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                if (StringUtils.isEmpty(getSearchKeyWord())) {
                    refreshLayout.finishRefresh(false);
                    return;
                }
                mPresenter.getBaiduImageList(getSearchKeyWord(), 1);
            }
        });
        listBinding.rvImages.setLayoutManager(staggeredGridLayoutManager);
        mAdapter.bindToRecyclerView(listBinding.rvImages);
        mAdapter.addHeaderView(getHeadView());
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mPresenter.onPhotoPreview(position);
            }
        });

        rxBinding();
    }

    protected void rxBinding() {
        RxTextView.textChanges(headBinding.etSearchKeyword).subscribe(new Consumer<CharSequence>() {
            @Override
            public void accept(CharSequence charSequence) throws Exception {
                if (charSequence.length() > 0) {
                    headBinding.btnSearchImage.setEnabled(true);
                } else {
                    headBinding.btnSearchImage.setEnabled(false);
                }
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

    public View getHeadView() {
        headBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.test_fragment_two_list_head, listBinding.rvImages, false);
        headBinding.setTwo(this);
        //加载新添加的布局字体
        FontUtils.injectFont(headBinding.getRoot());
        return headBinding.getRoot();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_get_cache:
                mPresenter.showCacheFilePath();
                break;
            case R.id.btn_search_history:
                mPresenter.searchHistory();
                break;
            case R.id.btn_search_image:
                mPresenter.startPicassoToImage();
                break;
        }
    }

}
