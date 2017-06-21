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
public abstract class ABaseActivity<V extends IBaseContract.IBaseView, P extends IBaseContract.IBasePresenter<V>> extends AppCompatActivity implements IBaseContract.IBaseView {

    @Inject
    protected CustomLoadingDialog mLoadingDialog;

    public abstract int getContextView();

    protected abstract P getMVPPresenter();

    public abstract void setupActivityComponent();

    public abstract void initCreate(@Nullable Bundle savedInstanceState);

    //在base里面初始化和设置一些通用操作
    private P p;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContextView());
        ButterKnife.bind(this);
        setupActivityComponent();
        initMVP();
        initCreate(savedInstanceState);
    }

    @SuppressWarnings("unchecked")
    private void initMVP() {
        p = getMVPPresenter();
        try {
            p.setView((V) this);
        } catch (ClassCastException e) {
            try {
                throw new Exception("子类必须实现IBaseContract.IBaseView接口");
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }


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
                mLoadingDialog.setOnDismissListener(null);
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
