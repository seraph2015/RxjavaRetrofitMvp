package org.seraph.mvprxjavaretrofit.ui.module.main.presenter;

import org.reactivestreams.Subscription;
import org.seraph.mvprxjavaretrofit.data.local.db.gen.DaoSession;
import org.seraph.mvprxjavaretrofit.data.local.db.gen.UserTableDao;
import org.seraph.mvprxjavaretrofit.data.local.db.table.UserTable;
import org.seraph.mvprxjavaretrofit.data.network.RxSchedulers;
import org.seraph.mvprxjavaretrofit.data.network.service.ApiService;
import org.seraph.mvprxjavaretrofit.ui.module.base.ABaseNetWorkSubscriber;
import org.seraph.mvprxjavaretrofit.ui.module.base.BaseDataResponse;
import org.seraph.mvprxjavaretrofit.ui.module.main.contract.MainOneFragmentContract;
import org.seraph.mvprxjavaretrofit.ui.module.user.UserBean;

import java.util.List;

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

    private Subscription mSubscription;

    private UserBean mUserBean;

    private ApiService mApiService;

    private DaoSession mDaoSession;

    @Inject
    MainOneFragmentPresenter(ApiService apiService, DaoSession daoSession) {
        this.mDaoSession = daoSession;
        this.mApiService = apiService;
    }

    @Override
    public void start() {

    }

    @Override
    public void unSubscribe() {
        if (mSubscription != null) {
            mSubscription.cancel();
        }
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
                .compose(mView.<BaseDataResponse<UserBean>>bindToLifecycle())
                .compose(RxSchedulers.<UserBean>io_main_business())
                .doOnSubscribe(new Consumer<Subscription>() {
                    @Override
                    public void accept(Subscription subscription) throws Exception {
                        mSubscription = subscription;
                        mView.showLoading("正在登陆...");
                    }
                })
                .subscribe(new ABaseNetWorkSubscriber<UserBean>(mView) {
                    @Override
                    public void onSuccess(UserBean userBean) {
                        mUserBean = userBean;
                        mView.showToast("登陆成功");
                    }

                    @Override
                    public void onError(String errStr) {
                        mView.showToast(errStr);
                    }
                });

    }


    /**
     * 保存用户信息
     */
    @Override
    public void saveUserInfo() {
        if (mUserBean == null) {
            mView.showToast("没有可保存数据");
            return;
        }
        UserTable userTable = new UserTable();
        userTable.setId(mUserBean.id);
        userTable.setToken(mUserBean.token);
        userTable.setName(mUserBean.user_nicename);
        userTable.setHeadPortrait(mUserBean.avatar);
        mDaoSession.getUserTableDao().save(userTable);
        mView.showToast("保存成功");
    }

    /**
     * 更新用户id与网络请求数据id相同的信息
     */
    @Override
    public void upDataUserInfo() {
        List<UserTable> listAll = mDaoSession.getUserTableDao().queryBuilder().where(UserTableDao.Properties.Id.eq(mUserBean.id)).list();
        if (listAll.size() == 0) {
            mView.showToast("没有可更新的数据");
            return;
        }
        for (UserTable searchUser : listAll) {
            //更新token
            searchUser.setToken(mUserBean.token);
            mDaoSession.getUserTableDao().update(searchUser);
        }

        queryUserInfo();
    }

    /**
     * 查询用户信息
     */
    @Override
    public void queryUserInfo() {
        List<UserTable> list = mDaoSession.getUserTableDao().queryBuilder().list();
        StringBuilder stringBuilder = new StringBuilder();
        for (UserTable userTable : list) {
            stringBuilder.append("_id:" + userTable.get_id() + "\nid:" + userTable.getId() + "\ntoken:" + userTable.getToken() + "\nname:" + userTable.getName() + "\nheadImg:" + userTable.getHeadPortrait() + "\n\n");
        }
        mView.setUserTextViewValue(stringBuilder.toString());
    }

    /**
     * 清理数据
     */
    @Override
    public void cleanUserInfo() {
        mDaoSession.getUserTableDao().deleteAll();
        queryUserInfo();
    }


}
