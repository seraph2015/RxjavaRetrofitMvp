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
import org.seraph.mvprxjavaretrofit.di.component.DaggerLoginComponent;
import org.seraph.mvprxjavaretrofit.di.component.base.AppComponent;
import org.seraph.mvprxjavaretrofit.di.module.base.ActivityModule;
import org.seraph.mvprxjavaretrofit.ui.module.base.ABaseActivity;
import org.seraph.mvprxjavaretrofit.ui.module.login.contract.RegisteredActivityContract;
import org.seraph.mvprxjavaretrofit.ui.module.login.presenter.RegisteredActivityPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;

/**
 * 注册
 * date：2017/10/25 12:37
 * author：Seraph
 * mail：417753393@qq.com
 **/
public class RegisteredActivity extends ABaseActivity<RegisteredActivityContract.View, RegisteredActivityContract.Presenter> implements RegisteredActivityContract.View {


    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.btn_get_code)
    Button btnGetCode;
    @BindView(R.id.tv_agreement)
    TextView tvAgreement;
    @BindView(R.id.btn_ok)
    Button btnOk;

    @Override
    public int getContextView() {
        return R.layout.act_login_registered;
    }

    @Override
    public void setupActivityComponent(AppComponent appComponent, ActivityModule activityModule) {
        DaggerLoginComponent.builder().appComponent(appComponent).activityModule(activityModule).build().inject(this);
    }

    @Inject
    RegisteredActivityPresenter mPresenter;

    @Override
    protected RegisteredActivityContract.Presenter getMVPPresenter() {
        return mPresenter;
    }

    @Override
    public void initCreate(@Nullable Bundle savedInstanceState) {
        tvToolbarTitle.setText("注册");
        initListener();
    }

    private void initListener() {
        Observable<CharSequence> phone = RxTextView.textChanges(etPhone);
        Observable<CharSequence> code = RxTextView.textChanges(etCode);
        Observable.combineLatest(phone, code, new BiFunction<CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean apply(CharSequence phone, CharSequence code) throws Exception {
                //验证手机
                boolean isPhone = RegexUtils.isMobileSimple(phone);
                btnGetCode.setEnabled(isPhone);
                //验证验证码
                return isPhone && code.length() == 6;
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                btnOk.setEnabled(aBoolean);
            }
        });
    }


    @OnClick({R.id.btn_get_code, R.id.tv_agreement, R.id.btn_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_get_code://获取验证码
                mPresenter.onGetCode(etPhone.getText().toString());
                break;
            case R.id.tv_agreement:
                startActivity(new Intent(this,AgreementActivity.class));
                break;
            case R.id.btn_ok://提交注册
                mPresenter.onRegistered();
                break;
        }
    }
}
