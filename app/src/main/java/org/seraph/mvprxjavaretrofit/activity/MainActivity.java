package org.seraph.mvprxjavaretrofit.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.mvp.presenter.BasePresenter;
import org.seraph.mvprxjavaretrofit.mvp.presenter.MainPresenter;
import org.seraph.mvprxjavaretrofit.mvp.view.MainView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 主界面
 * date：2017/2/15 11:24
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class MainActivity extends BaseActivity implements MainView {


    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_db_user)
    TextView tvDbUser;


    @Override
    protected int getContextView() {
        return R.layout.activity_mian;
    }

    private MainPresenter mMainPresenter;

    @Override
    protected BasePresenter getPresenter() {
        mMainPresenter = new MainPresenter();
        return mMainPresenter;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("主页");
        toolbar.setLogo(R.mipmap.icon_logo);
    }

    @OnClick(value = {R.id.btn_show, R.id.tv_content, R.id.btn_sava_db, R.id.tv_query_user, R.id.tv_clean_user})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_show:
                mMainPresenter.getNetWork();
                break;
            case R.id.tv_content:
                mMainPresenter.switchToolBarVisibility();
                break;
            case R.id.btn_sava_db:
                mMainPresenter.saveUserInfo();
                break;
            case R.id.tv_query_user:
                mMainPresenter.queryUserInfo();
                break;
            case R.id.tv_clean_user:
                mMainPresenter.cleanUserInfo();
                break;
        }
    }

    @Override
    public void setTextViewValue(CharSequence charSequence) {
        tvContent.setText(charSequence);
    }

    @Override
    public void setUserTextViewValue(CharSequence charSequence) {
        tvDbUser.setText(charSequence);
    }

}
