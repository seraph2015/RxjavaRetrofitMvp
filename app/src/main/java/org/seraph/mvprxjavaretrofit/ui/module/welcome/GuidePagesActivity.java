package org.seraph.mvprxjavaretrofit.ui.module.welcome;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.Gravity;

import com.blankj.utilcode.util.ConvertUtils;
import com.tmall.ultraviewpager.UltraViewPager;

import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.di.component.DaggerWelcomeComponent;
import org.seraph.mvprxjavaretrofit.di.component.base.AppComponent;
import org.seraph.mvprxjavaretrofit.di.module.base.ActivityModule;
import org.seraph.mvprxjavaretrofit.ui.module.base.ABaseActivity;
import org.seraph.mvprxjavaretrofit.ui.module.main.MainActivity;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * 引导页
 * date：2017/5/3 10:40
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class GuidePagesActivity extends ABaseActivity<GuidePagesActivityContract.View, GuidePagesActivityContract.Presenter> implements GuidePagesActivityContract.View {

    @BindView(R.id.ultra_viewpager)
    UltraViewPager ultraViewPager;

    @Override
    public int getContextView() {
        return R.layout.welcome_activity_guide_pages;
    }

    @Inject
    GuidePagesActivityPresenter mPresenter;

    @Override
    protected GuidePagesActivityContract.Presenter getMVPPresenter() {
        return mPresenter;
    }

    @Override
    public void setupActivityComponent(AppComponent appComponent, ActivityModule activityModule) {
        DaggerWelcomeComponent.builder().appComponent(appComponent).activityModule(activityModule).build().inject(this);
    }

    @Inject
    UltraPagerAdapter mUltraPagerAdapter;

    @Override
    public void initCreate(@Nullable Bundle savedInstanceState) {
        initViewPager();
        mPresenter.start();
    }

    private void initViewPager() {
        mUltraPagerAdapter.setOnClickListener(new UltraPagerAdapter.PagerItemClickListener() {
            @Override
            public void onItemClick(int position) {
                mPresenter.onItemClick(position);
            }
        });
        ultraViewPager.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
        //UltraPagerAdapter 绑定子view到UltraViewPager
        ultraViewPager.setOffscreenPageLimit(3);
        ultraViewPager.setAdapter(mUltraPagerAdapter);
        //内置indicator初始化
        ultraViewPager.initIndicator();
        //设置indicator样式
        ultraViewPager.getIndicator()
                .setOrientation(UltraViewPager.Orientation.HORIZONTAL)
                .setFocusColor(Color.WHITE)
                .setNormalColor(Color.GRAY)
                .setRadius((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics()));
        //设置indicator对齐方式
        ultraViewPager.getIndicator().setMargin(10, 10, 10, ConvertUtils.dp2px(15));
        ultraViewPager.getIndicator().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
        //构造indicator,绑定到UltraViewPager
        ultraViewPager.getIndicator().build();
    }


    @Override
    public void jumpNextActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void setImageList(Integer[] images) {
        mUltraPagerAdapter.setListImage(images);
        ultraViewPager.setAdapter(mUltraPagerAdapter);
    }

}
