package org.seraph.mvprxjavaretrofit.ui.module.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.RegexUtils;
import com.jakewharton.rxbinding2.widget.RxTextView;

import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.di.component.base.AppComponent;
import org.seraph.mvprxjavaretrofit.di.module.base.ActivityModule;
import org.seraph.mvprxjavaretrofit.ui.module.base.ABaseActivity;
import org.seraph.mvprxjavaretrofit.ui.module.login.contract.VerifyPhoneActivityContract;
import org.seraph.mvprxjavaretrofit.ui.module.login.presenter.VerifyPhoneActivityPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;

/**
 * 验证手机号
 * date：2017/10/25 18:18
 * author：Seraph
 * mail：417753393@qq.com
 **/
public class VerifyPhoneActivity extends ABaseActivity<VerifyPhoneActivityContract.View, VerifyPhoneActivityContract.Presenter> implements VerifyPhoneActivityContract.View {

    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.btn_get_code)
    Button btnGetCode;
    @BindView(R.id.btn_next)
    Button btnNext;

    @Override
    public int getContextView() {
        return R.layout.act_login_verify_phone;
    }

    @Override
    public void setupActivityComponent(AppComponent appComponent, ActivityModule activityModule) {
        DaggerLoginComponent.builder().appComponent(appComponent).activityModule(activityModule).build().inject(this);
    }

    @Inject
    VerifyPhoneActivityPresenter mPresenter;

    @Override
    protected VerifyPhoneActivityContract.Presenter getMVPPresenter() {
        return mPresenter;
    }

    @Override
    public void initCreate(@Nullable Bundle savedInstanceState) {
        tvToolbarTitle.setText("验证手机号");
        initListener();
    }

    private void initListener() {
        Observable<CharSequence> phone = RxTextView.textChanges(etPhone);
        Observable<CharSequence> code = RxTextView.textChanges(etCode);
        Observable.combineLatest(phone, code, new BiFunction<CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean apply(CharSequence phone, CharSequence code) throws Exception {
                boolean isPhone = RegexUtils.isMobileSimple(phone);
                btnGetCode.setEnabled(isPhone);
                return isPhone && code.length() == 6;
            }
        }).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                btnNext.setEnabled(aBoolean);
            }
        });
    }


    @OnClick({R.id.btn_get_code, R.id.btn_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_get_code://获取验证码
                mPresenter.onGetCode(etPhone.getText().toString());
                break;
            case R.id.btn_next:
                //获取手机，验证码进行跳转
                startActivity(new Intent(this,SetPasswordActivity.class));
                finish();
                break;
        }
    }
}
