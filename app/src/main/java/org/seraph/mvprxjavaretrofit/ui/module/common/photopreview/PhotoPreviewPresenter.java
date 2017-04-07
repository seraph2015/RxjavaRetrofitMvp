package org.seraph.mvprxjavaretrofit.ui.module.common.photopreview;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.reactivestreams.Subscription;
import org.seraph.mvprxjavaretrofit.ui.module.common.permission.PermissionManagement;
import org.seraph.mvprxjavaretrofit.ui.module.common.permission.PermissionsActivity;
import org.seraph.mvprxjavaretrofit.utlis.Tools;

import java.io.File;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


/**
 * 图片预览逻辑处理
 * date：2017/2/27 14:25
 * author：xiongj
 * mail：417753393@qq.com
 **/
class PhotoPreviewPresenter implements PhotoPreviewAdapter.OnImageClickListener, PhotoPreviewContract.Presenter {

    private PhotoPreviewContract.View mView;

    @Override
    public void setView(PhotoPreviewContract.View view) {
        this.mView = view;
    }

    @Inject
    PhotoPreviewPresenter() {

    }

    private String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};


    private Subscription mSubscription;

    /**
     * 保存的图片
     */
    private PhotoPreviewBean savePhoto;
    /**
     * 保存的图片名称
     */
    private String saveImageName;


    @Override
    public void start() {
        PhotoPreviewAdapter mPhotoPreviewAdapter = new PhotoPreviewAdapter(mView.getContext(), mView.getPhotoList());
        mPhotoPreviewAdapter.setOnImageClickListener(this);
        mView.setPagerAdapter(mPhotoPreviewAdapter);
        mView.onPageSelected(mView.getCurrentPosition());
    }

    @Override
    public void onImageClick(int i) {
        mView.switchToolBarVisibility();
    }


    /**
     * 保存当前图片
     */
    @Override
    public void saveImage() {
        savePhoto = mView.getPhotoList().get(mView.getCurrentPosition());
        download();
    }


    private void download() {
        //判然系统权限
        // 缺少权限时, 进入权限配置页面
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && PermissionManagement.lacksPermissions(mView.getContext(), permissions)) {
            mView.startPermissionsActivity(permissions);
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
                    .flatMap(new Function<Bitmap, Flowable<String>>() {
                        @Override
                        public Flowable<String> apply(Bitmap bitmap) throws Exception {
                            saveImageName = Tools.getMD5(savePhoto.objURL) + "." + savePhoto.type;
                            File dcimFile = Tools.getDCIMFile(saveImageName);
                            if (dcimFile.exists() && dcimFile.length() > 0) {
                                return Flowable.just("图片已保存");
                            }
                            Tools.bitmapToFile(bitmap, dcimFile);
                            return Flowable.just("保存成功");
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(new Consumer<Subscription>() {
                        @Override
                        public void accept(Subscription subscription) throws Exception {
                            mSubscription = subscription;
                        }
                    })
                    .subscribe(new Consumer<String>() {
                        @Override
                        public void accept(String s) throws Exception {
                            mView.hideLoading();
                            mView.showToast(s);
                            // 最后通知图库更新此图片
                            Tools.scanAppImageFile(mView.getContext(), saveImageName);
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            mView.hideLoading();
                            mView.showToast("保存失败");
                        }
                    });

        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
            mView.hideLoading();
            mView.showToast("保存失败");
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
        }
    };


    @Override
    public void unSubscribe() {
        if (mSubscription != null) {
            mSubscription.cancel();
        }

    }

    /**
     * 权限请求返回
     */
    @Override
    public void onActivityResult(int requestCode) {
        if (requestCode == PermissionsActivity.PERMISSIONS_GRANTED) {
            mView.showLoading("正在保存");
            Picasso.with(mView.getContext()).load(savePhoto.objURL).into(target);
        } else {
            mView.showToast("权限请求失败，无法保存图片");
        }
    }


}
