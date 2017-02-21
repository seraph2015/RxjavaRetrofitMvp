package org.seraph.mvprxjavaretrofit.mvp.presenter;

import android.content.Context;
import android.graphics.Color;

import org.reactivestreams.Subscription;
import org.seraph.mvprxjavaretrofit.App;
import org.seraph.mvprxjavaretrofit.activity.MainActivity;
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


    private MainOneFragmentView fragmentOneView;

    private Subscription subscriber;

    private BaseData<UserBean> baseData;

    private MainActivity mainActivity;

    @Override
    public void attachView(BaseView mView) {
        super.attachView(mView);
        this.fragmentOneView = (MainOneFragmentView) mView;
    }

    @Override
    public void onAttach(Context context) {
        mainActivity = (MainActivity) context;
    }

    /**
     * test网络请求
     */
    public void getNetWork() {
        ApiService.doLogin("15623088767", "123456").doOnSubscribe(subscription -> {
            this.subscriber = subscription;
            fragmentOneView.showLoading();
        }).subscribe(baseResponse -> {
            fragmentOneView.hideLoading();
            baseData = baseResponse.data;
            UserBean userBean = baseData.data;
            fragmentOneView.setTextViewValue("token->" + baseData.token + "\nnickName->" + userBean.nickName + "\nheadImg->" + userBean.headImg);
        }, e -> ServerErrorCode.errorCodeToMessageShow(e, mainActivity));
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
        userBean.setToken(baseData.token);
        userBean.setName(baseData.data.nickName);
        userBean.setHeadPortrait(baseData.data.headImg);
        App.getDaoSession().getUserTableDao().save(userBean);
        mainActivity.showSnackBar("保存成功");
    }

    /**
     * 查询用户信息
     */
    public void queryUserInfo() {
        List<UserTable> list = App.getDaoSession().getUserTableDao().queryBuilder().list();
        StringBuilder stringBuilder = new StringBuilder();
        for (UserTable userTable : list) {
            stringBuilder.append("id:" + userTable.get_id() + "\ntoken:" + userTable.getToken() + "\nname:" + userTable.getName() + "\nheadImg:" + userTable.getHeadPortrait() + "\n\n");
        }
        fragmentOneView.setUserTextViewValue(stringBuilder.toString());
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
     * 更新头部背景透明度
     *
     * @param percentScroll 进度百分比
     */
    public void upDataToolbarAlpha(float percentScroll) {
        //计算透明度，默认透明 50  00FF9D
        int alpha = (int) (50 + (205 * percentScroll));
        mainActivity.setToolBarBackgroundColor(Color.argb(alpha, 0x9E, 0x57, 0xFF));
    }

    /**
     * 切换状态栏是否显示
     */
    public void switchToolBarVisibility() {
        mainActivity.mMainPresenter.switchToolBarVisibility();
    }
}
