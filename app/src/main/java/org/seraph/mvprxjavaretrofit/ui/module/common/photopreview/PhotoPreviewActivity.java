package org.seraph.mvprxjavaretrofit.ui.module.common.photopreview;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.jakewharton.rxbinding2.view.RxView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.databinding.CommonActivityPhotoPreviewBinding;
import org.seraph.mvprxjavaretrofit.ui.module.base.ABaseActivity;
import org.seraph.mvprxjavaretrofit.ui.views.zoom.ImageViewTouchViewPager;

import java.util.ArrayList;
import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;


/**
 * 图片查看器
 */
public class PhotoPreviewActivity extends ABaseActivity<PhotoPreviewContract.Presenter> implements PhotoPreviewContract.View {


    CommonActivityPhotoPreviewBinding binding;

    @Override
    protected void initContextView() {
        binding = DataBindingUtil.setContentView(this, R.layout.common_activity_photo_preview);
    }


    public static final String IMAGE_TYPE = "image_type";

    public static final String IMAGE_TYPE_LOCAL = "image_type_local";

    public static final String IMAGE_TYPE_NETWORK = "image_type_network";

    /**
     * 图片列表数据
     */
    public final static String PHOTO_LIST = "photoList";
    /**
     * 当前选中的图片
     */
    public final static String CURRENT_POSITION = "currentPosition";

    @Inject
    PhotoPreviewPresenter mPresenter;

    private PhotoPreviewAdapter mPhotoPreviewAdapter;

    @Inject
    RxPermissions rxPermissions;

    @Override
    protected PhotoPreviewContract.Presenter getMVPPresenter() {
        return mPresenter;
    }

    /**
     * 多图片预览
     *
     * @param imageList<T>    数据源IMAGE_TYPE_NETWORK对应PhotoPreviewBean，IMAGE_TYPE_LOCAL对应String
     * @param currentPosition 当前第几个（从0开始)
     * @param imageType       数据类型 { PhotoPreviewActivity.IMAGE_TYPE_LOCAL, PhotoPreviewActivity.IMAGE_TYPE_NETWORK}
     */
    public static <T> void startPhotoPreview(Activity activity, ArrayList<T> imageList, int currentPosition, String imageType) {
        Intent intent = new Intent(activity, PhotoPreviewActivity.class);
        intent.putExtra(IMAGE_TYPE, imageType);
        intent.putExtra(PHOTO_LIST, imageList);
        intent.putExtra(CURRENT_POSITION, currentPosition);
        activity.startActivity(intent);
    }

    /**
     * 多图片预览
     *
     * @param imageList<T> 数据源IMAGE_TYPE_NETWORK对应PhotoPreviewBean，IMAGE_TYPE_LOCAL对应String
     * @param imageType    数据类型 { PhotoPreviewActivity.IMAGE_TYPE_LOCAL, PhotoPreviewActivity.IMAGE_TYPE_NETWORK}
     */
    public static <T> void startPhotoPreview(Activity activity, ArrayList<T> imageList, String imageType) {
        startPhotoPreview(activity, imageList, 0, imageType);
    }

    /**
     * 单张图片预览
     * 网络地址图片使用PhotoPreviewBean，本地使用String
     *
     * @param image 数据源IMAGE_TYPE_NETWORK对应PhotoPreviewBean，IMAGE_TYPE_LOCAL对应String
     */
    public static <T> void startPhotoPreview(Activity activity, T image) {
        String imageType = PhotoPreviewActivity.IMAGE_TYPE_NETWORK;
        if (image instanceof PhotoPreviewBean) {
            imageType = PhotoPreviewActivity.IMAGE_TYPE_NETWORK;
        } else if (image instanceof String) {
            imageType = PhotoPreviewActivity.IMAGE_TYPE_LOCAL;
        }
        ArrayList<T> imageList = new ArrayList<>();
        imageList.add(image);
        startPhotoPreview(activity, imageList, 0, imageType);
    }


    @Override
    public void initCreate(@Nullable Bundle savedInstanceState) {
        initViewPager();
        initRxBinding();
        mPresenter.setIntent(getIntent());
        mPresenter.start();
    }

    private void initRxBinding() {
        //点击保存按钮先检查权限
        RxView.clicks(binding.llSave)
                .compose(rxPermissions.ensure(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE))
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            //获取权限成功
                            mPresenter.saveImage();
                        } else {
                            //获取权限失败
                            ToastUtils.showShort("缺少SD卡权限，保存图片失败");
                        }
                    }
                });

    }


    private void initViewPager() {
        binding.vpPhotoPreview.setOnPageSelectedListener(new ImageViewTouchViewPager.OnPageSelectedListener() {
            @Override
            public void onPageSelected(int position) {
                mPresenter.upDataCurrentPosition(position);
            }
        });
        binding.vpPhotoPreview.setOffscreenPageLimit(5);
        mPhotoPreviewAdapter = new PhotoPreviewAdapter(this);
        mPhotoPreviewAdapter.setOnImageClickListener(new PhotoPreviewAdapter.OnImageClickListener() {
            @Override
            public void onImageClick(int position) {
                //关闭当前界面
                finish();
            }
        });
        binding.vpPhotoPreview.setAdapter(mPhotoPreviewAdapter);
    }


    @Override
    public void setPhotoList(ArrayList<PhotoPreviewBean> mPhotoList) {
        mPhotoPreviewAdapter.setListData(mPhotoList);
    }

    /**
     * 跳转到指定页
     */
    @Override
    public void showPageSelected(int position, int size) {
        binding.tvProgress.setText(String.format(Locale.getDefault(), "%d/%d", position + 1, size));
        binding.vpPhotoPreview.setCurrentItem(position);
    }

    @Override
    public void hideSaveBtn() {
        binding.llSave.setVisibility(View.GONE);
    }


}
