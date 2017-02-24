package org.seraph.mvprxjavaretrofit.mvp.presenter;

import android.content.Context;

import org.seraph.mvprxjavaretrofit.activity.MainActivity;
import org.seraph.mvprxjavaretrofit.mvp.view.BaseView;
import org.seraph.mvprxjavaretrofit.mvp.view.MainFourFragmentView;

/**
 * FragmentFpur逻辑处理层
 * date：2017/2/15 11:27
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class MainFourFragmentPresenter extends BasePresenter {


    private MainFourFragmentView mView;

    private MainActivity mainActivity;

    private float percentScroll = 0f;

    @Override
    public void attachView(BaseView mView) {
        super.attachView(mView);
        this.mView = (MainFourFragmentView) mView;
    }

    @Override
    public void onAttach(Context context) {
        mainActivity = (MainActivity) context;
    }


    private String title;

    public void initData() {
        title = " Four";
        setTitle(title);
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


    @Override
    public void restoreData() {
        super.restoreData();
        setTitle(title);
        upDataToolbarAlpha(percentScroll);
    }
}
