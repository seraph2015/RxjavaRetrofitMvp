package org.seraph.mvprxjavaretrofit.ui.module.main;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hwangjr.rxbus.RxBus;

import org.seraph.mvprxjavaretrofit.AppConstants;
import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.databinding.TestFragmentThreeBinding;
import org.seraph.mvprxjavaretrofit.di.component.MainActivityComponent;
import org.seraph.mvprxjavaretrofit.ui.module.base.ABaseFragment;
import org.seraph.mvprxjavaretrofit.ui.module.main.contract.MainThreeFragmentContract;
import org.seraph.mvprxjavaretrofit.ui.module.main.presenter.MainThreeFragmentPresenter;

import javax.inject.Inject;


/**
 * 第三页
 * date：2017/2/20 16:38
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class MainThreeFragment extends ABaseFragment<MainThreeFragmentContract.Presenter> implements MainThreeFragmentContract.View {


    TestFragmentThreeBinding binding;

    @Inject
    MainThreeFragmentPresenter mPresenter;


    @Override
    protected MainThreeFragmentContract.Presenter getMVPPresenter() {
        return mPresenter;
    }

    @Override
    protected View initDataBinding(LayoutInflater inflater, ViewGroup container) {
        binding = DataBindingUtil.inflate(inflater, R.layout.test_fragment_three, container, false);
        binding.setFragment(this);
        return binding.getRoot();
    }

    @Override
    public void setupActivityComponent() {
        getComponent(MainActivityComponent.class).inject(this);
    }

    @Override
    public void initCreate(@Nullable Bundle savedInstanceState) {

    }


    public void setTextView(CharSequence charSequence) {
        binding.tvHttpsValue.setText(charSequence);
    }

    @Override
    public void setDownloadProgressRate(long downloadSize, long max) {
        if (dialog != null) {
            dialog.setMax((int) max);
            dialog.setProgress((int) downloadSize);
        }
    }



    ProgressDialog dialog;

    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_https:
                mPresenter.post12306Https();
                break;
            case R.id.btn_jump:
                RxBus.get().post(AppConstants.RxBusAction.TAG_MAIN_MENU, 1);
                break;

            case R.id.btn_show:
                dialog = new ProgressDialog(getActivity());
                dialog.setIndeterminate(false);
                dialog.setCancelable(false);
                dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                dialog.setButton(DialogInterface.BUTTON_POSITIVE, "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        mPresenter.cancelDownload();
                    }
                });
                dialog.show();
                mPresenter.startDownload();
                break;
        }

    }


}
