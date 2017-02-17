package org.seraph.mvprxjavaretrofit.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.mvp.presenter.BasePresenter;
import org.seraph.mvprxjavaretrofit.mvp.presenter.MainPresenter;
import org.seraph.mvprxjavaretrofit.mvp.view.MainView;

/**
 * 主界面
 * date：2017/2/15 11:24
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class MainActivity extends BaseActivity implements MainView {


   // @BindView(R.id.btn_show)
    Button btnShow;
    //@BindView(R.id.tv_content)
    TextView tvContent;

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
        btnShow = (Button) findViewById(R.id.btn_show);
        tvContent = (TextView) findViewById(R.id.tv_content);
        btnShow.setOnClickListener(this::onClick);
    }

    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_show:
                mMainPresenter.getNetWork();
                break;
        }
    }

    @Override
    public void setTextViewValue(CharSequence charSequence) {
        tvContent.setText(charSequence);
    }

}
