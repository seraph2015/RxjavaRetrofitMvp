package org.seraph.mvprxjavaretrofit.ui.module.login;

import android.content.Intent;

import androidx.databinding.DataBindingUtil;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.Nullable;

import android.view.View;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.jakewharton.rxbinding3.widget.RxTextView;

import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.databinding.ActLoginBinding;
import org.seraph.mvprxjavaretrofit.ui.module.base.ABaseActivity;
import org.seraph.mvprxjavaretrofit.ui.module.login.contract.LoginContract;
import org.seraph.mvprxjavaretrofit.ui.module.login.presenter.LoginPresenter;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * 登录
 * date：2017/10/25 10:28
 * author：Seraph
 * mail：417753393@qq.com
 **/
public class LoginActivity extends ABaseActivity implements LoginContract.View {

    ActLoginBinding binding;

    @Override
    protected void initContextView() {
        binding = DataBindingUtil.setContentView(this, R.layout.act_login);
        binding.setActivity(this);
    }

    @Inject
    LoginPresenter presenter;

    @Override
    protected LoginContract.Presenter getMVPPresenter() {
        presenter.setView(this);
        return presenter;
    }

    @Override
    public void initCreate(@Nullable Bundle savedInstanceState) {
        binding.appbar.toolbar.setTitle("登录");
        overridePendingTransition(R.anim.anim_slide_in_from_bottom, R.anim.anim_null);
        initListener();
        presenter.start();
    }

    /**
     * 初始化监听
     */
    private void initListener() {
        Bitmap bitmap = ConvertUtils.drawable2Bitmap(getResources().getDrawable(R.mipmap.welcome_guide_pages_three));
        binding.ivUiBg.setImageBitmap(ImageUtils.fastBlur(bitmap, 1, 15, true));

        Observable<CharSequence> loginPhone = RxTextView.textChanges(binding.etPhone);
        Observable<CharSequence> loginPassword = RxTextView.textChanges(binding.etPassword);

        Observable.combineLatest(loginPhone, loginPassword,
                (phone, password) -> (RegexUtils.isMobileSimple(phone) && password.length() >= 6))
                .observeOn(AndroidSchedulers.mainThread())
                .as(bindLifecycle())
                .subscribe(aBoolean -> binding.btnLogin.setEnabled(aBoolean));
    }


    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_show_delete://删除手机号
                binding.etPhone.setText("");
                break;
            case R.id.tv_registered://注册
                startActivity(new Intent(this, RegisteredActivity.class));
                break;
            case R.id.tv_forget_password://忘记密码
                startActivity(new Intent(this, ResetPasswordActivity.class));
                break;
            case R.id.btn_login://登录
                presenter.onLogin(binding.etPhone.getText().toString().trim(), binding.etPassword.getText().toString().trim());
                break;
        }
    }


    @Override
    public void setUserLoginInfo(String username, String passWord) {
        binding.etPhone.setText(username);
        binding.etPassword.setText(passWord);
        binding.etPassword.setSelection(passWord.length());
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_null, R.anim.anim_slide_out_to_bottom);
    }
}
