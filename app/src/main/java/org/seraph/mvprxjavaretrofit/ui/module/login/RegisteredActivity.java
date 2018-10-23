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
import com.jakewharton.rxbinding2.widget.RxCompoundButton;
import com.jakewharton.rxbinding2.widget.RxTextView;

import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.databinding.ActLoginRegisteredBinding;
import org.seraph.mvprxjavaretrofit.ui.module.base.ABaseActivity;
import org.seraph.mvprxjavaretrofit.ui.module.login.contract.RegisteredContract;
import org.seraph.mvprxjavaretrofit.ui.module.login.presenter.RegisteredPresenter;

import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * 注册
 * date：2017/10/25 12:37
 * author：Seraph
 * mail：417753393@qq.com
 **/
public class RegisteredActivity extends ABaseActivity<RegisteredContract.Presenter> implements RegisteredContract.View {


    ActLoginRegisteredBinding binding;

    @Override
    protected void initContextView() {
        binding = DataBindingUtil.setContentView(this, R.layout.act_login_registered);
        binding.setActivity(this);
    }

    @Inject
    RegisteredPresenter mPresenter;

    @Override
    protected RegisteredContract.Presenter getMVPPresenter() {
        return mPresenter;
    }

    @Override
    public void initCreate(@Nullable Bundle savedInstanceState) {
        binding.appbar.tvToolbarTitle.setText("注册");
        initListener();
    }

    private boolean isPhone;
    private boolean isCountdown;

    private void initListener() {
        Bitmap bitmap = ConvertUtils.drawable2Bitmap(getResources().getDrawable(R.mipmap.welcome_guide_pages_two));
        binding.ivUiBg.setImageBitmap(ImageUtils.fastBlur(bitmap, 1, 15, true));
        Observable<CharSequence> phone = RxTextView.textChanges(binding.etPhone);//手机号
        Observable<CharSequence> code = RxTextView.textChanges(binding.etCode);//验证码
        Observable<CharSequence> password = RxTextView.textChanges(binding.etPassword);//密码
        Observable<Boolean> isOk = RxCompoundButton.checkedChanges(binding.cbZc);//注册协议
        Observable.combineLatest(phone, code, password, isOk, (phone2, code2, password2, isOk2) -> {
            binding.ivShowDelete.setVisibility(phone2.length() > 0 ? View.VISIBLE : View.GONE);
            //验证手机
            isPhone = RegexUtils.isMobileSimple(phone2);

            binding.tvGetCode.setTextColor(isPhone && !isCountdown ? 0xff0099cc : 0xffcccccc);
            //验证验证码
            return isPhone && code2.length() == 6 && password2.toString().trim().length() >= 6 && isOk2;
        })
                .observeOn(AndroidSchedulers.mainThread())
                .as(bindLifecycle())
                .subscribe(aBoolean -> binding.btnOk.setEnabled(aBoolean));

        RxCompoundButton.checkedChanges(binding.cbPasswordMode).as(bindLifecycle()).subscribe(aBoolean -> {
            //切换密码显示和隐藏
            binding.etPassword.setTransformationMethod(aBoolean ? HideReturnsTransformationMethod.getInstance() : PasswordTransformationMethod.getInstance());
            binding.etPassword.setSelection(binding.etPassword.getText().length());
        });
    }


    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_show_delete://删除手机号
                binding.etPhone.setText("");
                break;
            case R.id.tv_get_code://获取验证码
                if (binding.tvGetCode.getCurrentTextColor() == 0xff0099cc) {
                    mPresenter.onGetCode(binding.etPhone.getText().toString().trim());
                }
                break;
            case R.id.tv_agreement://注册协议
                mPresenter.doLookAgreement();
                break;
            case R.id.btn_ok://提交注册
                mPresenter.onRegistered(binding.etPhone.getText().toString().trim(), binding.etCode.getText().toString().trim(), binding.etPassword.getText().toString().trim());
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
