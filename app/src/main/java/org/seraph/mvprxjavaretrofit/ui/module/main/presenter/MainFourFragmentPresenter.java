package org.seraph.mvprxjavaretrofit.ui.module.main.presenter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;

import org.seraph.mvprxjavaretrofit.data.network.service.ApiService;
import org.seraph.mvprxjavaretrofit.ui.module.common.photolist.LocalImageListActivity;
import org.seraph.mvprxjavaretrofit.ui.module.main.contract.MainFourFragmentContract;
import org.seraph.mvprxjavaretrofit.utlis.TakePhoto;
import org.seraph.mvprxjavaretrofit.utlis.Tools;

import java.io.File;
import java.util.ArrayList;

import javax.inject.Inject;

/**
 * FragmentFpur逻辑处理层
 * date：2017/2/15 11:27
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class MainFourFragmentPresenter extends MainFourFragmentContract.Presenter {


    private TakePhoto mTakePhoto;
    private ApiService mApiService;

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


    public void doTakePhoto() {
        mTakePhoto.doTakePhoto(mView.getFragment());
    }

    public void doSelectedLocalImage() {
        //选择图片
        mView.startLocalImageActivity(imageList);
    }

    public void removePath(String path) {
        if (imageList.contains(path)) {
            imageList.remove(path);
        }
    }

    public void onLocalImageResult(Intent data) {
        imageList.clear();
        imageList.addAll(data.getStringArrayListExtra(LocalImageListActivity.SELECT_PATH));
        mView.setImageList(imageList);
    }

    public void photoPreview(int position) {
        mView.startPhotoPreview(imageList, position);
    }

    public void onCameraComplete() {
        if (mTakePhoto.getCurrentPhotoFile() == null) {
            ToastUtils.showShort("拍照异常");
            return;
        }
        Uri photoUri = Uri.fromFile(mTakePhoto.getCurrentPhotoFile());
        imageList.add(photoUri.getPath());
        mView.setImageList(imageList);
    }

    public void uploadFile() {
        if (imageList.size() == 0) {
            ToastUtils.showShort("请先选择需要上传的图片");
            return;
        }

//        HashMap<String, String> hashMap = new HashMap<>();
//        hashMap.put("token", "a280d150b5592cf560925a6eebf56145");
//        hashMap.put("rec_id", "1165");
//        hashMap.put("hide_username", "0");
//        hashMap.put("content", "测试");
//        hashMap.put("goods_rank", "3");
//        hashMap.put("serve_rank", "3");
//        hashMap.put("express_rank", "3");
//        hashMap.put("comment_rank", "3");
//
//        HashMap<String, File> fileMap = new HashMap<>();
//        for (int i = 0; i < (imageList.size() > 4 ? 4 : imageList.size()); i++) {
//            fileMap.put("image["+i+"]", new File(imageList.get(i)));
//        }
//
//        mApiService.multipart("?service=goods.addComment", FileUploadHelp.multipartRequestBody(hashMap, fileMap))
//                .compose(RxSchedulers.<BaseDataResponse>io_main())
//                .doOnSubscribe(new Consumer<Subscription>() {
//                    @Override
//                    public void accept(@NonNull final Subscription subscription) throws Exception {
//                        mView.showLoading("正在上传图片").setOnDismissListener(new DialogInterface.OnDismissListener() {
//                            @Override
//                            public void onDismiss(DialogInterface dialog) {
//                                subscription.cancel();
//                            }
//                        });
//                    }
//                }).subscribe(new ABaseSubscriber<BaseDataResponse>(mView) {
//            @Override
//            public void onSuccess(BaseDataResponse stringBaseDataResponse) {
//                if (stringBaseDataResponse.status == 0) {
//                    ToastUtils.showShort("上传成功");
//                } else {
//                    ToastUtils.showShort(stringBaseDataResponse.msg);
//                }
//            }
//
//            @Override
//            public void onError(String errStr) {
//                mView.showLoading(errStr);
//            }
//        });
    }

    public void onSaveInstanceState(Bundle outState) {
        //如果拍照路径不为空，则保存。
        if (mTakePhoto.getCurrentPhotoFile() != null) {
            outState.putStringArrayList("imageList", imageList);
            outState.putString("photoFile", mTakePhoto.getCurrentPhotoFile().getAbsolutePath());
        }
    }

    public void onViewStateRestored(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            return;
        }
        LogUtils.i("MainFourFragment->onViewStateRestored", savedInstanceState);
        String photoPath = savedInstanceState.getString("photoFile");
        imageList = savedInstanceState.getStringArrayList("imageList");
        if (!Tools.isNull(photoPath)) {
            mTakePhoto.setmCurrentPhotoFile(new File(photoPath));
        }
    }


}
