package org.seraph.mvprxjavaretrofit.ui.module.welcome;

import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.Nullable;
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
public class WelcomeActivity extends ABaseActivity implements WelcomeActivityContract.View {

    WelcomeActivityBinding binding;

    @Override
    protected void initContextView() {
        binding = DataBindingUtil.setContentView(this, R.layout.welcome_activity);
        binding.setActivity(this);
    }

    @Inject
    WelcomeActivityPresenter presenter;


    @Override
    protected WelcomeActivityContract.Presenter getMVPPresenter() {
        presenter.setView(this);
        return presenter;
    }

    @Override
    public void initCreate(@Nullable Bundle savedInstanceState) {
        presenter.start();
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
