package org.seraph.mvprxjavaretrofit.ui.module.common.photolist;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.Menu;

import com.blankj.utilcode.util.ToastUtils;
import com.jakewharton.rxbinding2.support.v7.widget.RxToolbar;

import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.databinding.CommonActivityLocalImageListBinding;
import org.seraph.mvprxjavaretrofit.ui.module.base.ABaseActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * 显示并且选择本地图片的类，结果以List传出
 */
public class LocalImageListActivity extends ABaseActivity<LocalImageListContract.Presenter> implements LocalImageListContract.View {


    CommonActivityLocalImageListBinding binding;

    @Override
    protected void initContextView() {
        binding = DataBindingUtil.setContentView(this, R.layout.common_activity_local_image_list);
    }

    @Inject
    LocalImageListPresenter mPresenter;

    LocalImageListAdapter mImageListAdapter;

    @Inject
    GridLayoutManager manager;

    @Override
    protected LocalImageListContract.Presenter getMVPPresenter() {
        return mPresenter;
    }


    public static final String SELECT_PATH = "select_path";

    public static final String SELECTED_PATH = "selected_path";

    @Override
    public void initCreate(@Nullable Bundle savedInstanceState) {
        initRxBinding();
        mPresenter.setIntent(getIntent());
        mPresenter.start();
    }

    private void initRxBinding() {
        mImageListAdapter = new LocalImageListAdapter(this);
        binding.appbar.tvToolbarTitle.setText("选择图片");
        RxToolbar.itemClicks(binding.appbar.toolbar).subscribe(menuItem -> {
            mPresenter.save(mImageListAdapter.getSelectedPathList());
        });
        binding.rvLocalImageList.setLayoutManager(manager);
        binding.rvLocalImageList.setAdapter(mImageListAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_common_activity_local_image, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void setQueryImageList(List<LocalImageBean> localImageBeen) {
        if (localImageBeen == null || localImageBeen.size() == 0) {
            ToastUtils.showShort("获取照片失败");
            return;
        }
        mImageListAdapter.replaceData(localImageBeen);
    }

    @Override
    public void setResult(ArrayList<String> arrayList) {
        Intent intent = new Intent();
        intent.putStringArrayListExtra(SELECT_PATH, arrayList);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void setSelectedPath(ArrayList<String> arrayList) {
        //回填已经选择的数据
        mImageListAdapter.setSelectedPathList(arrayList);
    }


}
