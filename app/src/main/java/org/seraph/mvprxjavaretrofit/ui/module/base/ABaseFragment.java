package org.seraph.mvprxjavaretrofit.ui.module.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hwangjr.rxbus.RxBus;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.AutoDisposeConverter;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import org.seraph.mvprxjavaretrofit.ui.views.CustomLoadingDialog;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import dagger.android.support.AndroidSupportInjection;

/**
 * 基础的Fragment继承父类
 * 说明参见{@link ABaseActivity}
 * date：2017/2/20 16:40
 * author：xiongj
 * mail：417753393@qq.com
 **/
public abstract class ABaseFragment extends Fragment implements IABaseContract.IBaseView {

    protected abstract View initDataBinding(LayoutInflater inflater, ViewGroup container);

    protected abstract IABaseContract.ABasePresenter getMVPPresenter();

    public abstract void initCreate(@Nullable Bundle savedInstanceState);

    @Inject
    CustomLoadingDialog mLoadingDialog;

    private IABaseContract.ABasePresenter mPresenter;

    //自动解绑rxjava（在指定的生命周期）
    public <T> AutoDisposeConverter<T> bindLifecycle(Lifecycle.Event untilEvent) {
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, untilEvent));
    }

    //自动解绑rxjava（在结束的时候）
    public <T> AutoDisposeConverter<T> bindLifecycle() {
        return bindLifecycle(Lifecycle.Event.ON_DESTROY);
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //数据绑定
        RxBus.get().register(this);
        mPresenter = getMVPPresenter();
        return initDataBinding(inflater, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initCreate(savedInstanceState);
    }

    @Override
    public CustomLoadingDialog showLoading() {
        return showLoading("");
    }

    @Override
    public CustomLoadingDialog showLoading(String str) {
        mLoadingDialog.setDialogMessage(str);
        return mLoadingDialog;
    }

    @Override
    public void hideLoading() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

    @Override
    public void finish() {
        if (getActivity() != null) {
            getActivity().finish();
        }
    }

    @Override
    public void onDestroyView() {
        RxBus.get().unregister(this);
        super.onDestroyView();
        if (mPresenter != null) {
            mPresenter.onDetach();
        }
    }


}
