package org.seraph.mvprxjavaretrofit.ui.module.login;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.jakewharton.rxbinding2.widget.RxTextView;

import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.di.component.DaggerLoginComponent;
import org.seraph.mvprxjavaretrofit.di.component.base.AppComponent;
import org.seraph.mvprxjavaretrofit.di.module.base.ActivityModule;
import org.seraph.mvprxjavaretrofit.ui.module.base.ABaseActivity;
import org.seraph.mvprxjavaretrofit.ui.module.login.contract.LoginContract;
import org.seraph.mvprxjavaretrofit.ui.module.login.presenter.LoginPresenter;

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
public class LoginActivity extends ABaseActivity<LoginContract.Presenter> implements LoginContract.View {

    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.iv_show_delete)
    ImageView ivDelete;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.cb_password_mode)
    CheckBox cbPasswordMode;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.iv_ui_bg)
    ImageView ivUIBg;

    @Override
    public int getContextView() {
        return R.layout.act_login;
    }

    @Override
    public void setupActivityComponent(AppComponent appComponent, ActivityModule activityModule) {
        DaggerLoginComponent.builder().appComponent(appComponent).activityModule(activityModule).build().inject(this);
    }

    @Inject
    LoginPresenter mPresenter;

    @Override
    protected LoginContract.Presenter getMVPPresenter() {
        return mPresenter;
    }

    @Override
    public void initCreate(@Nullable Bundle savedInstanceState) {
        tvToolbarTitle.setText("登录");
        initListener();
        mPresenter.start();
    }

    /**
     * 初始化监听
     */
    private void initListener() {
        Bitmap bitmap = ConvertUtils.drawable2Bitmap(getResources().getDrawable(R.mipmap.welcome_guide_pages_three));
        ivUIBg.setImageBitmap(ImageUtils.fastBlur(bitmap, 1, 15, true));

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
                btnLogin.setEnabled(aBoolean);
            }
        });

    }


    @OnClick({R.id.tv_registered, R.id.iv_show_delete, R.id.tv_forget_password, R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_show_delete://删除手机号
                etPhone.setText("");
                break;
            case R.id.tv_registered://注册
                startActivity(new Intent(getContext(), RegisteredActivity.class));
                break;
            case R.id.tv_forget_password://忘记密码
                startActivity(new Intent(this, ResetPasswordActivity.class));
                break;
            case R.id.btn_login://登录
                mPresenter.onLogin(etPhone.getText().toString().trim(), etPassword.getText().toString().trim());
                break;
        }
    }


    @Override
    public void setUserName(String username) {
        etPhone.setText(username);
        etPhone.setSelection(username.length());
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.anim_slide_out_to_bottom);
    }
}
