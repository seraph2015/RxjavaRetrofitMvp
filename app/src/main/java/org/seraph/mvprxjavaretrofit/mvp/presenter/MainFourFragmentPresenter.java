package org.seraph.mvprxjavaretrofit.mvp.presenter;

import org.seraph.mvprxjavaretrofit.R;
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

    private float percentScroll = 0f;


    @Override
    public void attachView(BaseView view) {
        super.attachView(view);
        this.mView = (MainFourFragmentView) view;
    }


    private String title;
    private int logoIcon;

    public void initData() {
        title = " 其它测试";
        logoIcon = R.drawable.ic_assignment_ind_black_24dp;
        mView.setTitleAndLogo(title,logoIcon);
    }


    /**
     * 更新头部背景透明度
     *
     * @param percentScroll 进度百分比
     */
    public void upDataToolbarAlpha(float percentScroll) {
        this.percentScroll = percentScroll;
        mView.upDataToolbarAlpha(percentScroll);
    }


    @Override
    public void restoreData() {
        super.restoreData();
        mView.setTitleAndLogo(title,logoIcon);
        mView.upDataToolbarAlpha(percentScroll);
    }
}
