package org.seraph.mvprxjavaretrofit.ui.module.welcome;


import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.data.local.AppPreferencesConstant;
import org.seraph.mvprxjavaretrofit.data.local.AppPreferencesManager;

import javax.inject.Inject;

/**
 * 欢迎页逻辑
 * date：2017/5/3 10:12
 * author：xiongj
 * mail：417753393@qq.com
 **/
class GuidePagesActivityPresenter implements GuidePagesActivityContract.Presenter {

    private GuidePagesActivityContract.View mView;

    private Integer[] images = new Integer[]{R.mipmap.welcome_guide_pages_one, R.mipmap.welcome_guide_pages_two, R.mipmap.welcome_guide_pages_three, R.mipmap.welcome_guide_pages_four};

    private AppPreferencesManager mPreferencesManager;

    @Inject
    GuidePagesActivityPresenter(AppPreferencesManager preferencesManager) {
        mPreferencesManager = preferencesManager;
    }


    @Override
    public void setView(GuidePagesActivityContract.View view) {
        mView = view;
    }


    @Override
    public void start() {
        mPreferencesManager.save(AppPreferencesConstant.IS_FIRST, false);
        //设置引导页
        mView.setImageList(images);
    }

    @Override
    public void onItemClick(int position) {
        if (position == images.length - 1) {//最后一页点击跳转
            mView.jumpNextActivity();
        }
    }
}
