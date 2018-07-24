package org.seraph.mvprxjavaretrofit.ui.module.welcome;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.blankj.utilcode.util.SPUtils;

import org.seraph.mvprxjavaretrofit.AppConstants;
import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.databinding.WelcomeActivityBinding;
import org.seraph.mvprxjavaretrofit.ui.module.base.ABaseActivity;
import org.seraph.mvprxjavaretrofit.ui.module.main.MainActivity;

import javax.inject.Inject;

/**
 * 欢迎页
 * date：2017/5/3 09:50
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class WelcomeActivity extends ABaseActivity<WelcomeActivityContract.Presenter> implements WelcomeActivityContract.View {

    WelcomeActivityBinding binding;

    @Override
    protected void initContextView() {
        binding = DataBindingUtil.setContentView(this, R.layout.welcome_activity);
        binding.setActivity(this);
    }

    @Inject
    WelcomeActivityPresenter mPresenter;


    @Override
    protected WelcomeActivityContract.Presenter getMVPPresenter() {
        return mPresenter;
    }

    @Override
    public void initCreate(@Nullable Bundle savedInstanceState) {
        mPresenter.start();
    }


    @Override
    public void jumpNextActivity() {
        //如果已经不是第一次则跳转主界面。如果是第一次，跳转引导页
        Intent intent;
        if (SPUtils.getInstance(AppConstants.SPAction.SP_NAME).getBoolean(AppConstants.SPAction.IS_FIRST, true)) {
            intent = new Intent(this, GuidePagesActivity.class);
        } else {
            intent = new Intent(this, MainActivity.class);
        }
        startActivity(intent);
        finish();
    }


    public void onViewClicked(View view) {
        jumpNextActivity();
    }
}
