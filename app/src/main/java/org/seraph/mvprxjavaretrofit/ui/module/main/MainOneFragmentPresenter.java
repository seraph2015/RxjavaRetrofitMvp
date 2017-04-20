package org.seraph.mvprxjavaretrofit.ui.module.main;

import org.reactivestreams.Subscription;
import org.seraph.mvprxjavaretrofit.data.network.ApiManager;
import org.seraph.mvprxjavaretrofit.ui.module.base.BaseNetWorkSubscriber;
import org.seraph.mvprxjavaretrofit.data.local.db.gen.DaoSession;
import org.seraph.mvprxjavaretrofit.data.local.db.gen.UserTableDao;
import org.seraph.mvprxjavaretrofit.data.local.db.table.UserTable;
import org.seraph.mvprxjavaretrofit.ui.module.base.BaseData;
import org.seraph.mvprxjavaretrofit.ui.module.base.BaseDataResponse;
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
class MainOneFragmentPresenter implements MainOneFragmentContract.Presenter {


    private MainOneFragmentContract.View mView;

    private Subscription mSubscriber;

    private BaseData<UserBean> baseData;

    private ApiManager mApiManager;

    private DaoSession mDaoSession;

    @Inject
    MainOneFragmentPresenter(ApiManager apiManager, DaoSession daoSession) {
        this.mDaoSession = daoSession;
        this.mApiManager = apiManager;
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
        mApiManager.doLogin("15172311067", "123456").doOnSubscribe(new Consumer<Subscription>() {
            @Override
            public void accept(Subscription subscription) throws Exception {
                mSubscriber = subscription;
                mView.showLoading("正在登陆...");
            }
        }).subscribe(new BaseNetWorkSubscriber<BaseDataResponse<UserBean>,MainOneFragmentContract.View>(mView) {
            @Override
            public void onSuccess(BaseDataResponse<UserBean> baseDataResponse) {
                baseData = baseDataResponse.data;
                UserBean userBean = baseData.data;
                mView.showToast("登陆成功");
                mView.setTextViewValue("token->" + baseData.token + "\nuserId->" + userBean.id + "\nnickName->" + userBean.nickName + "\nheadImg->" + userBean.headImg);
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
        if (baseData == null) {
            mView.showToast("没有可保存数据");
            return;
        }
        UserTable userBean = new UserTable();
        userBean.setId(baseData.data.id);
        userBean.setToken(baseData.token);
        userBean.setName(baseData.data.nickName);
        userBean.setHeadPortrait(baseData.data.headImg);
        mDaoSession.getUserTableDao().save(userBean);
        mView.showToast("保存成功");
    }

    /**
     * 更新用户id与网络请求数据id相同的信息
     */
    @Override
    public void upDataUserInfo() {
        List<UserTable> listAll = mDaoSession.getUserTableDao().queryBuilder().where(UserTableDao.Properties.Id.eq(baseData.data.id)).list();
        if (listAll.size() == 0) {
            mView.showToast("没有可更新的数据");
            return;
        }
        for (UserTable searchUser : listAll) {
            //更新token
            searchUser.setToken(baseData.token);
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

    @Override
    public void unSubscriber() {
        if (mSubscriber != null) {
            mSubscriber.cancel();
        }
    }


}
