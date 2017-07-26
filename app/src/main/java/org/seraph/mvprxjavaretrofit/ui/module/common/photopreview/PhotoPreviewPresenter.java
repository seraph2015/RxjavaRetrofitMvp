package org.seraph.mvprxjavaretrofit.ui.module.common.photopreview;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.blankj.utilcode.util.StringUtils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.reactivestreams.Subscription;
import org.seraph.mvprxjavaretrofit.utlis.Tools;

import java.io.File;
import java.util.ArrayList;

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
class PhotoPreviewPresenter implements PhotoPreviewContract.Presenter {

    private PhotoPreviewContract.View mView;

    @Override
    public void setView(PhotoPreviewContract.View view) {
        this.mView = view;
    }

    @Inject
    PhotoPreviewPresenter() {

    }


    private Subscription mSubscription;

    /**
     * 保存的图片
     */
    private PhotoPreviewBean mSavePhoto;
    /**
     * 保存的图片名称
     */
    private String saveImageName;


    private ArrayList<PhotoPreviewBean> mPhotoList;


    /**
     * 当前第几张照片
     */
    private int currentPosition = 0;

    private String imageType;

    @SuppressWarnings("unchecked")
    @Override
    public void setIntent(Intent intent) {
        if (intent == null || !intent.hasExtra(PhotoPreviewActivity.IMAGE_TYPE)) {
            throw new RuntimeException("PhotoPreviewActivity需要使用静态startPhotoPreview方法启动!");
        }
        imageType = intent.getStringExtra(PhotoPreviewActivity.IMAGE_TYPE);
        switch (imageType) {
            case PhotoPreviewActivity.IMAGE_TYPE_LOCAL:
                //处理成统一格式
                mPhotoList = initPhotoListData(intent.getStringArrayListExtra(PhotoPreviewActivity.PHOTO_LIST));
                mView.hideSaveBtn();
                break;
            case PhotoPreviewActivity.IMAGE_TYPE_NETWORK:
                mPhotoList = (ArrayList<PhotoPreviewBean>) intent.getSerializableExtra(PhotoPreviewActivity.PHOTO_LIST);
                break;
        }
        if (mPhotoList == null || mPhotoList.size() == 0) {
            mView.showToast("没有可预览的图片");
            mView.finish();
            return;
        }
        currentPosition = intent.getIntExtra(PhotoPreviewActivity.CURRENT_POSITION, 0);
    }

    /**
     * 处理成统一格式
     */
    private ArrayList<PhotoPreviewBean> initPhotoListData(ArrayList<String> mLocalPhotoList) {
        if (mLocalPhotoList == null) {
            return null;
        }
        ArrayList<PhotoPreviewBean> arrayList = new ArrayList<>();
        for (String photoPath : mLocalPhotoList) {
            PhotoPreviewBean previewBean = new PhotoPreviewBean();
            previewBean.objURL = photoPath;
            previewBean.fromType = PhotoPreviewActivity.IMAGE_TYPE_LOCAL;
            arrayList.add(previewBean);
        }
        return arrayList;
    }

    @Override
    public void start() {
        mView.setPhotoList(mPhotoList);
        //显示指定位置图片
        upDataCurrentPosition(currentPosition);
    }


    @Override
    public void upDataCurrentPosition(int position) {
        this.currentPosition = position;
        mView.showPageSelected(position, mPhotoList.size());
    }


    /**
     * 保存当前图片
     */
    @Override
    public void saveImage() {
        if (StringUtils.equals(imageType, PhotoPreviewActivity.IMAGE_TYPE_NETWORK)) {
            mSavePhoto = mPhotoList.get(currentPosition);
            mView.showLoading("正在保存");
            Picasso.with(mView.getContext()).load(mSavePhoto.objURL).into(target);
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
                    .compose(mView.<Bitmap>bindToLifecycle())
                    .flatMap(new Function<Bitmap, Flowable<String>>() {
                        @Override
                        public Flowable<String> apply(Bitmap bitmap) throws Exception {
                            saveImageName = Tools.getMD5(mSavePhoto.objURL) + "." + (StringUtils.isEmpty(mSavePhoto.type) ? "jpg" : mSavePhoto.type);
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

}
