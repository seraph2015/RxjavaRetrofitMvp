package org.seraph.mvprxjavaretrofit.ui.module.common.photopreview;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.TextView;

import org.seraph.mvprxjavaretrofit.AppApplication;
import org.seraph.mvprxjavaretrofit.AppConfig;
import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.di.component.DaggerCommonComponent;
import org.seraph.mvprxjavaretrofit.di.module.CommonModule;
import org.seraph.mvprxjavaretrofit.ui.module.base.BaseActivity2;
import org.seraph.mvprxjavaretrofit.ui.module.common.permission.PermissionsActivity;
import org.seraph.mvprxjavaretrofit.ui.views.zoom.ImageViewTouchViewPager;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 图片查看器
 */
public class PhotoPreviewActivity2 extends BaseActivity2<PhotoPreviewContract.View, PhotoPreviewContract.Presenter> implements PhotoPreviewContract.View {

    @BindView(R.id.vp_photo_preview)
    ImageViewTouchViewPager vpPhotoPreview;

    @BindView(R.id.tv_progress)
    TextView tvProgress;
    @BindView(R.id.tv_save)
    TextView tvSave;


    @Override
    public int getContextView() {
        return R.layout.common_activity_photo_preview;
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

    @Inject
    PhotoPreviewAdapter mPhotoPreviewAdapter;

    @Override
    protected PhotoPreviewContract.Presenter getMVPPresenter() {
        return mPresenter;
    }

    @Override
    protected PhotoPreviewContract.View getMVPView() {
        return this;
    }

    @Override
    public void setupActivityComponent() {
        DaggerCommonComponent.builder().appComponent(AppApplication.getAppComponent()).commonModule(new CommonModule(this)).build().inject(this);
    }

    /**
     * 图片预览
     *
     * @param imageList<T>    数据源IMAGE_TYPE_NETWORK对应PhotoPreviewBean，IMAGE_TYPE_LOCAL对应String
     * @param currentPosition 当前第几个
     * @param imageType       数据类型 { PhotoPreviewActivity.IMAGE_TYPE_LOCAL, PhotoPreviewActivity.IMAGE_TYPE_NETWORK}
     */
    public static <T> void startPhotoPreview(Activity activity, ArrayList<T> imageList, int currentPosition, String imageType) {
        Intent intent = new Intent(activity, PhotoPreviewActivity2.class);
        intent.putExtra(IMAGE_TYPE, imageType);
        intent.putExtra(PHOTO_LIST, imageList);
        intent.putExtra(CURRENT_POSITION, currentPosition);
        activity.startActivity(intent);
    }

    @Override
    public void initCreate(@Nullable Bundle savedInstanceState) {
        initListener();
        initViewPager();
        mPresenter.setIntent(getIntent());
        mPresenter.start();
    }




    private void initViewPager() {
        vpPhotoPreview.setOnPageSelectedListener(new ImageViewTouchViewPager.OnPageSelectedListener() {
            @Override
            public void onPageSelected(int position) {
                mPresenter.upDataCurrentPosition(position);
            }
        });
        vpPhotoPreview.setOffscreenPageLimit(5);
        mPhotoPreviewAdapter.setOnImageClickListener(new PhotoPreviewAdapter.OnImageClickListener() {
            @Override
            public void onImageClick(int position) {
                //关闭当前界面
                finish();
            }
        });
        vpPhotoPreview.setAdapter(mPhotoPreviewAdapter);
    }

    private void initListener() {
        mLoadingDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mPresenter.unSubscribe();
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void startPermissionsActivity(String[] permissions) {
        PermissionsActivity.startActivityForResult(this, AppConfig.PERMISSIONS_CODE_REQUEST_1, permissions);
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
        tvProgress.setText((position + 1) + "/" + size);
        vpPhotoPreview.setCurrentItem(position);
    }

    @Override
    public void hideSaveBtn() {
        tvSave.setVisibility(View.GONE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConfig.PERMISSIONS_CODE_REQUEST_1) {
            mPresenter.onActivityResult(resultCode);
        }
    }


    @OnClick(R.id.tv_save)
    public void onViewClicked() {
        mPresenter.saveImage();
    }
}
