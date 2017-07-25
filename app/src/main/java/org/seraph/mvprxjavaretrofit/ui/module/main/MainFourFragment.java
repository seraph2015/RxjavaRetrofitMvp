package org.seraph.mvprxjavaretrofit.ui.module.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import org.seraph.mvprxjavaretrofit.AppApplication;
import org.seraph.mvprxjavaretrofit.AppConfig;
import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.di.component.main.DaggerMainFragmentComponent;
import org.seraph.mvprxjavaretrofit.di.module.FragmentModule;
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

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 4
 * date：2017/2/20 16:38
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class MainFourFragment extends ABaseFragment<MainFourFragmentContract.View, MainFourFragmentContract.Presenter> implements MainFourFragmentContract.View {

    @BindView(R.id.vg_add_image_group)
    CustomImageViewGroup mAddImageView;

    @Override
    public int getContextView() {
        return R.layout.test_fragment_four;
    }

    @Inject
    MainFourFragmentPresenter mPresenter;

    @Inject
    AlertDialogUtils mAlertDialogUtils;

    @Override
    protected MainFourFragmentContract.Presenter getMVPPresenter() {
        return mPresenter;
    }


    @Override
    public void setupActivityComponent() {
        DaggerMainFragmentComponent.builder().appComponent(AppApplication.getAppComponent()).fragmentModule(new FragmentModule(this)).build().inject(this);
    }

    @Override
    public void initCreate(@Nullable Bundle savedInstanceState) {
        initListener();
        mPresenter.start();
    }

    private void initListener() {
        mAddImageView.setOnClickPicListener(new CustomImageViewGroup.OnClickPicListener() {
            @Override
            public void onPicClick(View v, int position) {
                switch ((int) v.getTag()) {
                    case 0: //add
                        mAlertDialogUtils.createHeadSelectedDialog(mAddImageView, new AlertDialogUtils.SelectedItemListener() {
                            @Override
                            public void onSelectedItem(int position) {
                                switch (position) {
                                    case 1:
                                        mPresenter.doTakePhoto();
                                        break;
                                    case 2:
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
            }
        });
        mAddImageView.setOnContentChangeListener(new CustomImageViewGroup.OnContentChangeListener() {
            @Override
            public void OnContentChanged(String path) {
                mPresenter.removePath(path);
            }
        });
    }


    @OnClick(value = {R.id.btn_design_layout,R.id.btn_upload_test})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_design_layout:
                startActivity(new Intent(mContext, DesignLayoutTestActivity.class));
                break;
            case R.id.btn_upload_test:
                mPresenter.uploadFile();
                break;
        }
    }


    @Override
    public void setImageList(ArrayList<String> imageList) {
        //设置图片
        mAddImageView.setVisibility(View.VISIBLE);
        mAddImageView.setItemPaths(imageList);
    }

    @Override
    public void startLocalImageActivity(ArrayList<String> imageList) {
        Intent intent = new Intent(mContext, LocalImageListActivity.class);
        intent.putExtra(LocalImageListActivity.SELECTED_PATH, imageList);
        startActivityForResult(intent, AppConfig.PERMISSIONS_CODE_REQUEST_1);
    }

    @Override
    public void startPhotoPreview(ArrayList<String> imageList, int position) {
        PhotoPreviewActivity.startPhotoPreview(getActivity(), imageList, position, PhotoPreviewActivity.IMAGE_TYPE_LOCAL);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case TakePhoto.CAMERA_WITH_DATA:
                    mPresenter.onCameraComplete();
                    break;
                case AppConfig.PERMISSIONS_CODE_REQUEST_1://相册
                    mPresenter.onLocalImageResult(data);
                    break;
            }

        }
    }
}
