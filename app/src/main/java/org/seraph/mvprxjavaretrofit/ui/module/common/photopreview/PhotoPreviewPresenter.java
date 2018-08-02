package org.seraph.mvprxjavaretrofit.ui.module.common.photopreview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.seraph.mvprxjavaretrofit.data.network.ImageLoad.glide.GlideApp;
import org.seraph.mvprxjavaretrofit.data.network.rx.RxSchedulers;
import org.seraph.mvprxjavaretrofit.utlis.Tools;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;


/**
 * 图片预览逻辑处理
 * date：2017/2/27 14:25
 * author：xiongj
 * mail：417753393@qq.com
 **/
class PhotoPreviewPresenter extends PhotoPreviewContract.Presenter {

    private Context context;

    @Inject
    PhotoPreviewPresenter(Context context) {
        this.context = context;
    }

    private Disposable mDisposable;

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
            ToastUtils.showShort("没有可预览的图片");
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


    public void upDataCurrentPosition(int position) {
        this.currentPosition = position;
        mView.showPageSelected(position, mPhotoList.size());
    }


    /**
     * 保存当前图片
     */
    public void saveImage() {
        if (!StringUtils.equals(imageType, PhotoPreviewActivity.IMAGE_TYPE_NETWORK)) {
            return;
        }
        mSavePhoto = mPhotoList.get(currentPosition);
        mView.showLoading("正在保存").setOnDismissListener(dialog -> {
            if (mDisposable != null) {
                mDisposable.dispose();
            }
        });
        //此方法需要在主线程里(限制最大的宽为1080px,防止图片过大，保存oom)
        // Picasso.with(mView.getContext()).load(mSavePhoto.objURL).transform(new PicassoZoomTransformation(1080)).into(target);
        GlideApp.with(context).asBitmap().load(mSavePhoto.objURL).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                //保存bitmap
                mDisposable = saveImageToDisk(resource);
            }
        });
    }


    //注意：Target 不能直接new 出来。因为Picasso 里面持有Target 用的是弱引用，要是直接new 就有很大可能被GC回收导致接收不到回调。
    private Target target = new Target() {
        @Override
        public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
            mDisposable = saveImageToDisk(bitmap);
        }


        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
            ToastUtils.showShort("保存失败");
            mView.hideLoading();
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
        }
    };

    /**
     * 保存到磁盘
     */
    private Disposable saveImageToDisk(final Bitmap bitmap) {
        //使用子线程进行保存
        return Flowable.create((@NonNull FlowableEmitter<String> e) -> {
            String saveImageName = EncryptUtils.encryptMD5ToString(mSavePhoto.objURL) + "." + (StringUtils.isEmpty(mSavePhoto.type) ? "jpg" : mSavePhoto.type);
            File dcimFile = Tools.getDCIMFile(saveImageName);
            if (dcimFile != null && dcimFile.exists() && dcimFile.length() > 0) {
                e.onNext("图片已保存");
                e.onComplete();
            }
            try {
                Tools.bitmapToFile(bitmap, dcimFile);
                // 最后通知图库更新此图片
                Tools.scanAppImageFile(context, saveImageName);
                e.onNext("保存成功");
                e.onComplete();
            } catch (IOException e1) {
                e.onError(e1);
            }
        }, BackpressureStrategy.BUFFER)
                .compose(RxSchedulers.io_main(mView))
                .subscribe(s -> {
                    ToastUtils.showShort(s);
                    mView.hideLoading();
                }, throwable -> {
                    throwable.printStackTrace();
                    ToastUtils.showShort("保存失败");
                    mView.hideLoading();
                });
    }
}
