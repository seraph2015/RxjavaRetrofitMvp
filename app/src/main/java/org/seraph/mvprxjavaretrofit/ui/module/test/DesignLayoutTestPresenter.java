package org.seraph.mvprxjavaretrofit.ui.module.test;

import org.reactivestreams.Subscription;
import org.seraph.mvprxjavaretrofit.data.network.RxSchedulers;
import org.seraph.mvprxjavaretrofit.data.network.service.ApiBaiduService;
import org.seraph.mvprxjavaretrofit.ui.module.main.model.ImageBaiduBean;
import org.seraph.mvprxjavaretrofit.utlis.Tools;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * p层
 * date：2017/4/14 15:56
 * author：xiongj
 * mail：417753393@qq.com
 **/
class DesignLayoutTestPresenter implements DesignLayoutTestContract.Presenter {

    private DesignLayoutTestContract.View mView;

    @Override
    public void setView(DesignLayoutTestContract.View view) {
        this.mView = view;
    }

    private Subscription mSubscription;

    private ApiBaiduService mApiManager;

    private List<ImageBaiduBean.BaiduImage> mBaiduImages = new ArrayList<>();

    private int pageNo = 0;

    @Inject
    DesignLayoutTestPresenter(ApiBaiduService mApiManager) {
        this.mApiManager = mApiManager;
    }

    @Override
    public void start() {
        mView.showLoading("正在请求相册数据");
        requestRefresh();
    }

    @Override
    public void unSubscribe() {
        if (mSubscription != null) {
            mSubscription.cancel();
        }
    }

    @Override
    public void requestNextPage() {
        doBaiduImages(++pageNo);
    }

    @Override
    public void requestRefresh() {
        doBaiduImages(1);
    }

    private void doBaiduImages(final int tempNo) {
        mApiManager.doBaiduImageUrl(Tools.getBaiduImagesUrl("tomia", tempNo))
                .compose(mView.<ImageBaiduBean>bindToLifecycle())
                .compose(RxSchedulers.<ImageBaiduBean>io_main())
                .doOnSubscribe(new Consumer<Subscription>() {
            @Override
            public void accept(Subscription subscription) throws Exception {
                mSubscription = subscription;
            }
        }).map(new Function<ImageBaiduBean, List<ImageBaiduBean.BaiduImage>>() {
            @Override
            public List<ImageBaiduBean.BaiduImage> apply(ImageBaiduBean imageBaiduBean) throws Exception {
                return imageBaiduBean.imgs;
            }
        }).subscribe(new Consumer<List<ImageBaiduBean.BaiduImage>>() {

            @Override
            public void accept(List<ImageBaiduBean.BaiduImage> baiduImages) throws Exception {
                mView.hideLoading();
                if (tempNo == 1) {
                    mBaiduImages.clear();
                }
                mBaiduImages.addAll(baiduImages);
                mView.setImageListData(mBaiduImages, baiduImages.size() == 48);
                pageNo = tempNo;
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                mView.hideLoading();
                mView.showToast("网络异常");
            }
        });
    }


}
