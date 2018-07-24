package org.seraph.mvprxjavaretrofit.ui.module.base;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hwangjr.rxbus.RxBus;
import com.trello.rxlifecycle2.components.support.RxFragment;

import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.ui.views.CustomLoadingDialog;
import org.seraph.mvprxjavaretrofit.utlis.FontUtils;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjection;

/**
 * 基础的Fragment继承父类
 * 说明参见{@link ABaseActivity}
 * date：2017/2/20 16:40
 * author：xiongj
 * mail：417753393@qq.com
 **/
public abstract class ABaseFragment<P extends IABaseContract.ABaseFragmentPresenter> extends RxFragment implements IABaseContract.IBaseFragmentView {

    protected abstract View initDataBinding(LayoutInflater inflater, ViewGroup container);

    protected abstract P getMVPPresenter();

    public abstract void initCreate(@Nullable Bundle savedInstanceState);

    @Inject
    protected CustomLoadingDialog mLoadingDialog;

    protected P mPresenter;

    @Override
    public void onAttach(Activity activity) {
        AndroidSupportInjection.inject(this);
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = initDataBinding(inflater, container);
        View appbar = rootView.findViewById(R.id.appbar);
        if (appbar != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            appbar.setOutlineProvider(null);
        }
        FontUtils.injectFont(rootView);
        //数据绑定
        RxBus.get().register(this);
        initMVP();
        return rootView;
    }

    @SuppressWarnings("unchecked")
    private void initMVP() {
        try {
            if (getMVPPresenter() == null) {
                return;
            }
            mPresenter = getMVPPresenter();
            mPresenter.setView(this);
        } catch (ClassCastException e) {
            throw new RuntimeException("子类必须实现AIBaseContract.IBaseFragmentView接口");
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
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
        getActivity().finish();
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
