package org.seraph.mvprxjavaretrofit.mvp.presenter;

import org.reactivestreams.Subscription;
import org.seraph.mvprxjavaretrofit.mvp.model.UserBean;
import org.seraph.mvprxjavaretrofit.mvp.view.BaseView;
import org.seraph.mvprxjavaretrofit.mvp.view.MainView;
import org.seraph.mvprxjavaretrofit.request.ApiService;
import org.seraph.mvprxjavaretrofit.request.exception.ServerErrorCode;

/**
 * mian逻辑处理层
 * date：2017/2/15 11:27
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class MainPresenter extends BasePresenter {

    private MainView mainView;

    private Subscription subscriber;

    private boolean isToolBarShow = true;

    @Override
    public void attachView(BaseView mView) {
        super.attachView(mView);
        this.mainView = (MainView) mView;
    }

    public void getNetWork() {
        ApiService.doLogin("15623088767", "123456").doOnSubscribe(subscription -> {
            this.subscriber = subscription;
            mainView.showLoading();
        }).subscribe(baseResponse -> {
            mainView.hideLoading();
            UserBean userBean = baseResponse.data.data;
            mainView.setTextViewValue("token->" + baseResponse.data.token + "\nnickName->" + userBean.nickName + "\nheadImg->" + userBean.headImg);
        }, e -> ServerErrorCode.errorCodeToMessageShow(e, mainView));
    }

    @Override
    public void unSubscribe() {
        super.unSubscribe();
        if (subscriber != null) {
            subscriber.cancel();
        }
    }

    /**
     * 切换状态栏是否显示
     */
    public void switchToolBarVisibility() {
        if (isToolBarShow) {
            mainView.hideToolBar();
        } else {
            mainView.showToolBar();
        }
        this.isToolBarShow = !isToolBarShow;
    }
}
