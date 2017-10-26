package org.seraph.mvprxjavaretrofit.ui.module.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.jakewharton.rxbinding2.widget.RxCompoundButton;
import com.jakewharton.rxbinding2.widget.RxTextView;

import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.di.component.DaggerLoginComponent;
import org.seraph.mvprxjavaretrofit.di.component.base.AppComponent;
import org.seraph.mvprxjavaretrofit.di.module.base.ActivityModule;
import org.seraph.mvprxjavaretrofit.ui.module.base.ABaseActivity;
import org.seraph.mvprxjavaretrofit.ui.module.login.contract.SetPasswordActivityContract;
import org.seraph.mvprxjavaretrofit.ui.module.login.presenter.SetPasswordActivityPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 * 设置密码
 * date：2017/10/26 10:40
 * author：Seraph
 * mail：417753393@qq.com
 **/
public class SetPasswordActivity extends ABaseActivity<SetPasswordActivityContract.View, SetPasswordActivityContract.Presenter> implements SetPasswordActivityContract.View {

    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.et_new_password)
    EditText etNewPassword;
    @BindView(R.id.switch_show)
    Switch aSwitch;
    @BindView(R.id.btn_ok)
    Button btnOk;

    @Override
    public int getContextView() {
        return R.layout.act_login_set_password;
    }

    @Override
    public void setupActivityComponent(AppComponent appComponent, ActivityModule activityModule) {
        DaggerLoginComponent.builder().appComponent(appComponent).activityModule(activityModule).build().inject(this);
    }

    @Inject
    SetPasswordActivityPresenter mPresenter;

    @Override
    protected SetPasswordActivityContract.Presenter getMVPPresenter() {
        return mPresenter;
    }

    @Override
    public void initCreate(@Nullable Bundle savedInstanceState) {
        tvToolbarTitle.setText("设置新密码");
        initListener();
    }

    private void initListener() {
        RxTextView.textChanges(etNewPassword).subscribe(new Consumer<CharSequence>() {
            @Override
            public void accept(CharSequence newPassword) throws Exception {
                aSwitch.setVisibility((newPassword.length() > 0) ? View.VISIBLE : View.GONE);
                btnOk.setEnabled(newPassword.length() >= 6);
            }
        });
        RxCompoundButton.checkedChanges(aSwitch).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                //切换密码显示和隐藏
                etNewPassword.setTransformationMethod(aBoolean ? HideReturnsTransformationMethod.getInstance() : PasswordTransformationMethod.getInstance());
                etNewPassword.setSelection(etNewPassword.getText().length());
            }
        });

    }


    @OnClick(R.id.btn_ok)
    public void onViewClicked() {
        mPresenter.onSetPassword(etNewPassword.getText().toString());
    }
}
