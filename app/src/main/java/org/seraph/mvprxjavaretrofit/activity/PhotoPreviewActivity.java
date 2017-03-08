package org.seraph.mvprxjavaretrofit.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.Menu;
import android.view.MenuItem;

import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.adapter.PhotoPreviewAdapter;
import org.seraph.mvprxjavaretrofit.mvp.model.PhotoPreviewBean;
import org.seraph.mvprxjavaretrofit.mvp.presenter.BaseActivityPresenter;
import org.seraph.mvprxjavaretrofit.mvp.presenter.PhotoPreviewPresenter;
import org.seraph.mvprxjavaretrofit.mvp.view.PhotoPreviewView;
import org.seraph.mvprxjavaretrofit.permission.PermissionsActivity;
import org.seraph.mvprxjavaretrofit.preference.AppConstant;
import org.seraph.mvprxjavaretrofit.views.zoom.ImageViewTouchViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;


/**
 * 图片查看器
 */
public class PhotoPreviewActivity extends BaseActivity implements PhotoPreviewView {

    private ImageViewTouchViewPager mPhotoPreview;

    @Override
    protected int getContextView() {
        return R.layout.activity_photo_preview;
    }

    private PhotoPreviewPresenter mPresenter;
    /**
     * 图片列表数据
     */
    public final static String PHOTO_LIST = "photoList";
    /**
     * 当前选中的图片
     */
    public final static String CURRENT_POSITION = "currentPosition";

    @Override
    protected BaseActivityPresenter getPresenter() {
        mPresenter = new PhotoPreviewPresenter();
        return mPresenter;
    }

    private ArrayList<PhotoPreviewBean> mPhotoList;
    /**
     * 当前第几张照片
     */
    private int currentPosition = 0;

    @Override
    protected void init(Bundle savedInstanceState) {
        mPhotoPreview = ButterKnife.findById(this, R.id.vp_photo_preview);
        mPhotoPreview.setOnPageSelectedListener(this::onPageSelected);

        mPhotoList = (ArrayList<PhotoPreviewBean>) getIntent().getSerializableExtra(PhotoPreviewActivity.PHOTO_LIST);
        currentPosition = getIntent().getIntExtra(PhotoPreviewActivity.CURRENT_POSITION, 0);

        mPresenter.initData();
    }

    private void onPageSelected(int position) {
        //翻页并且保存页码
        mPresenter.onPageSelected(position);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void setPagerAdapter(PhotoPreviewAdapter mPhotoPreviewAdapter) {
        mPhotoPreview.setAdapter(mPhotoPreviewAdapter);
    }

    @Override
    public void setCurrentItem(int currentPosition) {
        mPhotoPreview.setCurrentItem(currentPosition);
        this.currentPosition = currentPosition;
    }

    @Override
    public void setMenuClick() {
        toolbar.setOnMenuItemClickListener(this::onMenuItem);
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
        PermissionsActivity.startActivityForResult(this, AppConstant.CODE_REQUEST_PERMISSIONS, permissions);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.act_photo_preview, menu);
        return super.onCreateOptionsMenu(menu);
    }


    private boolean onMenuItem(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_save_image:
                mPresenter.saveImage();
                break;
        }
        return false;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConstant.CODE_REQUEST_PERMISSIONS) {
            mPresenter.onActivityResult(resultCode);
        }
    }
}
