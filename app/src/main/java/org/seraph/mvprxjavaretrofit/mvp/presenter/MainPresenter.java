package org.seraph.mvprxjavaretrofit.mvp.presenter;

import org.reactivestreams.Subscription;
import org.seraph.mvprxjavaretrofit.App;
import org.seraph.mvprxjavaretrofit.db.table.UserTable;
import org.seraph.mvprxjavaretrofit.mvp.model.BaseData;
import org.seraph.mvprxjavaretrofit.mvp.model.UserBean;
import org.seraph.mvprxjavaretrofit.mvp.view.BaseView;
import org.seraph.mvprxjavaretrofit.mvp.view.MainView;
import org.seraph.mvprxjavaretrofit.request.ApiService;
import org.seraph.mvprxjavaretrofit.request.exception.ServerErrorCode;

import java.util.List;

/**
 * mian逻辑处理层
 * date：2017/2/15 11:27
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class MainPresenter extends BasePresenter {

    private MainView mainView;

    private Subscription subscriber;

    private boolean isToolBarShow = true;

    private BaseData<UserBean> baseData;

    @Override
    public void attachView(BaseView mView) {
        super.attachView(mView);
        this.mainView = (MainView) mView;
    }

    /**
     * test网络请求
     */
    public void getNetWork() {
        ApiService.doLogin("15623088767", "123456").doOnSubscribe(subscription -> {
            this.subscriber = subscription;
            mainView.showLoading();
        }).subscribe(baseResponse -> {
            mainView.hideLoading();
            baseData = baseResponse.data;
            UserBean userBean = baseData.data;
            mainView.setTextViewValue("token->" + baseData.token + "\nnickName->" + userBean.nickName + "\nheadImg->" + userBean.headImg);
        }, e -> ServerErrorCode.errorCodeToMessageShow(e, mainView));
    }


    /**
     * 切换状态栏是否显示
     */
    public void switchToolBarVisibility() {
        if (isToolBarShow) {
            mainView.hideToolBar();
        } else {
            mainView.showToolBar();
        }
        this.isToolBarShow = !isToolBarShow;
    }


    /**
     * 保存用户信息
     */
    public void saveUserInfo() {
        if (baseData == null) {
            mainView.showSnackBar("没有可保存数据");
            return;
        }
        UserTable userBean = new UserTable();
        userBean.setToken(baseData.token);
        userBean.setName(baseData.data.nickName);
        userBean.setHeadPortrait(baseData.data.headImg);
        App.getSingleton().getDaoSession().getUserTableDao().save(userBean);
        mainView.showSnackBar("保存成功");
    }

    /**
     * 查询用户信息
     */
    public void queryUserInfo() {
        List<UserTable>  list = App.getSingleton().getDaoSession().getUserTableDao().queryBuilder().list();
        StringBuilder stringBuilder = new StringBuilder();
        for (UserTable userTable : list){
            stringBuilder.append("id:"+userTable.get_id()+"\ntoken:"+userTable.getToken()+"\nname:"+userTable.getName()+"\nheadImg:"+userTable.getHeadPortrait()+"\n\n");
        }
        mainView.setUserTextViewValue(stringBuilder.toString());
    }

    /**
     * 清理数据
     */
    public void cleanUserInfo() {
        App.getSingleton().getDaoSession().getUserTableDao().deleteAll();
        queryUserInfo();
    }

    @Override
    public void unSubscribe() {
        super.unSubscribe();
        if (subscriber != null) {
            subscriber.cancel();
        }
    }


}
