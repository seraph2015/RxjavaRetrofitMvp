package org.seraph.mvprxjavaretrofit.mvp.presenter;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.reactivestreams.Subscription;
import org.seraph.mvprxjavaretrofit.activity.PhotoPreviewActivity;
import org.seraph.mvprxjavaretrofit.adapter.PhotoPreviewAdapter;
import org.seraph.mvprxjavaretrofit.mvp.model.PhotoPreviewBean;
import org.seraph.mvprxjavaretrofit.mvp.view.BaseView;
import org.seraph.mvprxjavaretrofit.mvp.view.PhotoPreviewView;
import org.seraph.mvprxjavaretrofit.preference.AppConstant;
import org.seraph.mvprxjavaretrofit.utlis.Tools;

import java.io.File;
import java.io.FileOutputStream;
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

    private PhotoPreviewAdapter mPhotoPreviewAdapter;

    private ArrayList<PhotoPreviewBean> mPhotoList;

    public Subscription subscription;

    /**
     * 当前第几张照片
     */
    private int currentPosition = 0;

    private PhotoPreviewBean savePhoto;

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
        mView.showLoading("正在保存");
        Picasso.with(mView.getContext()).load(savePhoto.objURL).into(target);
    }

    private Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            saveFileToDisk(bitmap);
        }

        private void saveFileToDisk(Bitmap bitmap) {
            Flowable.just(bitmap)
                    .subscribeOn(Schedulers.io())
                    .flatMap(mBitmap -> {
                                String imageName = Tools.getMD5(savePhoto.objURL) + "." + savePhoto.type;
                                File dcimFile = Tools.getDCIMFile(AppConstant.SAVE_FOLDER_NAME, imageName);
                                if (dcimFile.exists() && dcimFile.length() > 0) {
                                    return Flowable.just("图片已存在");
                                }
                                FileOutputStream outputStream = new FileOutputStream(dcimFile);
                                mBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                                outputStream.flush();
                                outputStream.close();
                                return Flowable.just("保存成功");
                            }
                    )
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(s -> subscription = s)
                    .subscribe(s -> {
                        mView.hideLoading();
                        mView.showSnackBar(s);
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
        subscription.cancel();
    }
}
