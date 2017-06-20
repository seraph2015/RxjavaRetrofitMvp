package org.seraph.mvprxjavaretrofit.ui.module.main;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import org.seraph.mvprxjavaretrofit.AppApplication;
import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.di.component.main.DaggerMainActivityComponent;
import org.seraph.mvprxjavaretrofit.di.module.ActivityModule;
import org.seraph.mvprxjavaretrofit.ui.module.base.BaseFragment;
import org.seraph.mvprxjavaretrofit.ui.module.main.contract.MainOneFragmentContract;
import org.seraph.mvprxjavaretrofit.ui.module.main.presenter.MainOneFragmentPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 主界面
 * date：2017/2/20 16:38
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class MainOneFragment extends BaseFragment implements MainOneFragmentContract.View {


    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_db_user)
    TextView tvDbUser;

    @Override
    public int getContentView() {
        return R.layout.test_fragment_one;
    }

    @Inject
    MainOneFragmentPresenter mPresenter;

    @Override
    public void setupActivityComponent() {
        DaggerMainActivityComponent.builder().appComponent(AppApplication.getAppComponent()).activityModule(new ActivityModule(getActivity())).build().inject(this);
    }


    @Override
    public void initCreate(@Nullable Bundle savedInstanceState) {
        initListener();
        mPresenter.setView(this);
        mPresenter.start();

    }

    private void initListener() {
        mLoadingDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mPresenter.unSubscribe();
            }
        });
    }


    public void setTextViewValue(CharSequence charSequence) {
        tvContent.setText(charSequence);
    }

    public void setUserTextViewValue(CharSequence charSequence) {
        tvDbUser.setText(charSequence);
    }

    @OnClick({R.id.btn_request, R.id.btn_sava_db, R.id.btn_updata_db, R.id.tv_query_user, R.id.tv_clean_user})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_request:
                mPresenter.doLoginTest();
                break;
            case R.id.btn_sava_db:
                mPresenter.saveUserInfo();
                break;
            case R.id.btn_updata_db:
                mPresenter.upDataUserInfo();
                break;
            case R.id.tv_query_user:
                mPresenter.queryUserInfo();
                break;
            case R.id.tv_clean_user:
                mPresenter.cleanUserInfo();
                break;
        }
    }
}
