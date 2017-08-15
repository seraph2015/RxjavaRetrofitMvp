package org.seraph.mvprxjavaretrofit.ui.module.common.photolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.jakewharton.rxbinding2.support.v7.widget.RxToolbar;

import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.di.component.DaggerCommonComponent;
import org.seraph.mvprxjavaretrofit.di.component.base.AppComponent;
import org.seraph.mvprxjavaretrofit.di.module.CommonModule;
import org.seraph.mvprxjavaretrofit.di.module.base.ActivityModule;
import org.seraph.mvprxjavaretrofit.ui.module.base.ABaseActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * 显示并且选择本地图片的类，结果以List传出
 */
public class LocalImageListActivity extends ABaseActivity<LocalImageListContract.View, LocalImageListContract.Presenter> implements LocalImageListContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_toolbar_title)
    TextView toolbarTitle;

    @BindView(R.id.rv_local_image_list)
    RecyclerView mRvList;


    @Override
    public int getContextView() {
        return R.layout.common_activity_local_image_list;
    }

    @Inject
    LocalImageListPresenter mPresenter;

    @Inject
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
        toolbarTitle.setText("选择图片");
        RxToolbar.itemClicks(toolbar).subscribe(new Consumer<MenuItem>() {
            @Override
            public void accept(MenuItem menuItem) throws Exception {
                mPresenter.save(mImageListAdapter.getSelectedPathList());
            }
        });
        mRvList.setLayoutManager(manager);
        mRvList.setAdapter(mImageListAdapter);
    }

    @Override
    public void setupActivityComponent(AppComponent appComponent, ActivityModule activityModule) {
        DaggerCommonComponent.builder()
                .appComponent(appComponent)
                .activityModule(activityModule)
                .commonModule(new CommonModule())
                .build()
                .inject(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.common_activity_local_image_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void setQueryImageList(List<LocalImageBean> localImageBeen) {
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
