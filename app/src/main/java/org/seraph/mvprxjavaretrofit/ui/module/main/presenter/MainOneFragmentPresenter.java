package org.seraph.mvprxjavaretrofit.ui.module.main.presenter;

import com.blankj.utilcode.util.ToastUtils;

import org.seraph.mvprxjavaretrofit.data.db.help.UserBeanHelp;
import org.seraph.mvprxjavaretrofit.data.db.table.UserTable;
import org.seraph.mvprxjavaretrofit.data.network.rx.RxSchedulers;
import org.seraph.mvprxjavaretrofit.data.network.service.ApiService;
import org.seraph.mvprxjavaretrofit.ui.module.base.ABaseSubscriber;
import org.seraph.mvprxjavaretrofit.ui.module.main.contract.MainOneFragmentContract;
import org.seraph.mvprxjavaretrofit.ui.module.user.UserBean;

import javax.inject.Inject;

/**
 * FragmentOne逻辑处理层
 * date：2017/2/15 11:27
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class MainOneFragmentPresenter extends MainOneFragmentContract.Presenter {

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


    /**
     * test网络请求
     */
    public void doLoginTest() {
        mApiService.login("15172311067", "123456")
                .compose(RxSchedulers.io_main_business(mView))
                .doOnSubscribe(subscription-> mView.showLoading("正在登陆...").setOnDismissListener(dialog-> subscription.cancel()))
                .subscribe(new ABaseSubscriber<UserBean>(mView) {
                    @Override
                    public void onSuccess(UserBean userBean) {
                        mUserBean = userBean.user;
                        ToastUtils.showShort("登陆成功");
                    }

                    @Override
                    public void onError(String errStr) {
                        ToastUtils.showShort(errStr);
                    }
                });

    }


    /**
     * 保存用户信息
     */
    public void saveUserInfo() {
        if (mUserBean == null) {
            ToastUtils.showShort("没有可保存数据");
            return;
        }
        UserTable userTable = new UserTable();
        userTable.setId(mUserBean.id);
        userTable.setToken(mUserBean.token);
        userTable.setName(mUserBean.nickname);
        userTable.setHeadPortrait(mUserBean.headimg);
        mUserBeanHelp.save(userTable);
        ToastUtils.showShort("保存成功");
    }

    /**
     * 查询用户信息
     */
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
    public void cleanUserInfo() {
        mUserBeanHelp.cleanUserBean();
        queryUserInfo();
    }


}
