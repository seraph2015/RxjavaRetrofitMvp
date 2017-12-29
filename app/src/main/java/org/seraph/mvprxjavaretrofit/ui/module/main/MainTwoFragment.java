package org.seraph.mvprxjavaretrofit.ui.module.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jakewharton.rxbinding2.widget.RxTextView;

import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.databinding.TestFragmentTwoBinding;
import org.seraph.mvprxjavaretrofit.databinding.TestFragmentTwoListHeadBinding;
import org.seraph.mvprxjavaretrofit.di.component.MainActivityComponent;
import org.seraph.mvprxjavaretrofit.ui.module.base.ABaseFragment;
import org.seraph.mvprxjavaretrofit.ui.module.common.photopreview.PhotoPreviewActivity;
import org.seraph.mvprxjavaretrofit.ui.module.common.photopreview.PhotoPreviewBean;
import org.seraph.mvprxjavaretrofit.ui.module.main.contract.MainTwoFragmentContract;
import org.seraph.mvprxjavaretrofit.ui.module.main.presenter.MainTwoFragmentPresenter;
import org.seraph.mvprxjavaretrofit.utlis.FontUtils;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * 第二页
 * date：2017/2/21 17:06
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class MainTwoFragment extends ABaseFragment<MainTwoFragmentContract.Presenter> implements MainTwoFragmentContract.View {


    TestFragmentTwoBinding listBinding;
    TestFragmentTwoListHeadBinding headBinding;

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


    @Override
    public void setupActivityComponent() {
        getComponent(MainActivityComponent.class).inject(this);
    }

    @Override
    public void initCreate(@Nullable Bundle savedInstanceState) {
        mPresenter.start();
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
    public RecyclerView getRecyclerView() {
        return listBinding.rvImages;
    }

    @Override
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
