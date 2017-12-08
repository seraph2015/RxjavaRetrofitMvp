package org.seraph.mvprxjavaretrofit.ui.module.login;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.jakewharton.rxbinding2.widget.RxCompoundButton;
import com.jakewharton.rxbinding2.widget.RxTextView;

import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.di.component.DaggerLoginComponent;
import org.seraph.mvprxjavaretrofit.di.component.base.AppComponent;
import org.seraph.mvprxjavaretrofit.di.module.base.ActivityModule;
import org.seraph.mvprxjavaretrofit.ui.module.base.ABaseActivity;
import org.seraph.mvprxjavaretrofit.ui.module.login.contract.ResetPasswordContract;
import org.seraph.mvprxjavaretrofit.ui.module.login.presenter.ResetPasswordPresenter;

import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function3;

/**
 * 修改/找回密码
 * date：2017/10/25 18:18
 * author：Seraph
 * mail：417753393@qq.com
 **/
public class ResetPasswordActivity extends ABaseActivity<ResetPasswordContract.Presenter> implements ResetPasswordContract.View {

    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.iv_show_delete)
    ImageView ivDelete;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_get_code)
    TextView tvGetCode;
    @BindView(R.id.et_new_password)
    EditText etNewPassword;
    @BindView(R.id.cb_password_mode)
    CheckBox cbPasswordMode;
    @BindView(R.id.btn_ok)
    Button btnOk;
    @BindView(R.id.iv_ui_bg)
    ImageView ivUIBg;

    @Override
    public int getContextView() {
        return R.layout.act_login_reset_password;
    }

    @Override
    public void setupActivityComponent(AppComponent appComponent, ActivityModule activityModule) {
        DaggerLoginComponent.builder().appComponent(appComponent).activityModule(activityModule).build().inject(this);
    }

    @Inject
    ResetPasswordPresenter mPresenter;

    @Override
    protected ResetPasswordContract.Presenter getMVPPresenter() {
        return mPresenter;
    }

    @Override
    public void initCreate(@Nullable Bundle savedInstanceState) {
        tvToolbarTitle.setText("重置密码");
        initListener();
    }

    private boolean isPhone;
    private boolean isCountdown;

    private void initListener() {
        Bitmap bitmap = ConvertUtils.drawable2Bitmap(getResources().getDrawable(R.mipmap.welcome_guide_pages_one));
        ivUIBg.setImageBitmap(ImageUtils.fastBlur(bitmap, 1, 15, true));
        Observable<CharSequence> phone = RxTextView.textChanges(etPhone);
        Observable<CharSequence> code = RxTextView.textChanges(etCode);
        Observable<CharSequence> newPassword = RxTextView.textChanges(etNewPassword);
        Observable.combineLatest(phone, code, newPassword, new Function3<CharSequence, CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean apply(CharSequence phone, CharSequence code, CharSequence newPassword) throws Exception {
                ivDelete.setVisibility(phone.length() > 0 ? View.VISIBLE : View.GONE);
                //验证手机
                isPhone = RegexUtils.isMobileSimple(phone);
                tvGetCode.setTextColor(isPhone && !isCountdown ? 0xff0099cc : 0xffcccccc);
                //验证验证码
                return isPhone && code.length() == 6 && newPassword.toString().trim().length() >= 6;
            }
        }).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                btnOk.setEnabled(aBoolean);
            }
        });


        RxCompoundButton.checkedChanges(cbPasswordMode).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                //切换密码显示和隐藏
                etNewPassword.setTransformationMethod(aBoolean ? HideReturnsTransformationMethod.getInstance() : PasswordTransformationMethod.getInstance());
                etNewPassword.setSelection(etNewPassword.getText().length());
            }
        });
    }


    @OnClick({R.id.iv_show_delete, R.id.tv_get_code, R.id.btn_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_show_delete://删除手机号
                etPhone.setText("");
                break;
            case R.id.tv_get_code://获取验证码
                if (tvGetCode.getCurrentTextColor() == 0xff0099cc) {
                    mPresenter.onGetCode(etPhone.getText().toString());
                }
                break;
            case R.id.btn_ok:
                //提交网络
                mPresenter.onSetPassword(etPhone.getText().toString(), etCode.getText().toString(), etNewPassword.getText().toString());
                break;
        }
    }

    @Override
    public void setCountdownText(long time) {
        isCountdown = time > 0;
        tvGetCode.setTextColor(isPhone && !isCountdown ? 0xff0099cc : 0xffcccccc);
        tvGetCode.setText(isCountdown ? String.format(Locale.getDefault(), "%d秒后重试", time) : "获取验证码");
    }
}
