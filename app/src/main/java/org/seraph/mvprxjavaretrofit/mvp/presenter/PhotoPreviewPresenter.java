package org.seraph.mvprxjavaretrofit.mvp.presenter;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.reactivestreams.Subscription;
import org.seraph.mvprxjavaretrofit.activity.PhotoPreviewActivity;
import org.seraph.mvprxjavaretrofit.adapter.PhotoPreviewAdapter;
import org.seraph.mvprxjavaretrofit.mvp.model.PhotoPreviewBean;
import org.seraph.mvprxjavaretrofit.mvp.view.BaseView;
import org.seraph.mvprxjavaretrofit.mvp.view.PhotoPreviewView;
import org.seraph.mvprxjavaretrofit.permission.PermissionManagement;
import org.seraph.mvprxjavaretrofit.permission.PermissionsActivity;
import org.seraph.mvprxjavaretrofit.preference.AppConstant;
import org.seraph.mvprxjavaretrofit.utlis.Tools;

import java.io.File;
import java.util.ArrayList;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * 图片预览逻辑处理
 * date：2017/2/27 14:25
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class PhotoPreviewPresenter extends BaseActivityPresenter {

    private PhotoPreviewView mView;

    private PhotoPreviewActivity mActivity;


    @Override
    public void attachView(BaseView view) {
        super.attachView(view);
        mView = (PhotoPreviewView) view;
    }

    private String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private PhotoPreviewAdapter mPhotoPreviewAdapter;

    private ArrayList<PhotoPreviewBean> mPhotoList;

    private Subscription subscription;

    /**
     * 当前第几张照片
     */
    private int currentPosition = 0;

    /**
     * 保存的图片
     */
    private PhotoPreviewBean savePhoto;
    /**
     * 保存的图片名称
     */
    private String saveImageName;

    @Override
    public void initBaseDefaultConfig() {
        super.initBaseDefaultConfig();
        mView.setStatusBarImmersionMode(true);
        mView.setBackgroundColor(Color.BLACK);
    }

    public void initData() {
        mActivity = (PhotoPreviewActivity) mView.getContext();
        mPhotoList = (ArrayList<PhotoPreviewBean>) mActivity.getIntent().getSerializableExtra(PhotoPreviewActivity.PHOTO_LIST);
        currentPosition = mActivity.getIntent().getIntExtra(PhotoPreviewActivity.CURRENT_POSITION, 0);

        mPhotoPreviewAdapter = new PhotoPreviewAdapter(mView.getContext(), mPhotoList);
        mPhotoPreviewAdapter.setOnImageClickListener(this::onImageClick);
        mView.setPagerAdapter(mPhotoPreviewAdapter);

        onPageSelected(currentPosition);
    }

    private void onImageClick(int i) {
        switchToolBarVisibility();
    }

    /**
     * 跳转到指定页
     */
    public void onPageSelected(int position) {
        this.currentPosition = position;
        mView.setTitle("图片预览" + "（" + (currentPosition + 1) + "/" + mPhotoList.size() + "）");
        mView.setCurrentItem(currentPosition);
        mView.setMenuClick();
        mView.showToolBarNavigation();
    }

    /**
     * 保存当前图片
     */
    public void saveImage() {
        savePhoto = mPhotoList.get(currentPosition);
        download();
    }


    private void download() {
        //判然系统权限
        // 缺少权限时, 进入权限配置页面
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && PermissionManagement.lacksPermissions(mView.getContext(), permissions)) {
            PermissionsActivity.startActivityForResult(mActivity, AppConstant.CODE_REQUEST_PERMISSIONS, permissions);
        } else {
            mView.showLoading("正在保存");
            Picasso.with(mView.getContext()).load(savePhoto.objURL).into(target);
        }
    }

    /**
     * 保存图片到本地
     */
    private Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            saveFileToDisk(bitmap);
        }

        private void saveFileToDisk(Bitmap bitmap) {
            Flowable.just(bitmap)
                    .subscribeOn(Schedulers.io())
                    .flatMap(mBitmap -> {
                                saveImageName = Tools.getMD5(savePhoto.objURL) + "." + savePhoto.type;
                                File dcimFile = Tools.getDCIMFile(saveImageName);
                                if (dcimFile.exists() && dcimFile.length() > 0) {
                                    return Flowable.just("图片已保存");
                                }
                                Tools.bitmapToFile(mBitmap, dcimFile);
                                return Flowable.just("保存成功");
                            }
                    )
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(s -> subscription = s)
                    .subscribe(s -> {
                        mView.hideLoading();
                        mView.showSnackBar(s);
                        // 最后通知图库更新此图片
                        Tools.scanAppImageFile(mView.getContext(), saveImageName);
                    }, e -> {
                        mView.hideLoading();
                        mView.showSnackBar("保存失败");
                    });
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
            mView.hideLoading();
            mView.showSnackBar("下载失败");
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
        }
    };


    @Override
    public void unSubscribe() {
        super.unSubscribe();
        if (subscription != null) {
            subscription.cancel();
        }


    }

    /**
     * 权限请求返回
     */
    public void onActivityResult(int requestCode) {
        if (requestCode == PermissionsActivity.PERMISSIONS_GRANTED) {
            mView.showLoading("正在保存");
            Picasso.with(mView.getContext()).load(savePhoto.objURL).into(target);
        } else {
            mView.showToast("权限请求失败，无法保存图片");
        }
    }
}
