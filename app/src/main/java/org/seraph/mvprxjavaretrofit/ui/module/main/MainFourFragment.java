package org.seraph.mvprxjavaretrofit.ui.module.main;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.databinding.TestFragmentFourBinding;
import org.seraph.mvprxjavaretrofit.di.scope.ActivityScoped;
import org.seraph.mvprxjavaretrofit.ui.module.base.ABaseFragment;
import org.seraph.mvprxjavaretrofit.ui.module.common.photolist.LocalImageListActivity;
import org.seraph.mvprxjavaretrofit.ui.module.common.photopreview.PhotoPreviewActivity;
import org.seraph.mvprxjavaretrofit.ui.module.main.contract.MainFourFragmentContract;
import org.seraph.mvprxjavaretrofit.ui.module.main.presenter.MainFourFragmentPresenter;
import org.seraph.mvprxjavaretrofit.ui.module.test.DesignLayoutTestActivity;
import org.seraph.mvprxjavaretrofit.ui.views.addImage.CustomImageViewGroup;
import org.seraph.mvprxjavaretrofit.utlis.AlertDialogUtils;
import org.seraph.mvprxjavaretrofit.utlis.TakePhoto;

import java.util.ArrayList;

import javax.inject.Inject;


/**
 * 4
 * date：2017/2/20 16:38
 * author：xiongj
 * mail：417753393@qq.com
 **/
@ActivityScoped
public class MainFourFragment extends ABaseFragment<MainFourFragmentContract.Presenter> implements MainFourFragmentContract.View {


    TestFragmentFourBinding binding;

    @Inject
    MainFourFragmentPresenter mPresenter;

    @Inject
    AlertDialogUtils mAlertDialogUtils;


    @Inject
    public MainFourFragment() {
    }

    @Override
    protected MainFourFragmentContract.Presenter getMVPPresenter() {
        return mPresenter;
    }


    @Override
    protected View initDataBinding(LayoutInflater inflater, ViewGroup container) {
        binding = DataBindingUtil.inflate(inflater, R.layout.test_fragment_four, container, false);
        binding.setFragment(this);
        return binding.getRoot();
    }


    private static final int CODE_REQUEST = 1000;

    @Override
    public void initCreate(@Nullable Bundle savedInstanceState) {
        initListener();
        mPresenter.start();
    }

    private void initListener() {
        binding.vgAddImageGroup.setOnClickPicListener((v, position) -> {
            switch ((int) v.getTag()) {
                case 0: //add
                    mAlertDialogUtils.createHeadSelectedDialog(binding.vgAddImageGroup, new AlertDialogUtils.SelectedItemListener() {
                        @Override
                        public void onSelectedItem(int position) {
                            switch (position) {
                                case 1:
                                    mPresenter.doTakePhoto();
                                    break;
                                case 2://本地相册
                                    mPresenter.doSelectedLocalImage();
                                    break;
                            }
                        }
                    });
                    break;
                case 1: //点击
                    mPresenter.photoPreview(position);
                    break;
            }
        });
        binding.vgAddImageGroup.setOnContentChangeListener(path -> mPresenter.removePath(path));
    }


    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_design_layout:
                startActivity(new Intent(getContext(), DesignLayoutTestActivity.class));
                break;
            case R.id.btn_upload_test:
                mPresenter.uploadFile();
                break;
        }
    }


    @Override
    public void setImageList(ArrayList<String> imageList) {
        //设置图片
        binding.vgAddImageGroup.setVisibility(View.VISIBLE);
        binding.vgAddImageGroup.setItemPaths(imageList);
    }

    @Override
    public void startLocalImageActivity(ArrayList<String> imageList) {
        Intent intent = new Intent(getContext(), LocalImageListActivity.class);
        intent.putExtra(LocalImageListActivity.SELECTED_PATH, imageList);
        startActivityForResult(intent, CODE_REQUEST);
    }

    @Override
    public void startPhotoPreview(ArrayList<String> imageList, int position) {
        PhotoPreviewActivity.startPhotoPreview(getActivity(), imageList, position, PhotoPreviewActivity.IMAGE_TYPE_LOCAL);
    }

    @Override
    public Fragment getFragment() {
        return this;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case TakePhoto.CAMERA_WITH_DATA:
                    mPresenter.onCameraComplete();
                    break;
                case CODE_REQUEST://相册
                    mPresenter.onLocalImageResult(data);
                    break;
            }

        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mPresenter.onSaveInstanceState(outState);
    }


    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        mPresenter.onViewStateRestored(savedInstanceState);
    }
}
