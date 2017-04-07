package org.seraph.mvprxjavaretrofit.ui.module.common.photopreview;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.jakewharton.rxbinding2.support.v7.widget.RxToolbar;

import org.seraph.mvprxjavaretrofit.AppApplication;
import org.seraph.mvprxjavaretrofit.AppConfig;
import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.di.component.common.DaggerPhotoPreviewComponent;
import org.seraph.mvprxjavaretrofit.di.module.ActivityModule;
import org.seraph.mvprxjavaretrofit.ui.module.base.BaseActivity;
import org.seraph.mvprxjavaretrofit.ui.module.common.permission.PermissionsActivity;
import org.seraph.mvprxjavaretrofit.ui.views.zoom.ImageViewTouchViewPager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.functions.Consumer;


/**
 * 图片查看器
 */
public class PhotoPreviewActivity extends BaseActivity implements PhotoPreviewContract.View, ImageViewTouchViewPager.OnPageSelectedListener {

    @BindView(R.id.vp_photo_preview)
    ImageViewTouchViewPager vpPhotoPreview;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;

    @Override
    public int getContextView() {
        return R.layout.activity_photo_preview;
    }

    @Override
    public void setupActivityComponent() {
        DaggerPhotoPreviewComponent.builder().appComponent(AppApplication.getAppComponent()).activityModule(new ActivityModule(this)).build().inject(this);
    }


    @Inject
    PhotoPreviewPresenter mPresenter;
    /**
     * 图片列表数据
     */
    public final static String PHOTO_LIST = "photoList";
    /**
     * 当前选中的图片
     */
    public final static String CURRENT_POSITION = "currentPosition";


    private ArrayList<PhotoPreviewBean> mPhotoList;
    /**
     * 当前第几张照片
     */
    private int currentPosition = 0;


    @Override
    public void initCreate(@Nullable Bundle savedInstanceState) {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        setSupportActionBar(toolbar);
        mPhotoList = (ArrayList<PhotoPreviewBean>) getIntent().getSerializableExtra(PhotoPreviewActivity.PHOTO_LIST);
        currentPosition = getIntent().getIntExtra(PhotoPreviewActivity.CURRENT_POSITION, 0);
        vpPhotoPreview.setOnPageSelectedListener(this);
        vpPhotoPreview.setOffscreenPageLimit(5);
        initListener();
        rxBinding();
        mPresenter.setView(this);
        mPresenter.start();

    }

    private void initListener() {
        mLoadingDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mPresenter.unSubscribe();
            }
        });
    }

    private void rxBinding() {
        RxToolbar.itemClicks(toolbar).subscribe(new Consumer<MenuItem>() {
            @Override
            public void accept(MenuItem menuItem) throws Exception {
                switch (menuItem.getItemId()) {
                    case R.id.action_save_image:
                        mPresenter.saveImage();
                        break;
                }
            }
        });
        RxToolbar.navigationClicks(toolbar).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                finish();
            }
        });
    }


    @Override
    public void showToast(String str) {
        super.showToast(str);
    }

    @Override
    public void showLoading(String str) {
        super.showLoading(str);
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void setPagerAdapter(PhotoPreviewAdapter mPhotoPreviewAdapter) {
        vpPhotoPreview.setAdapter(mPhotoPreviewAdapter);
    }

    /**
     * 跳转到指定页和保存当前
     */
    @Override
    public void onPageSelected(int position) {
        toolbar.setTitle("图片预览" + "（" + (position + 1) + "/" + getPhotoList().size() + "）");
        vpPhotoPreview.setCurrentItem(position);
        this.currentPosition = position;
    }

    @Override
    public List<PhotoPreviewBean> getPhotoList() {
        return mPhotoList;
    }

    @Override
    public int getCurrentPosition() {
        return currentPosition;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void startPermissionsActivity(String[] permissions) {
        PermissionsActivity.startActivityForResult(this, AppConfig.CODE_REQUEST_PERMISSIONS, permissions);
    }

    @Override
    public void switchToolBarVisibility() {
        //切换头部的隐藏和显示
        if (appbar.getVisibility() == View.GONE) {
            appbar.setVisibility(View.VISIBLE);
        } else {
            appbar.setVisibility(View.GONE);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.act_photo_preview, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConfig.CODE_REQUEST_PERMISSIONS) {
            mPresenter.onActivityResult(resultCode);
        }
    }


}
