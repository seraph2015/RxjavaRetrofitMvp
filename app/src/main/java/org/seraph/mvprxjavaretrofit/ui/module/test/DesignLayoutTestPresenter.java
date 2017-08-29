package org.seraph.mvprxjavaretrofit.ui.module.test;

import android.content.DialogInterface;

import com.blankj.utilcode.util.ToastUtils;

import org.seraph.mvprxjavaretrofit.data.network.rx.RxDisposableHelp;
import org.seraph.mvprxjavaretrofit.data.network.rx.RxSchedulers;
import org.seraph.mvprxjavaretrofit.data.network.service.ApiBaiduService;
import org.seraph.mvprxjavaretrofit.ui.module.main.model.ImageBaiduBean;
import org.seraph.mvprxjavaretrofit.utlis.Tools;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
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

    @Override
    public void requestNextPage() {
        doBaiduImages(++pageNo);
    }


    private void doBaiduImages(final int tempNo) {
        disposable = mApiBaiduService.doBaiduImageUrl(Tools.getBaiduImagesUrl("tomia", tempNo))
                .compose(RxSchedulers.<ImageBaiduBean>io_main(mView))
                .map(new Function<ImageBaiduBean, List<ImageBaiduBean.BaiduImage>>() {
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
                        mView.setImageListData(mBaiduImages, baiduImages.size() >= 48);
                        pageNo = tempNo;
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.hideLoading();
                        mView.onLoadErr();
                        ToastUtils.showShortToast("网络异常");
                    }
                });
    }


}
