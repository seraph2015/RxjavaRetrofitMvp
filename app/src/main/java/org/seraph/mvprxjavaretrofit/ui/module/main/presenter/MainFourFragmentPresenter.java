package org.seraph.mvprxjavaretrofit.ui.module.main.presenter;

import android.content.Intent;
import android.net.Uri;

import org.seraph.mvprxjavaretrofit.ui.module.common.photolist.LocalImageListActivity;
import org.seraph.mvprxjavaretrofit.ui.module.main.contract.MainFourFragmentContract;
import org.seraph.mvprxjavaretrofit.utlis.TakePhoto;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * FragmentFpur逻辑处理层
 * date：2017/2/15 11:27
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class MainFourFragmentPresenter implements MainFourFragmentContract.Presenter {

    private MainFourFragmentContract.View mView;

    private TakePhoto mTakePhoto;

    @Inject
    MainFourFragmentPresenter(TakePhoto takePhoto) {
        mTakePhoto = takePhoto;
    }

    /**
     * 选择的数据源
     */
    private ArrayList<String> imageList = new ArrayList<>();

    @Override
    public void start() {

    }

    @Override
    public void unSubscribe() {

    }

    @Override
    public void setView(MainFourFragmentContract.View view) {
        this.mView = view;
    }

    @Override
    public void doTakePhoto() {
        mTakePhoto.doTakePhoto();
    }

    @Override
    public void doSelectedLocalImage() {
        //选择图片
        mView.startLocalImageActivity(imageList);
    }

    @Override
    public void removePath(String path) {
        if (imageList.contains(path)) {
            imageList.remove(path);
        }
    }

    @Override
    public void onLocalImageResult(Intent data) {
        imageList.clear();
        imageList.addAll(data.getStringArrayListExtra(LocalImageListActivity.SELECT_PATH));
        mView.setImageList(imageList);
    }

    @Override
    public void photoPreview(int position) {
        mView.startPhotoPreview(imageList, position);
    }

    @Override
    public void onCameraComplete() {
        Uri photoUri = Uri.fromFile(mTakePhoto.getCurrentPhotoFile());
        imageList.add(photoUri.getPath());
        mView.setImageList(imageList);
    }
}
