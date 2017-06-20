package org.seraph.mvprxjavaretrofit.ui.module.base;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import org.seraph.mvprxjavaretrofit.ui.views.CustomLoadingDialog;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * 所有的activity的父类继承，包含的一系列的常用操作
 * date：2017/2/15 09:09
 * author：xiongj
 * mail：417753393@qq.com
 **/
public abstract class BaseActivity2<V extends IBaseContract.IBaseView, P extends IBaseContract.IBasePresenter<V>> extends AppCompatActivity implements IBaseContract.IBaseView {

    @Inject
    protected CustomLoadingDialog mLoadingDialog;

    public abstract int getContextView();

    public abstract void initCreate(@Nullable Bundle savedInstanceState);

    protected V v;

    protected P p;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContextView());
        ButterKnife.bind(this);
        setupActivityComponent();
        initCreate(savedInstanceState);
        initMVP();
        p.setView(v);
    }

    protected void initMVP() {
        this.v = getMVPView();
        this.p = getMVPPresenter();
    }

    protected abstract P getMVPPresenter();

    protected abstract V getMVPView();

    public abstract void setupActivityComponent();

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading(String str) {
        mLoadingDialog.setDialogMessage(str);
        mLoadingDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                p.unSubscribe();
            }
        });
    }

    @Override
    public void hideLoading() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

}
