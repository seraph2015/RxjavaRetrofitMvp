package org.seraph.mvprxjavaretrofit.ui.module.common.photopreview;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.seraph.mvprxjavaretrofit.data.network.ImageLoad.picasso.PicassoZoomTransformation;
import org.seraph.mvprxjavaretrofit.data.network.rx.RxSchedulers;
import org.seraph.mvprxjavaretrofit.utlis.Tools;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


/**
 * 图片预览逻辑处理
 * date：2017/2/27 14:25
 * author：xiongj
 * mail：417753393@qq.com
 **/
class PhotoPreviewPresenter extends PhotoPreviewContract.Presenter {


    @Inject
    PhotoPreviewPresenter(){
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
        mView.showLoading("正在保存").setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (mDisposable != null) {
                    mDisposable.dispose();
                }
            }
        });
        //此方法需要在主线程里(限制最大的宽为1080px,防止图片过大，保存oom)
        Picasso.with(mView.getContext()).load(mSavePhoto.objURL).transform(new PicassoZoomTransformation(1080)).into(target);
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
        return Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull FlowableEmitter<String> e) throws Exception {
                String saveImageName = EncryptUtils.encryptMD5ToString(mSavePhoto.objURL) + "." + (StringUtils.isEmpty(mSavePhoto.type) ? "jpg" : mSavePhoto.type);
                File dcimFile = Tools.getDCIMFile(saveImageName);
                if (dcimFile != null && dcimFile.exists() && dcimFile.length() > 0) {
                    e.onNext("图片已保存");
                    e.onComplete();
                }
                try {
                    Tools.bitmapToFile(bitmap, dcimFile);
                    // 最后通知图库更新此图片
                    Tools.scanAppImageFile(mView.getContext(), saveImageName);
                    e.onNext("保存成功");
                    e.onComplete();
                } catch (IOException e1) {
                    e.onError(e1);
                }
            }
        }, BackpressureStrategy.BUFFER)
                .compose(RxSchedulers.<String>io_main(mView))
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        ToastUtils.showShort(s);
                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                        ToastUtils.showShort("保存失败");
                        mView.hideLoading();
                    }
                });
    }
}
