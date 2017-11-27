package org.seraph.mvprxjavaretrofit.ui.module.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.hwangjr.rxbus.RxBus;

import org.seraph.mvprxjavaretrofit.AppConstants;
import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.di.component.MainActivityComponent;
import org.seraph.mvprxjavaretrofit.ui.module.base.ABaseFragment;
import org.seraph.mvprxjavaretrofit.ui.module.main.contract.MainThreeFragmentContract;
import org.seraph.mvprxjavaretrofit.ui.module.main.presenter.MainThreeFragmentPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 第三页
 * date：2017/2/20 16:38
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class MainThreeFragment extends ABaseFragment<MainThreeFragmentContract.View, MainThreeFragmentContract.Presenter> implements MainThreeFragmentContract.View {

    @BindView(R.id.tv_https_value)
    TextView tvHttpsValue;

    TextView nllText;

    @Inject
    MainThreeFragmentPresenter mPresenter;

    @Override
    public int getContextView() {
        return R.layout.test_fragment_three;
    }

    @Override
    protected MainThreeFragmentContract.Presenter getMVPPresenter() {
        return mPresenter;
    }

    @Override
    public void setupActivityComponent() {
        getComponent(MainActivityComponent.class).inject(this);
    }

    @Override
    public void initCreate(@Nullable Bundle savedInstanceState) {

    }


    public void setTextView(CharSequence charSequence) {
        tvHttpsValue.setText(charSequence);
    }

    @OnClick(value = {R.id.btn_https, R.id.btn_jump,R.id.btn_bk})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_https:
                mPresenter.post12306Https();
                break;
            case R.id.btn_jump:
                RxBus.get().post(AppConstants.RxBusAction.TAG_MAIN_MENU, 1);
                break;
            case R.id.btn_bk:
                nllText.setText("奔溃测试");
                break;

        }

    }


}
