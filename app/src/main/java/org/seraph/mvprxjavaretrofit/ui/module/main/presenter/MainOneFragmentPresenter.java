package org.seraph.mvprxjavaretrofit.ui.module.main.presenter;

import android.net.Uri;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.qcloud.image.ImageClient;
import com.qcloud.image.exception.AbstractImageException;
import com.qcloud.image.request.GeneralOcrRequest;

import org.reactivestreams.Publisher;
import org.seraph.mvprxjavaretrofit.data.db.help.UserBeanHelp;
import org.seraph.mvprxjavaretrofit.data.db.table.UserTable;
import org.seraph.mvprxjavaretrofit.data.network.FileUploadHelp;
import org.seraph.mvprxjavaretrofit.data.network.rx.RxSchedulers;
import org.seraph.mvprxjavaretrofit.data.network.service.ApiService;
import org.seraph.mvprxjavaretrofit.ui.module.base.ABaseSubscriber;
import org.seraph.mvprxjavaretrofit.ui.module.base.BaseData;
import org.seraph.mvprxjavaretrofit.ui.module.base.BaseDataResponse;
import org.seraph.mvprxjavaretrofit.ui.module.main.contract.MainOneFragmentContract;
import org.seraph.mvprxjavaretrofit.ui.module.main.model.ImageClientBean;
import org.seraph.mvprxjavaretrofit.ui.module.user.UserBean;
import org.seraph.mvprxjavaretrofit.utlis.TakePhoto;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * FragmentOne逻辑处理层
 * date：2017/2/15 11:27
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class MainOneFragmentPresenter extends MainOneFragmentContract.Presenter {

    private UserBean.UserInfo mUserBean;

    private ApiService mApiService;

    private UserBeanHelp mUserBeanHelp;

    private TakePhoto mTakePhoto;

    @Inject
    MainOneFragmentPresenter(ApiService apiService, UserBeanHelp userBeanHelp, TakePhoto takePhoto) {
        this.mUserBeanHelp = userBeanHelp;
        this.mApiService = apiService;
        this.mTakePhoto = takePhoto;
    }

    private ImageClient imageClient;

    @Override
    public void start() {

        imageClient = new ImageClient("1258208797", "AKID5RdjgBrOp5B8vMy2tcMA779FG8ytZV6Z", "Tn7Y6iCWuMqEkKsgehosmXGf7cC1xtfH", ImageClient.NEW_DOMAIN_recognition_image_myqcloud_com);

    }


    public void doPickPhotoFromGallery() {

        mTakePhoto.doPickPhotoFromGallery(mView.getFragment());

    }

    //照片返回
    public void onPhotoComplete(Uri data) {
        mView.onUCropImage(data, Uri.fromFile(mTakePhoto.getNewPhotoFile()));
    }

    //图片剪切返回
    public void onUCropResult(Uri output) {
        //上传图片
        try {
            upTempImage(new File(new URI(output.toString())));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }

    //上传图片到服务器
    private void upTempImage(File file) {
        Map<String, File> fileParams = new HashMap<>();
        fileParams.put("file", file);
        mApiService.upTemp(FileUploadHelp.multipartRequestBody(null, fileParams))
                // .compose(RxSchedulers.io_main_business())
                .map(baseDataResponse -> {
                    String tempStr = "";
                    try {
                        tempStr = imageClient.generalOcr(new GeneralOcrRequest("bucketName", baseDataResponse.result.image_path));
                    } catch (AbstractImageException e) {
                        e.printStackTrace();
                    }
                    return jx(new Gson().fromJson(tempStr, ImageClientBean.class));
                }).compose(RxSchedulers.io_main())
                .doOnSubscribe(subscription -> mView.showLoading().setOnDismissListener(dialog -> subscription.cancel()))
                .as(mView.bindLifecycle())
                .subscribe(new ABaseSubscriber<String>(mView) {
                    @Override
                    public void onSuccess(String str) {
                        //得到上传后的网络地址，调用图像识别
                        LogUtils.i(str);
                        ToastUtils.showShort(str);
                    }

                    @Override
                    public void onError(String errStr) {
                        ToastUtils.showShort(errStr);
                    }
                });
    }

    public void imageStart() {
        Flowable.just("http://files.xubei.com/demon/864ba393c81f4c28b05804e24fc4f872.jpg")
                .map(s -> {
                    String tempStr = "";
                    try {
                        tempStr = imageClient.generalOcr(new GeneralOcrRequest("bucketName", s));
                    } catch (AbstractImageException e) {
                        e.printStackTrace();
                    }
                    return new Gson().fromJson(tempStr, ImageClientBean.class);
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .as(mView.bindLifecycle())
                .subscribe(imageClientBean -> {
                    LogUtils.i(jx(imageClientBean));
                });
    }

    //解析规则，如果字符匹配到百分之90则使用该字符
    private String jx(ImageClientBean imageClientBean) {
        LogUtils.i(imageClientBean.toString());
        StringBuilder sb = new StringBuilder();
        for (ImageClientBean.ItemBean itemBean : imageClientBean.data.items) {
            //获取对应一条的数据，获取单个字符
            for (ImageClientBean.WordBean wordBean : itemBean.words) {
                //如果匹配度大于百分之90，则使用
                if (wordBean.confidence >= 0.9d) {
                    sb.append(wordBean.character);
                }
            }
        }
        return sb.toString();
    }

    /**
     * test网络请求
     */
    public void doLoginTest() {
//        mApiService.login("15172311067", "123456")
//                .compose(RxSchedulers.io_main_business())
//                .doOnSubscribe(subscription -> mView.showLoading("正在登陆...").setOnDismissListener(dialog -> subscription.cancel()))
//                .as(mView.bindLifecycle())
//                .subscribe(new ABaseSubscriber<BaseDataResponse<UserBean>>(mView) {
//                    @Override
//                    public void onSuccess(BaseDataResponse<UserBean> baseDataResponse) {
//                        mUserBean = baseDataResponse.data.user;
//                        ToastUtils.showShort("登陆成功");
//                    }
//
//                    @Override
//                    public void onError(String errStr) {
//                        ToastUtils.showShort(errStr);
//                    }
//                });

    }


    /**
     * 保存用户信息
     */
    public void saveUserInfo() {
        if (mUserBean == null) {
            ToastUtils.showShort("没有可保存数据");
            return;
        }
        UserTable userTable = new UserTable();
        userTable.setId(mUserBean.id);
        userTable.setToken(mUserBean.token);
        userTable.setName(mUserBean.nickname);
        userTable.setHeadPortrait(mUserBean.headimg);
        mUserBeanHelp.save(userTable);
        ToastUtils.showShort("保存成功");
    }


    public void show() {
        mView.showLoading();
    }

    /**
     * 查询用户信息
     */
    public void queryUserInfo() {
        UserTable userBeanTable = mUserBeanHelp.getUserBean();
        if (userBeanTable != null) {
            mView.setUserTextViewValue("_id:" + userBeanTable.get_id() + "\nid:" + userBeanTable.getId() + "\ntoken:" + userBeanTable.getToken() + "\nname:" + userBeanTable.getName() + "\nheadImg:" + userBeanTable.getHeadPortrait() + "\n\n");
        } else {
            mView.setUserTextViewValue("没有保存的信息");
        }
    }

    /**
     * 清理数据
     */
    public void cleanUserInfo() {
        mUserBeanHelp.cleanUserBean();
        queryUserInfo();
    }


}
