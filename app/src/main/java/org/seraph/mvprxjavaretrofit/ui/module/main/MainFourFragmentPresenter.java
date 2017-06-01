package org.seraph.mvprxjavaretrofit.ui.module.main;

import javax.inject.Inject;

/**
 * FragmentFpur逻辑处理层
 * date：2017/2/15 11:27
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class MainFourFragmentPresenter implements MainFourFragmentContract.Presenter {

    private MainFourFragmentContract.View mView;

    @Inject
    MainFourFragmentPresenter() {

    }

    @Override
    public void start() {

    }

    @Override
    public void unSubscribe() {

    }

    @Override
    public void setView(MainFourFragmentContract.View view) {
        this.mView = view;
    }
}
