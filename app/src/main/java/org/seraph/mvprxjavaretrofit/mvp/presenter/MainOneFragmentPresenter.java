package org.seraph.mvprxjavaretrofit.mvp.presenter;

import android.content.Context;

import org.reactivestreams.Subscription;
import org.seraph.mvprxjavaretrofit.App;
import org.seraph.mvprxjavaretrofit.activity.MainActivity;
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

    private MainActivity mainActivity;

    @Override
    public void attachView(BaseView view) {
        super.attachView(view);
        this.mView = (MainOneFragmentView) view;
    }

    @Override
    public void onAttach(Context context) {
        mainActivity = (MainActivity) context;
    }

    /**
     * 保存的百分比
     */
    private float percentScroll = 0f;

    private String title;

    public void initData() {
        title = " 主页";
        setTitle(title);
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
                    ServerErrorCode.errorCodeToMessageShow(e, mainActivity);
                }
        );
    }


    /**
     * 保存用户信息
     */
    public void saveUserInfo() {
        if (baseData == null) {
            mainActivity.showSnackBar("没有可保存数据");
            return;
        }
        UserTable userBean = new UserTable();
        userBean.setId(baseData.data.id);
        userBean.setToken(baseData.token);
        userBean.setName(baseData.data.nickName);
        userBean.setHeadPortrait(baseData.data.headImg);
        App.getDaoSession().getUserTableDao().save(userBean);
        mainActivity.showSnackBar("保存成功");
    }

    /**
     * 更新用户id与网络请求数据id相同的信息
     */
    public void upDataUserInfo() {
        List<UserTable> listAll = App.getDaoSession().getUserTableDao().queryBuilder().where(UserTableDao.Properties.Id.eq(baseData.data.id)).list();
        if (listAll.size() == 0) {
            mainActivity.showSnackBar("没有可更新的数据");
            return;
        }
        for(UserTable searchUser : listAll){
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

    public void setTitle(String title) {
        mainActivity.setTitle(title);
    }

    /**
     * 更新头部背景透明度
     *
     * @param percentScroll 进度百分比
     */
    public void upDataToolbarAlpha(float percentScroll) {
        this.percentScroll = percentScroll;
        mainActivity.mPresenter.upDataToolbarAlpha(percentScroll);
    }

    /**
     * 切换状态栏是否显示
     */
    public void switchToolBarVisibility() {
        mainActivity.mPresenter.switchToolBarVisibility();
    }

    @Override
    public void restoreData() {
        super.restoreData();
        setTitle(title);
        upDataToolbarAlpha(percentScroll);
    }

}
