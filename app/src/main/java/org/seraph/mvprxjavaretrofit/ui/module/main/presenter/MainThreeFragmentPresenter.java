package org.seraph.mvprxjavaretrofit.ui.module.main.presenter;

import android.content.DialogInterface;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;

import org.reactivestreams.Subscription;
import org.seraph.mvprxjavaretrofit.data.network.exception.ServerErrorCode;
import org.seraph.mvprxjavaretrofit.data.network.rx.RxSchedulers;
import org.seraph.mvprxjavaretrofit.data.network.service.Api12306Service;
import org.seraph.mvprxjavaretrofit.ui.module.base.ABaseSubscriber;
import org.seraph.mvprxjavaretrofit.ui.module.main.contract.MainThreeFragmentContract;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import zlc.season.rxdownload2.RxDownload;
import zlc.season.rxdownload2.entity.DownloadStatus;

/**
 * FragmentThee逻辑处理层
 * date：2017/2/15 11:27
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class MainThreeFragmentPresenter extends MainThreeFragmentContract.Presenter {


    private Api12306Service mApi12306Service;

    @Inject
    MainThreeFragmentPresenter(Api12306Service api12306Service) {
        mApi12306Service = api12306Service;
    }


    @Override
    public void start() {

    }


    public void post12306Https() {
        mApi12306Service.do12306Url().compose(RxSchedulers.io_main(mView))
                .doOnSubscribe(subscription -> mView.showLoading("正在访问").setOnDismissListener(dialog -> subscription.cancel()))
                .subscribe(new ABaseSubscriber<String>(mView) {
                    @Override
                    public void onSuccess(String s) {

                    }

                    @Override
                    public void onError(String errStr) {
                        if (ServerErrorCode.MALFORMED_JSON_EXCEPTION.equals(errStr)) {
                            mView.setTextView("访问成功");
                        } else {
                            mView.setTextView(errStr);
                        }
                    }
                });

    }


    private Disposable disposable;

    public void startDownload() {
//        disposable = Flowable.interval(0, 40, TimeUnit.MILLISECONDS)
//                .compose(RxSchedulers.<Long>io_main(mView)).subscribe(new Consumer<Long>() {
//                    @Override
//                    public void accept(Long o) throws Exception {
//                        LogUtils.i(o);
//                        mView.setDownloadProgressRate(o);
//                        if (o == 2500) {
//                            disposable.dispose();
//                        }
//                    }
//                });
//        RxDownload.getInstance(mView.getContext()).download("http://speed.myzone.cn/WindowsXP_SP2.exe")
//                .compose(RxSchedulers.<DownloadStatus>io_main_o(mView))
//                .subscribe(new Observer<DownloadStatus>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        disposable = d;
//                    }
//
//                    @Override
//                    public void onNext(DownloadStatus downloadStatus) {
//                        mView.setDownloadProgressRate(downloadStatus.getDownloadSize(), downloadStatus.getTotalSize());
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        ToastUtils.showShort(e.getMessage());
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        ToastUtils.showShort("下载完成");
//                    }
//                });
    }

    public void cancelDownload() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
