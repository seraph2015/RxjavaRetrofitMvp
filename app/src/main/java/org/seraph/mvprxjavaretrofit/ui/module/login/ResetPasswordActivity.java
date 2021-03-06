package org.seraph.mvprxjavaretrofit.ui.module.login;

import androidx.databinding.DataBindingUtil;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.Nullable;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.jakewharton.rxbinding3.widget.RxCompoundButton;
import com.jakewharton.rxbinding3.widget.RxTextView;

import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.databinding.ActLoginResetPasswordBinding;
import org.seraph.mvprxjavaretrofit.ui.module.base.ABaseActivity;
import org.seraph.mvprxjavaretrofit.ui.module.login.contract.ResetPasswordContract;
import org.seraph.mvprxjavaretrofit.ui.module.login.presenter.ResetPasswordPresenter;

import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * 修改/找回密码
 * date：2017/10/25 18:18
 * author：Seraph
 * mail：417753393@qq.com
 **/
public class ResetPasswordActivity extends ABaseActivity implements ResetPasswordContract.View {


    ActLoginResetPasswordBinding binding;

    @Override
    protected void initContextView() {
        binding = DataBindingUtil.setContentView(this, R.layout.act_login_reset_password);
        binding.setActivity(this);
    }

    @Inject
    ResetPasswordPresenter presenter;

    @Override
    protected ResetPasswordContract.Presenter getMVPPresenter() {
        presenter.setView(this);
        return presenter;
    }

    @Override
    public void initCreate(@Nullable Bundle savedInstanceState) {
        binding.appbar.toolbar.setTitle("重置密码");
        initListener();
    }

    private boolean isPhone;
    private boolean isCountdown;

    private void initListener() {
        Bitmap bitmap = ConvertUtils.drawable2Bitmap(getResources().getDrawable(R.mipmap.welcome_guide_pages_one));
        binding.ivUiBg.setImageBitmap(ImageUtils.fastBlur(bitmap, 1, 15, true));
        Observable<CharSequence> phone = RxTextView.textChanges(binding.etPhone);
        Observable<CharSequence> code = RxTextView.textChanges(binding.etCode);
        Observable<CharSequence> newPassword = RxTextView.textChanges(binding.etNewPassword);
        Observable.combineLatest(phone, code, newPassword, (phone2, code2, newPassword2) -> {
            binding.ivShowDelete.setVisibility(phone2.length() > 0 ? View.VISIBLE : View.GONE);
            //验证手机
            isPhone = RegexUtils.isMobileSimple(phone2);
            binding.tvGetCode.setTextColor(isPhone && !isCountdown ? 0xff0099cc : 0xffcccccc);
            //验证验证码
            return isPhone && code2.length() == 6 && newPassword2.toString().trim().length() >= 6;
        }).as(bindLifecycle())
                .subscribe(aBoolean -> binding.btnOk.setEnabled(aBoolean));


        RxCompoundButton.checkedChanges(binding.cbPasswordMode).as(bindLifecycle()).subscribe(aBoolean -> {
            //切换密码显示和隐藏
            binding.etNewPassword.setTransformationMethod(aBoolean ? HideReturnsTransformationMethod.getInstance() : PasswordTransformationMethod.getInstance());
            binding.etNewPassword.setSelection(binding.etNewPassword.getText().length());
        });
    }


    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_show_delete://删除手机号
                binding.etPhone.setText("");
                break;
            case R.id.tv_get_code://获取验证码
                if (binding.tvGetCode.getCurrentTextColor() == 0xff0099cc) {
                    presenter.onGetCode(binding.etPhone.getText().toString());
                }
                break;
            case R.id.btn_ok:
                //提交网络
                presenter.onSetPassword(binding.etPhone.getText().toString(), binding.etCode.getText().toString(), binding.etNewPassword.getText().toString());
                break;
        }
    }

    @Override
    public void setCountdownText(long time) {
        isCountdown = time > 0;
        binding.tvGetCode.setTextColor(isPhone && !isCountdown ? 0xff0099cc : 0xffcccccc);
        binding.tvGetCode.setText(isCountdown ? String.format(Locale.getDefault(), "%d秒后重试", time) : "获取验证码");
    }
}
