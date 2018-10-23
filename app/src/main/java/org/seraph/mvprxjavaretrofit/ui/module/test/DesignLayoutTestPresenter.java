package org.seraph.mvprxjavaretrofit.ui.module.test;

import android.app.Activity;
import android.content.DialogInterface;

import com.blankj.utilcode.util.ToastUtils;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import org.seraph.mvprxjavaretrofit.data.network.rx.RxSchedulers;
import org.seraph.mvprxjavaretrofit.data.network.service.ApiBaiduService;
import org.seraph.mvprxjavaretrofit.ui.module.base.ABaseActivity;
import org.seraph.mvprxjavaretrofit.ui.module.main.model.ImageBaiduBean;
import org.seraph.mvprxjavaretrofit.utlis.Tools;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.Lifecycle;
import io.reactivex.disposables.Disposable;

/**
 * p层
 * date：2017/4/14 15:56
 * author：xiongj
 * mail：417753393@qq.com
 **/
class DesignLayoutTestPresenter extends DesignLayoutTestContract.Presenter {

    private ApiBaiduService mApiBaiduService;

    @Inject
    DesignLayoutTestPresenter(ApiBaiduService apiBaiduService) {
        this.mApiBaiduService = apiBaiduService;
    }

    private List<ImageBaiduBean.BaiduImage> mBaiduImages = new ArrayList<>();

    private int pageNo = 0;

    private Disposable disposable;


    @Override
    public void start() {
        mView.showLoading("正在加载").setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (disposable != null) {
                    disposable.dispose();
                }
            }
        });
        doBaiduImages(1);
    }

    public void requestNextPage() {
        doBaiduImages(++pageNo);
    }


    private void doBaiduImages(final int tempNo) {
        disposable = mApiBaiduService.doBaiduImageUrl(Tools.getBaiduImagesUrl("tomia", tempNo))
                .compose(RxSchedulers.io_main())
                .map(imageBaiduBean -> imageBaiduBean.imgs)
                .as(mView.bindLifecycle())
                .subscribe(baiduImages -> {
                    mView.hideLoading();
                    if (tempNo == 1) {
                        mBaiduImages.clear();
                    }
                    mBaiduImages.addAll(baiduImages);
                    mView.setImageListData(mBaiduImages, baiduImages.size() >= 48);
                    pageNo = tempNo;
                }, throwable -> {
                    mView.hideLoading();
                    mView.onLoadErr();
                    ToastUtils.showShort("网络异常");
                });
    }


}
