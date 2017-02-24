package org.seraph.mvprxjavaretrofit.activity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.mvp.presenter.BaseActivityPresenter;
import org.seraph.mvprxjavaretrofit.mvp.view.BaseActivityView;
import org.seraph.mvprxjavaretrofit.utlis.Tools;
import org.seraph.mvprxjavaretrofit.views.CustomLoadingDialog;

import butterknife.ButterKnife;

/**
 * 所有的activity的父类继承，包含的一系列的常用操作
 * date：2017/2/15 09:09
 * author：xiongj
 * mail：417753393@qq.com
 **/
public abstract class BaseActivity extends AppCompatActivity implements BaseActivityView {
    /**
     * 最高父布局
     */
    FrameLayout flRoot;
    AppBarLayout appBar;
    Toolbar toolbar;
    /**
     * 添加布局的父布局
     */
    private LinearLayout contentMain;

    protected abstract int getContextView();

    protected abstract BaseActivityPresenter getPresenter();

    protected abstract void init(Bundle savedInstanceState);

    //声明基类中的Presenter
    protected BaseActivityPresenter mPresenter;

    protected CustomLoadingDialog loadingDialog;

    /**
     * 默认不是沉浸模式
     */
    private boolean isImmersion = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //固定配置
        initConfig();
        //设置可选默认配置
        mPresenter.initBaseDefaultConfig();
        //开始界面
        init(savedInstanceState);
    }

    @Override
    public void initConfig() {
        initUIBind();
        initMVPBind();
        initToolbarBar();
        initLoadingDialog();
    }

    /**
     * 1.绑定界面UI
     */
    private void initUIBind() {
        setContentView(R.layout.activity_base_materal_design);
        contentMain = ButterKnife.findById(this, R.id.content_main);
        View view = LayoutInflater.from(this).inflate(getContextView(), contentMain, false);
        contentMain.addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        flRoot = ButterKnife.findById(this, R.id.fl_root);
        appBar = ButterKnife.findById(this, R.id.appbar);
        toolbar = ButterKnife.findById(this, R.id.toolbar);
    }


    /**
     * 2.绑定mvp关系
     */
    private void initMVPBind() {
        //获取Presenter持有
        mPresenter = getPresenter();
        //绑定mView
        mPresenter.attachView(this);
    }


    /**
     * 3.初始化toolbar状态栏
     */
    private void initToolbarBar() {
        toolbar.setPadding(0, getResources().getDimensionPixelSize(R.dimen.status_bar_height), 0, 0);
        contentMain.setPadding(0, getResources().getDimensionPixelSize(R.dimen.status_bar_height), 0, 0);
        setTitle("");
    }

    /**
     * 4.初始化loadingDialog
     */
    private void initLoadingDialog() {
        loadingDialog = new CustomLoadingDialog(this);
        loadingDialog.setOnDismissListener((DialogInterface dialog) -> mPresenter.unSubscribe());
    }


    @Override
    public void setTitle(String title) {
        if (Tools.isNull(title)) {
            toolbar.setTitle(" ");
        } else {
            toolbar.setTitle(title);
        }
        setSupportActionBar(toolbar);
    }

    @Override
    public void setBackListener(View.OnClickListener onClickListener) {
        if (onClickListener != null) {
            toolbar.setNavigationOnClickListener(onClickListener);
        }
    }

    @Override
    public void setBackIcon(@DrawableRes int resId) {
        toolbar.setNavigationIcon(resId);
    }

    @Override
    public void showToolBarNavigation() {
        setBackListener(v -> viewFinish());
        setBackIcon(android.R.drawable.ic_input_delete);
    }

    @Override
    public void showLoading() {
        if (loadingDialog != null) {
            loadingDialog.show();
        }
    }

    @Override
    public void hideLoading() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }


    @Override
    public void showToast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToast(int strId) {
        Toast.makeText(this, strId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSnackBar(String str) {
        Snackbar.make(contentMain, str, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showSnackBar(int strId) {
        Snackbar.make(contentMain, strId, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void setBackgroundColor(@ColorInt int backgroundColor) {
        flRoot.setBackgroundColor(backgroundColor);
    }

    @Override
    public void setBackgroundResource(@DrawableRes int resId) {
        flRoot.setBackgroundResource(resId);
    }

    /**
     * 设置toolbar背景颜色
     */
    @Override
    public void setToolBarBackgroundColor(@ColorInt int color) {
        appBar.setBackgroundColor(color);
        toolbar.setBackgroundColor(color);
        //如果有透明色，则去掉阴影
        if ((color >>> 24 > 0 || color == Color.TRANSPARENT) && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setElevation(0);
            //去掉5.0之后的特效阴影
            appBar.setOutlineProvider(null);
        }
    }

    /**
     * 设置toolbar背景图片
     */
    @Override
    public void setToolBarBackgroundResource(@DrawableRes int resId) {
        toolbar.setBackgroundResource(resId);
    }


    @Override
    public void showToolBar() {
        toolbar.setVisibility(View.VISIBLE);
        appBar.setVisibility(View.VISIBLE);
        //根据是否侵入模式进行状态栏设置
        setStatusBarImmersionMode(isImmersion);
    }

    @Override
    public void hideToolBar() {
        toolbar.setVisibility(View.GONE);
        appBar.setVisibility(View.GONE);
        contentMain.setPadding(0, 0, 0, 0);
        Tools.setMargins(contentMain, 0, 0, 0, 0);
    }

    /**
     * 是否侵入状态栏
     */
    @Override
    public boolean isImmersionMode() {
        return isImmersion;
    }

    /**
     * 设置状态栏模式（侵入）
     */
    @Override
    public void setStatusBarImmersionMode(boolean isImmersion) {
        this.isImmersion = isImmersion;
        if (isImmersion) {
            contentMain.setPadding(0, 0, 0, 0);
            Tools.setMargins(contentMain, 0, 0, 0, 0);
        } else {
            contentMain.setPadding(0, getResources().getDimensionPixelSize(R.dimen.contact_toolbar_h), 0, 0);
            Tools.setMargins(contentMain, 0, 0, 0, 0);
        }
    }


    @Override
    public void viewFinish() {
        finish();
    }


    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
        super.onDestroy();
    }


    @Override
    protected void onStart() {
        if (mPresenter != null) {
            mPresenter.onStart();
        }
        super.onStart();

    }

    @Override
    protected void onStop() {
        if (mPresenter != null) {
            mPresenter.onStop();
        }
        super.onStop();
    }

    @Override
    protected void onPause() {
        if (mPresenter != null) {
            mPresenter.onPause();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        if (mPresenter != null) {
            mPresenter.onResume();
        }
        super.onResume();
    }

    @Override
    protected void onRestart() {
        if (mPresenter != null) {
            mPresenter.onRestart();
        }
        super.onRestart();
    }
}
