package org.seraph.mvprxjavaretrofit.ui.module.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.RegexUtils;
import com.jakewharton.rxbinding2.support.v7.widget.RxToolbar;
import com.jakewharton.rxbinding2.widget.RxTextView;

import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.di.component.DaggerLoginComponent;
import org.seraph.mvprxjavaretrofit.di.component.base.AppComponent;
import org.seraph.mvprxjavaretrofit.di.module.base.ActivityModule;
import org.seraph.mvprxjavaretrofit.ui.module.base.ABaseActivity;
import org.seraph.mvprxjavaretrofit.ui.module.login.contract.LoginActivityContract;
import org.seraph.mvprxjavaretrofit.ui.module.login.presenter.LoginActivityPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;

/**
 * 登录
 * date：2017/10/25 10:28
 * author：Seraph
 * mail：417753393@qq.com
 **/
public class LoginActivity extends ABaseActivity<LoginActivityContract.View, LoginActivityContract.Presenter> implements LoginActivityContract.View {


    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    public int getContextView() {
        return R.layout.act_login;
    }

    @Override
    public void setupActivityComponent(AppComponent appComponent, ActivityModule activityModule) {
        DaggerLoginComponent.builder().appComponent(appComponent).activityModule(activityModule).build().inject(this);
    }

    @Inject
    LoginActivityPresenter mPresenter;

    @Override
    protected LoginActivityContract.Presenter getMVPPresenter() {
        return mPresenter;
    }

    @Override
    public void initCreate(@Nullable Bundle savedInstanceState) {
        tvToolbarTitle.setText("登录");
        initListener();
    }

    /**
     * 初始化监听
     */
    private void initListener() {
        RxToolbar.itemClicks(toolbar).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<MenuItem>() {
            @Override
            public void accept(MenuItem menuItem) throws Exception {
                //只有注册按钮
                startActivity(new Intent(getContext(),RegisteredActivity.class));
            }
        });

        Observable<CharSequence> loginPhone = RxTextView.textChanges(etPhone);
        Observable<CharSequence> loginPassword = RxTextView.textChanges(etPassword);

        Observable.combineLatest(loginPhone, loginPassword, new BiFunction<CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean apply(CharSequence phone, CharSequence password) throws Exception {

                return RegexUtils.isMobileSimple(phone) && password.length() >= 6;
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Boolean>() {

            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean) {
                    btnLogin.setEnabled(true);
                } else {
                    btnLogin.setEnabled(false);
                }
            }
        });

    }


    @OnClick({R.id.tv_forget_password, R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_forget_password://忘记密码
                startActivity(new Intent(this,ResetPasswordActivity.class));
                break;
            case R.id.btn_login://登录
                mPresenter.onLogin();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
