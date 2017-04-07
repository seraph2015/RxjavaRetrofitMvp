package org.seraph.mvprxjavaretrofit.ui.module.base;

import android.content.Context;
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
public abstract class BaseActivity extends AppCompatActivity {

    @Inject
    protected CustomLoadingDialog mLoadingDialog;

    public abstract int getContextView();

    public abstract void initCreate(@Nullable Bundle savedInstanceState);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContextView());
        ButterKnife.bind(this);
        setupActivityComponent();
        initCreate(savedInstanceState);
    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void showLoading(String str) {
        mLoadingDialog.setDialogMessage(str);
    }

    public void hideLoading() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

    public abstract void setupActivityComponent();

    public Context getContext() {
        return this;
    }

}
