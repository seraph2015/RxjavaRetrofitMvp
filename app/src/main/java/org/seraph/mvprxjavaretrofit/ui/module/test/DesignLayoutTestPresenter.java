package org.seraph.mvprxjavaretrofit.ui.module.test;

import org.reactivestreams.Subscription;
import org.seraph.mvprxjavaretrofit.data.network.ApiManager;
import org.seraph.mvprxjavaretrofit.ui.module.main.ImageBaiduBean;
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

    private ApiManager mApiManager;

    private List<ImageBaiduBean.BaiduImage> mBaiduImages = new ArrayList<>();

    private int pageNo = 0;

    @Inject
    public DesignLayoutTestPresenter(ApiManager mApiManager) {
        this.mApiManager = mApiManager;
    }

    @Override
    public void start() {
        doBaiduImages(1);
    }

    @Override
    public void requestNextPage() {
        doBaiduImages(++pageNo);
    }

    @Override
    public void requestRefresh() {
        doBaiduImages(++pageNo);
    }

    private void doBaiduImages(final int tempNo) {
        mApiManager.doBaiduImage(Tools.getBaiduImagesUrl("tomia", tempNo)).doOnSubscribe(new Consumer<Subscription>() {
            @Override
            public void accept(Subscription subscription) throws Exception {
                mSubscription = subscription;
                mView.showLoading("正在获取数据");
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
            }
        });
    }


    @Override
    public void unSubscription() {
        if (mSubscription != null) {
            mSubscription.cancel();
        }
    }

}
