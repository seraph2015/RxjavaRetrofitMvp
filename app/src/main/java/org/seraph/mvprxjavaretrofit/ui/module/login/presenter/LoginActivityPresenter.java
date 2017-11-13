package org.seraph.mvprxjavaretrofit.ui.module.login.presenter;

import android.content.DialogInterface;

import com.blankj.utilcode.util.ToastUtils;

import org.reactivestreams.Subscription;
import org.seraph.mvprxjavaretrofit.data.local.db.help.UserBeanHelp;
import org.seraph.mvprxjavaretrofit.data.local.db.table.UserTable;
import org.seraph.mvprxjavaretrofit.data.network.rx.RxSchedulers;
import org.seraph.mvprxjavaretrofit.data.network.service.ApiService;
import org.seraph.mvprxjavaretrofit.ui.module.login.contract.LoginActivityContract;

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
public class LoginActivityPresenter implements LoginActivityContract.Presenter {

    private LoginActivityContract.View view;

    @Override
    public void setView(LoginActivityContract.View view) {
        this.view = view;
    }

    private ApiService apiService;

    private UserBeanHelp userBeanHelp;

    @Inject
    public LoginActivityPresenter(ApiService apiService, UserBeanHelp userBeanHelp) {
        this.apiService = apiService;
        this.userBeanHelp = userBeanHelp;
    }

    @Override
    public void start() {

    }


    @Override
    public void onLogin() {
        Flowable.intervalRange(0,1,2,2 ,TimeUnit.SECONDS)
                .compose(RxSchedulers.<Long>io_main(view))
                .doOnSubscribe(new Consumer<Subscription>() {
                    @Override
                    public void accept(final Subscription subscription) throws Exception {
                        view.showLoading("正在登录").setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                subscription.cancel();
                            }
                        });
                    }
                }).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long l) throws Exception {
                view.hideLoading();
                //模拟用户信息
                UserTable userTable = new UserTable();
                userTable.setId(1);
                userTable.setNickName("小桃红");
                userTable.setToken("efewgafdfhhrehe23tryr2412");
                userTable.setHeadPortrait("http://img4.duitang.com/uploads/item/201212/14/20121214233012_iVvrQ.thumb.600_0.jpeg");
                userBeanHelp.saveUserBean(userTable);
                ToastUtils.showShort("登录成功");
                view.finish();
            }
        });

    }
}
