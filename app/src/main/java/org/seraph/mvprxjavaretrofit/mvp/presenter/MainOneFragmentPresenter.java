package org.seraph.mvprxjavaretrofit.mvp.presenter;

import org.reactivestreams.Subscription;
import org.seraph.mvprxjavaretrofit.App;
import org.seraph.mvprxjavaretrofit.db.gen.UserTableDao;
import org.seraph.mvprxjavaretrofit.db.table.UserTable;
import org.seraph.mvprxjavaretrofit.mvp.model.BaseData;
import org.seraph.mvprxjavaretrofit.mvp.model.UserBean;
import org.seraph.mvprxjavaretrofit.mvp.view.BaseView;
import org.seraph.mvprxjavaretrofit.mvp.view.MainOneFragmentView;
import org.seraph.mvprxjavaretrofit.request.ApiService;
import org.seraph.mvprxjavaretrofit.request.exception.ServerErrorCode;

import java.util.List;

/**
 * FragmentOne逻辑处理层
 * date：2017/2/15 11:27
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class MainOneFragmentPresenter extends BasePresenter {


    private MainOneFragmentView mView;

    private Subscription subscriber;

    private BaseData<UserBean> baseData;


    @Override
    public void attachView(BaseView view) {
        super.attachView(view);
        this.mView = (MainOneFragmentView) view;
    }

    /**
     * 保存的百分比
     */
    private float percentScroll = 0f;

    private String title;

    public void initData() {
        title = " 主页";
        mView.setTitle(title);
    }

    /**
     * test网络请求
     */
    public void doLogin() {
        ApiService.doLogin("15623088767", "123456").doOnSubscribe(subscription -> {
            this.subscriber = subscription;
            mView.showLoading();
        }).subscribe(baseResponse -> {
                    mView.hideLoading();
                    baseData = baseResponse.data;
                    UserBean userBean = baseData.data;
                    mView.setTextViewValue("token->" + baseData.token + "\nuserId->" + userBean.id + "\nnickName->" + userBean.nickName + "\nheadImg->" + userBean.headImg);
                }, e -> {
                    mView.hideLoading();
                    mView.showToast(ServerErrorCode.errorCodeToMessageShow(e));
                }
        );
    }


    /**
     * 保存用户信息
     */
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
        App.getDaoSession().getUserTableDao().save(userBean);
        mView.showToast("保存成功");
    }

    /**
     * 更新用户id与网络请求数据id相同的信息
     */
    public void upDataUserInfo() {
        List<UserTable> listAll = App.getDaoSession().getUserTableDao().queryBuilder().where(UserTableDao.Properties.Id.eq(baseData.data.id)).list();
        if (listAll.size() == 0) {
            mView.showToast("没有可更新的数据");
            return;
        }
        for (UserTable searchUser : listAll) {
            //更新token
            searchUser.setToken(baseData.token);
            App.getDaoSession().getUserTableDao().update(searchUser);
        }

        queryUserInfo();
    }

    /**
     * 查询用户信息
     */
    public void queryUserInfo() {
        List<UserTable> list = App.getDaoSession().getUserTableDao().queryBuilder().list();
        StringBuilder stringBuilder = new StringBuilder();
        for (UserTable userTable : list) {
            stringBuilder.append("_id:" + userTable.get_id() + "\nid:" + userTable.getId() + "\ntoken:" + userTable.getToken() + "\nname:" + userTable.getName() + "\nheadImg:" + userTable.getHeadPortrait() + "\n\n");
        }
        mView.setUserTextViewValue(stringBuilder.toString());
    }

    /**
     * 清理数据
     */
    public void cleanUserInfo() {
        App.getDaoSession().getUserTableDao().deleteAll();
        queryUserInfo();
    }

    @Override
    public void unSubscribe() {
        if (subscriber != null) {
            subscriber.cancel();
        }
    }


    /**
     * 更新头部背景透明度并且保存
     *
     * @param percentScroll 进度百分比
     */
    public void upDataSaveToolbarAlpha(float percentScroll) {
        this.percentScroll = percentScroll;
        mView.upDataToolbarAlpha(percentScroll);
    }


    @Override
    public void restoreData() {
        super.restoreData();
        mView.setTitle(title);
        mView.upDataToolbarAlpha(percentScroll);
    }

}
