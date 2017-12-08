package org.seraph.mvprxjavaretrofit.ui.module.login.presenter;

import android.app.Activity;
import android.content.DialogInterface;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hwangjr.rxbus.RxBus;

import org.reactivestreams.Subscription;
import org.seraph.mvprxjavaretrofit.AppConstants;
import org.seraph.mvprxjavaretrofit.data.local.db.help.UserBeanHelp;
import org.seraph.mvprxjavaretrofit.data.local.db.table.UserTable;
import org.seraph.mvprxjavaretrofit.data.network.rx.RxSchedulers;
import org.seraph.mvprxjavaretrofit.data.network.service.ApiService;
import org.seraph.mvprxjavaretrofit.ui.module.login.contract.LoginContract;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;

/**
 * 登录逻辑
 * date：2017/10/25 10:43
 * author：Seraph
 * mail：417753393@qq.com
 **/
public class LoginPresenter extends LoginContract.Presenter {

    private ApiService apiService;

    private UserBeanHelp userBeanHelp;

    @Inject
    public LoginPresenter(ApiService apiService, UserBeanHelp userBeanHelp) {
        this.apiService = apiService;
        this.userBeanHelp = userBeanHelp;
    }

    @Override
    public void start() {
        mView.setUserName(SPUtils.getInstance(AppConstants.SPAction.SP_NAME).getString(AppConstants.SPAction.USERNAME));
    }


    public void onLogin(final String phone, final String password) {
        Flowable.intervalRange(0, 1, 1, 1, TimeUnit.SECONDS)
                .compose(RxSchedulers.<Long>io_main(mView))
                .doOnSubscribe(new Consumer<Subscription>() {
                    @Override
                    public void accept(final Subscription subscription) throws Exception {
                        mView.showLoading("正在登录").setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                subscription.cancel();
                            }
                        });
                    }
                }).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long l) throws Exception {
                mView.hideLoading();
                //模拟用户信息
                UserTable userTable = new UserTable();
                userTable.setId(1);
                userTable.setNickName("小桃红");
                userTable.setToken("efewgafdfhhrehe23tryr2412");
                userTable.setHeadPortrait("http://img4.duitang.com/uploads/item/201212/14/20121214233012_iVvrQ.thumb.600_0.jpeg");
                ToastUtils.showShort("登录成功");
                //存储相关信息
                saveLoginUserAccount(phone, password);
                saveUserInfo(userTable);
                RxBus.get().post(AppConstants.RxBusAction.TAG_LOGIN, 1);
                ((Activity) mView.getContext()).onBackPressed();

            }
        });

    }


    //保存用户账号密码
    private void saveLoginUserAccount(String userName, String password) {
        SPUtils spUtils = SPUtils.getInstance(AppConstants.SPAction.SP_NAME);
        spUtils.put(AppConstants.SPAction.USERNAME, userName);
        spUtils.put(AppConstants.SPAction.PASSWORD, password);
    }


    //保存登录信息
    private void saveUserInfo(UserTable userTable) {
//        UserTable userTable = new UserTable();
//        userTable.setUserid(userBean.userid);
//        userTable.setMobile(userBean.mobile);
//        userTable.setUsername(userBean.username);
//        userTable.setUsernick(userBean.usernick);
//        userTable.setAvatar("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1822899106,40471411&fm=27&gp=0.jpg");
        userBeanHelp.saveUserBean(userTable);
    }
}
