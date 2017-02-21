package org.seraph.mvprxjavaretrofit.mvp.presenter;

import android.content.Context;

import org.seraph.mvprxjavaretrofit.activity.MainActivity;

/**
 * 第二页P
 * date：2017/2/21 17:10
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class MainTwoFragmentPresenter extends BasePresenter{

    private MainActivity mainActivity;

    @Override
    public void onAttach(Context context) {
        mainActivity = (MainActivity) context;
        super.onAttach(context);
    }

    private String title;

    public void initData(){
        title = "第二页";
        setTitle(title);
    }


    @Override
    public void restoreData() {
        super.restoreData();
        setTitle(title);
        upDataToolbarAlpha(0);
    }

    public void setTitle(String title){
        mainActivity.setTitle(title);
    }

    /**
     * 更新头部背景透明度
     * @param percentScroll 进度百分比
     */
    private void upDataToolbarAlpha(float percentScroll) {
        mainActivity.mMainPresenter.upDataToolbarAlpha(percentScroll);
    }

}
