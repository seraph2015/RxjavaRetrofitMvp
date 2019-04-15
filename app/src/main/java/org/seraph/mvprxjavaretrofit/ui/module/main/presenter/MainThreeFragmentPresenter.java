package org.seraph.mvprxjavaretrofit.ui.module.main.presenter;

import com.blankj.utilcode.util.ToastUtils;

import org.seraph.mvprxjavaretrofit.data.network.exception.ServerErrorCode;
import org.seraph.mvprxjavaretrofit.data.network.rx.RxSchedulers;
import org.seraph.mvprxjavaretrofit.data.network.service.Api12306Service;
import org.seraph.mvprxjavaretrofit.ui.module.base.ABaseSubscriber;
import org.seraph.mvprxjavaretrofit.ui.module.main.contract.MainThreeFragmentContract;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import zlc.season.rxdownload3.RxDownload;
import zlc.season.rxdownload3.core.Mission;

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
        mApi12306Service.do12306Url()
                .compose(RxSchedulers.io_main())
                .doOnSubscribe(subscription -> mView.showLoading("正在访问").setOnDismissListener(dialog -> subscription.cancel()))
                .as(mView.bindLifecycle())
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

//        Mission mission = new Mission("http://speed.myzone.cn/WindowsXP_SP2.exe", "WindowsXP_SP2.exe", SAVE_PATH, IS_OVERRIDE)
//        disposable = RxDownload.INSTANCE.create(mission, false).compose(RxSchedulers.io_main()).as(mView.bindLifecycle()).subscribe(status -> {
//
//        });
//        RxDownload.INSTANCE.start(mission).as(mView.bindLifecycle()).subscribe();
//        RxDownload.INSTANCE.stop(mission).as(mView.bindLifecycle()).subscribe();
    }

    public void cancelDownload() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
