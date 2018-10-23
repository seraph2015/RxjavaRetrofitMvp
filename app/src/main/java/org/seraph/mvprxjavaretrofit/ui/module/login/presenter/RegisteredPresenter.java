package org.seraph.mvprxjavaretrofit.ui.module.login.presenter;

import android.content.Context;
import android.content.Intent;

import com.blankj.utilcode.util.ToastUtils;

import org.seraph.mvprxjavaretrofit.data.db.help.UserBeanHelp;
import org.seraph.mvprxjavaretrofit.data.network.rx.RxSchedulers;
import org.seraph.mvprxjavaretrofit.data.network.service.ApiService;
import org.seraph.mvprxjavaretrofit.ui.module.login.AgreementActivity;
import org.seraph.mvprxjavaretrofit.ui.module.login.contract.RegisteredContract;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * 注册逻辑
 * date：2017/10/25 10:43
 * author：Seraph
 * mail：417753393@qq.com
 **/
public class RegisteredPresenter extends RegisteredContract.Presenter {


    private ApiService apiService;

    private UserBeanHelp userBeanHelp;

    private Context context;

    @Inject
    public RegisteredPresenter(Context context, ApiService apiService, UserBeanHelp userBeanHelp) {
        this.apiService = apiService;
        this.userBeanHelp = userBeanHelp;
        this.context = context;
    }

    @Override
    public void start() {

    }


    public void doLookAgreement() {
        //《xxxx平台服务协议》
        Intent intent = new Intent(context, AgreementActivity.class);
        context.startActivity(intent);
    }

    //开始倒计时
    private void startCountdown(final int count) {
        Flowable.intervalRange(1, count, 0, 1, TimeUnit.SECONDS)
                .compose(RxSchedulers.io_main())
                .as(mView.bindLifecycle())
                .subscribe(aLong -> mView.setCountdownText(count - aLong));
    }


    public void onRegistered(String phone, String code, String password) {
        ToastUtils.showShort("注册成功");
        mView.finish();
    }

    public void onGetCode(String phone) {
        //获取验证码
        ToastUtils.showShort("发送验证码成功");
        //获取验证码成功，开始倒计时
        startCountdown(60);
    }
}
