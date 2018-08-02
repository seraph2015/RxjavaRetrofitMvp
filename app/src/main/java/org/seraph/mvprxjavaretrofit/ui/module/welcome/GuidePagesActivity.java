package org.seraph.mvprxjavaretrofit.ui.module.welcome;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.Gravity;

import com.blankj.utilcode.util.ConvertUtils;
import com.tmall.ultraviewpager.UltraViewPager;

import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.databinding.WelcomeActivityGuidePagesBinding;
import org.seraph.mvprxjavaretrofit.ui.module.base.ABaseActivity;
import org.seraph.mvprxjavaretrofit.ui.module.main.MainActivity;

import javax.inject.Inject;

/**
 * 引导页
 * date：2017/5/3 10:40
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class GuidePagesActivity extends ABaseActivity<GuidePagesActivityContract.Presenter> implements GuidePagesActivityContract.View {


    WelcomeActivityGuidePagesBinding binding;

    @Override
    protected void initContextView() {
        binding = DataBindingUtil.setContentView(this,R.layout.welcome_activity_guide_pages);
    }

    @Inject
    GuidePagesActivityPresenter mPresenter;

    @Override
    protected GuidePagesActivityContract.Presenter getMVPPresenter() {
        return mPresenter;
    }

    @Inject
    UltraPagerAdapter mUltraPagerAdapter;

    @Override
    public void initCreate(@Nullable Bundle savedInstanceState) {
        initViewPager();
        mPresenter.start();
    }

    private void initViewPager() {
        mUltraPagerAdapter.setOnClickListener(position-> mPresenter.onItemClick(position));
        binding.ultraViewpager.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
        //UltraPagerAdapter 绑定子view到UltraViewPager
        binding.ultraViewpager.setOffscreenPageLimit(3);
        binding.ultraViewpager.setAdapter(mUltraPagerAdapter);
        //内置indicator初始化
        binding.ultraViewpager.initIndicator();
        //设置indicator样式
        binding.ultraViewpager.getIndicator()
                .setOrientation(UltraViewPager.Orientation.HORIZONTAL)
                .setFocusColor(Color.WHITE)
                .setNormalColor(Color.GRAY)
                .setRadius((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics()));
        //设置indicator对齐方式
        binding.ultraViewpager.getIndicator().setMargin(10, 10, 10, ConvertUtils.dp2px(15));
        binding.ultraViewpager.getIndicator().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
        //构造indicator,绑定到UltraViewPager
        binding.ultraViewpager.getIndicator().build();
    }


    @Override
    public void jumpNextActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void setImageList(Integer[] images) {
        mUltraPagerAdapter.setListImage(images);
        binding.ultraViewpager.refresh();
    }

}
