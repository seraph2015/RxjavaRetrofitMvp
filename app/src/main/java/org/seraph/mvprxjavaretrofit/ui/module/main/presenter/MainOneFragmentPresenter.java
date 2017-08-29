package org.seraph.mvprxjavaretrofit.ui.module.main.presenter;

import android.content.DialogInterface;

import com.blankj.utilcode.util.ToastUtils;

import org.reactivestreams.Subscription;
import org.seraph.mvprxjavaretrofit.data.local.db.help.UserBeanHelp;
import org.seraph.mvprxjavaretrofit.data.local.db.table.UserTable;
import org.seraph.mvprxjavaretrofit.data.network.rx.RxSchedulers;
import org.seraph.mvprxjavaretrofit.data.network.service.ApiService;
import org.seraph.mvprxjavaretrofit.ui.module.base.ABaseSubscriber;
import org.seraph.mvprxjavaretrofit.ui.module.main.contract.MainOneFragmentContract;
import org.seraph.mvprxjavaretrofit.ui.module.user.UserBean;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * FragmentOne逻辑处理层
 * date：2017/2/15 11:27
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class MainOneFragmentPresenter implements MainOneFragmentContract.Presenter {

    private MainOneFragmentContract.View mView;

    private UserBean.UserInfo mUserBean;

    private ApiService mApiService;

    private UserBeanHelp mUserBeanHelp;

    @Inject
    MainOneFragmentPresenter(ApiService apiService, UserBeanHelp userBeanHelp) {
        this.mUserBeanHelp = userBeanHelp;
        this.mApiService = apiService;
    }

    @Override
    public void start() {

    }

    @Override
    public void setView(MainOneFragmentContract.View view) {
        this.mView = view;
    }


    /**
     * test网络请求
     */
    @Override
    public void doLoginTest() {
        mApiService.login("15172311067", "123456")
                .compose(RxSchedulers.<UserBean>io_main_business(mView))
                .doOnSubscribe(new Consumer<Subscription>() {
                    @Override
                    public void accept(final Subscription subscription) throws Exception {
                        mView.showLoading("正在登陆...").setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                subscription.cancel();
                            }
                        });
                    }
                })
                .subscribe(new ABaseSubscriber<UserBean>(mView) {
                    @Override
                    public void onSuccess(UserBean userBean) {
                        mUserBean = userBean.user;
                        ToastUtils.showShortToast("登陆成功");
                    }

                    @Override
                    public void onError(String errStr) {
                        ToastUtils.showShortToast(errStr);
                    }
                });

    }


    /**
     * 保存用户信息
     */
    @Override
    public void saveUserInfo() {
        if (mUserBean == null) {
            ToastUtils.showShortToast("没有可保存数据");
            return;
        }
        UserTable userTable = new UserTable();
        userTable.setId(mUserBean.id);
        userTable.setToken(mUserBean.token);
        userTable.setName(mUserBean.nickname);
        userTable.setHeadPortrait(mUserBean.headimg);
        mUserBeanHelp.save(userTable);
        ToastUtils.showShortToast("保存成功");
    }

    /**
     * 查询用户信息
     */
    @Override
    public void queryUserInfo() {
        UserTable userBeanTable = mUserBeanHelp.getUserBean();
        if (userBeanTable != null) {
            mView.setUserTextViewValue("_id:" + userBeanTable.get_id() + "\nid:" + userBeanTable.getId() + "\ntoken:" + userBeanTable.getToken() + "\nname:" + userBeanTable.getName() + "\nheadImg:" + userBeanTable.getHeadPortrait() + "\n\n");
        } else {
            mView.setUserTextViewValue("没有保存的信息");
        }
    }

    /**
     * 清理数据
     */
    @Override
    public void cleanUserInfo() {
        mUserBeanHelp.cleanUserBean();
        queryUserInfo();
    }


}
