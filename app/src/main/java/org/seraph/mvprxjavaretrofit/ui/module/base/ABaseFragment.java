package org.seraph.mvprxjavaretrofit.ui.module.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hwangjr.rxbus.RxBus;
import com.trello.rxlifecycle2.components.support.RxFragment;

import org.seraph.mvprxjavaretrofit.ui.views.CustomLoadingDialog;
import org.seraph.mvprxjavaretrofit.utlis.FontUtils;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 基础的Fragment继承父类
 * 说明参见{@link ABaseActivity}
 * 如果fragment需要获取依赖activity的component，则activity必须实现{@link IComponent}接口，
 *
 * @see #getComponent(Class) 获取实现了{@link IComponent}接口的依赖Activity的Component连接类，
 * 以便在此类的继承子类{@link #setupActivityComponent()}中进行依赖注入。
 * date：2017/2/20 16:40
 * author：xiongj
 * mail：417753393@qq.com
 **/
public abstract class ABaseFragment<P extends IABaseContract.ABaseFragmentPresenter> extends RxFragment implements IABaseContract.IBaseFragmentView {

    public abstract int getContextView();

    public abstract void setupActivityComponent();

    protected abstract P getMVPPresenter();

    public abstract void initCreate(@Nullable Bundle savedInstanceState);

    @Inject
    protected CustomLoadingDialog mLoadingDialog;

    private Unbinder unbinder;

    protected P mPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(getContextView(), container, false);
        FontUtils.injectFont(rootView);
        unbinder = ButterKnife.bind(this, rootView);
        RxBus.get().register(this);
        setupActivityComponent();
        initMVP();
        return rootView;
    }

    /**
     * 根据不同类型获取对应依赖Activity的Component
     */
    @SuppressWarnings("unchecked")
    protected <C> C getComponent(Class<C> componentType) {
        return componentType.cast(((IComponent<C>) getActivity()).getComponent());
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
        mPresenter.onDetach();
        unbinder.unbind();
        super.onDestroyView();
    }


}
