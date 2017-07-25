package org.seraph.mvprxjavaretrofit.ui.module.main.presenter;

import android.content.Intent;
import android.net.Uri;

import org.reactivestreams.Subscription;
import org.seraph.mvprxjavaretrofit.data.network.FileUploadHelp;
import org.seraph.mvprxjavaretrofit.data.network.RxSchedulers;
import org.seraph.mvprxjavaretrofit.data.network.service.ApiService;
import org.seraph.mvprxjavaretrofit.ui.module.base.ABaseNetWorkSubscriber;
import org.seraph.mvprxjavaretrofit.ui.module.base.BaseDataResponse;
import org.seraph.mvprxjavaretrofit.ui.module.common.photolist.LocalImageListActivity;
import org.seraph.mvprxjavaretrofit.ui.module.main.contract.MainFourFragmentContract;
import org.seraph.mvprxjavaretrofit.utlis.TakePhoto;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * FragmentFpur逻辑处理层
 * date：2017/2/15 11:27
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class MainFourFragmentPresenter implements MainFourFragmentContract.Presenter {

    private MainFourFragmentContract.View mView;

    private TakePhoto mTakePhoto;
    private ApiService mApiService;

    private Subscription mSubscription;

    @Inject
    MainFourFragmentPresenter(ApiService apiService, TakePhoto takePhoto) {
        mTakePhoto = takePhoto;
        mApiService = apiService;
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
        if (mSubscription != null) {
            mSubscription.cancel();
        }
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

    @Override
    public void uploadFile() {
        if (imageList.size() == 0) {
            mView.showToast("请先选择需要上传的图片");
            return;
        }

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("token", "bb5a732c24e707069a50a7731ef6eb1a7358bc6bd826196e93fe7ebca9b9fe0c");
        hashMap.put("type", "4");
        hashMap.put("title", "发帖测试");
        hashMap.put("content", "发帖正文");

        ArrayList<File> arrayList = new ArrayList<>();
        for (String fileUrl : imageList) {
            arrayList.add(new File(fileUrl));
        }

        mApiService.multipart("post/addPost", FileUploadHelp.multipartRequestBody(hashMap, arrayList, "file"))
                .compose(RxSchedulers.<BaseDataResponse>io_main())
                .doOnSubscribe(new Consumer<Subscription>() {
                    @Override
                    public void accept(@NonNull Subscription subscription) throws Exception {
                        mView.showLoading("正在上传图片");
                        mSubscription = subscription;
                    }
                }).subscribe(new ABaseNetWorkSubscriber<BaseDataResponse>(mView) {
            @Override
            public void onSuccess(BaseDataResponse stringBaseDataResponse) {
                if (stringBaseDataResponse.status == 0) {
                    mView.showToast("上传成功");
                } else {
                    mView.showToast(stringBaseDataResponse.msg);
                }
            }

            @Override
            public void onError(String errStr) {
                mView.showLoading(errStr);
            }
        });
    }
}
