package org.seraph.mvprxjavaretrofit.ui.module.login.presenter;

import com.blankj.utilcode.util.ToastUtils;
import com.hwangjr.rxbus.RxBus;

import org.seraph.mvprxjavaretrofit.AppConstants;
import org.seraph.mvprxjavaretrofit.data.db.help.UserBeanHelp;
import org.seraph.mvprxjavaretrofit.data.network.rx.RxSchedulers;
import org.seraph.mvprxjavaretrofit.data.network.service.ApiService;
import org.seraph.mvprxjavaretrofit.ui.module.login.contract.ResetPasswordContract;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * 验证手机号逻辑
 * date：2017/10/25 10:43
 * author：Seraph
 * mail：417753393@qq.com
 **/
public class ResetPasswordPresenter extends ResetPasswordContract.Presenter {


    private ApiService apiService;

    private UserBeanHelp userBeanHelp;

    @Inject
    public ResetPasswordPresenter(ApiService apiService, UserBeanHelp userBeanHelp) {
        this.apiService = apiService;
        this.userBeanHelp = userBeanHelp;
    }

    @Override
    public void start() {

    }

    //开始倒计时
    private void startCountdown(final int count) {
        Flowable.intervalRange(1, count, 0, 1, TimeUnit.SECONDS)
                .compose(RxSchedulers.io_main(mView))
                .subscribe(aLong -> mView.setCountdownText(count - aLong));
    }

    public void onGetCode(String phone) {
        //获取验证码
        ToastUtils.showShort("发送验证码成功");
        //获取验证码成功，开始倒计时
        startCountdown(60);
    }

    public void onSetPassword(String phone, String code, String password) {
        ToastUtils.showShort("密码重置成功");
        //如果用户不为空，则更新Userid
        userBeanHelp.cleanUserBean();
        RxBus.get().post(AppConstants.RxBusAction.TAG_LOGIN, 1);
        mView.finish();
    }
}
