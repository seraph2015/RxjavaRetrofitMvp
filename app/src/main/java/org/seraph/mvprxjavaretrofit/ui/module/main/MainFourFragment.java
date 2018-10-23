package org.seraph.mvprxjavaretrofit.ui.module.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.databinding.ActMainFrg4Binding;
import org.seraph.mvprxjavaretrofit.di.scope.ActivityScoped;
import org.seraph.mvprxjavaretrofit.ui.module.base.ABaseFragment;
import org.seraph.mvprxjavaretrofit.ui.module.common.photolist.LocalImageListActivity;
import org.seraph.mvprxjavaretrofit.ui.module.common.photopreview.PhotoPreviewActivity;
import org.seraph.mvprxjavaretrofit.ui.module.main.contract.MainFourFragmentContract;
import org.seraph.mvprxjavaretrofit.ui.module.main.presenter.MainFourFragmentPresenter;
import org.seraph.mvprxjavaretrofit.ui.module.test.DesignLayoutTestActivity;
import org.seraph.mvprxjavaretrofit.utlis.AlertDialogUtils;
import org.seraph.mvprxjavaretrofit.utlis.TakePhoto;

import java.util.ArrayList;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;


/**
 * 4
 * date：2017/2/20 16:38
 * author：xiongj
 * mail：417753393@qq.com
 **/
@ActivityScoped
public class MainFourFragment extends ABaseFragment<MainFourFragmentContract.Presenter> implements MainFourFragmentContract.View {

    @Inject
    public MainFourFragment() {
    }

    private ActMainFrg4Binding binding;


    @Inject
    MainFourFragmentPresenter presenter;

    @Override
    protected MainFourFragmentContract.Presenter getMVPPresenter() {
        return presenter;
    }


    @Override
    protected View initDataBinding(LayoutInflater inflater, ViewGroup container) {
        binding = DataBindingUtil.inflate(inflater, R.layout.act_main_frg4, container, false);
        binding.setFragment(this);
        return binding.getRoot();
    }

    @Inject
    AlertDialogUtils mAlertDialogUtils;


    private static final int CODE_REQUEST = 1000;

    @Override
    public void initCreate(@Nullable Bundle savedInstanceState) {
        initListener();
        presenter.start();
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
                                    presenter.doTakePhoto();
                                    break;
                                case 2://本地相册
                                    presenter.doSelectedLocalImage();
                                    break;
                            }
                        }
                    });
                    break;
                case 1: //点击
                    presenter.photoPreview(position);
                    break;
            }
        });
        binding.vgAddImageGroup.setOnContentChangeListener(path -> presenter.removePath(path));
    }


    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_design_layout:
                startActivity(new Intent(getContext(), DesignLayoutTestActivity.class));
                break;
            case R.id.btn_upload_test:
                presenter.uploadFile();
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
                    presenter.onCameraComplete();
                    break;
                case CODE_REQUEST://相册
                    presenter.onLocalImageResult(data);
                    break;
            }

        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        presenter.onSaveInstanceState(outState);
    }


    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        presenter.onViewStateRestored(savedInstanceState);
    }
}
