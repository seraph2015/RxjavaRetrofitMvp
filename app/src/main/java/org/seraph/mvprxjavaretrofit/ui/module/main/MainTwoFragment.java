package org.seraph.mvprxjavaretrofit.ui.module.main;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.rxbinding2.widget.RxAdapterView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import org.seraph.mvprxjavaretrofit.AppApplication;
import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.di.component.main.DaggerMainFragmentTwoComponent;
import org.seraph.mvprxjavaretrofit.di.module.MainFragmentTwoModule;
import org.seraph.mvprxjavaretrofit.ui.module.base.BaseFragment;
import org.seraph.mvprxjavaretrofit.ui.module.base.adapter.BaseListAdapter;
import org.seraph.mvprxjavaretrofit.ui.module.common.photopreview.PhotoPreviewActivity;
import org.seraph.mvprxjavaretrofit.ui.module.common.photopreview.PhotoPreviewBean;
import org.seraph.mvprxjavaretrofit.ui.module.main.adapter.ImageListBaiduAdapter;
import org.seraph.mvprxjavaretrofit.ui.module.main.contract.MainTwoFragmentContract;
import org.seraph.mvprxjavaretrofit.ui.module.main.model.ImageBaiduBean;
import org.seraph.mvprxjavaretrofit.ui.module.main.presenter.MainTwoFragmentPresenter;
import org.seraph.mvprxjavaretrofit.ui.views.GoTopListView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 * 第二页
 * date：2017/2/21 17:06
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class MainTwoFragment extends BaseFragment implements MainTwoFragmentContract.View {


    @BindView(R.id.lv_images)
    GoTopListView lvImages;
    @BindView(R.id.iv_go_top)
    ImageView ivGoTop;
    @Inject
    MainTwoFragmentPresenter mPresenter;

    @Inject
    ImageListBaiduAdapter mImageListBaiduAdapter;


    class HeadViewHolder {

        @BindView(R.id.tv_cache)
        TextView tvCache;
        @BindView(R.id.et_search_keyword)
        EditText etSearchKeyword;
        @BindView(R.id.btn_picasso_image)
        Button btnPicassoImage;


        HeadViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        @OnClick(value = {R.id.btn_get_cache, R.id.btn_search_history, R.id.btn_picasso_image})
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_get_cache:
                    mPresenter.showCacheFilePath();
                    break;
                case R.id.btn_search_history:
                    mPresenter.searchHistory();
                    break;
                case R.id.btn_picasso_image:
                    mPresenter.startPicassoToImage();
                    break;
            }
        }


    }

    private HeadViewHolder headViewholder;

    @Override
    public int getContentView() {
        return R.layout.test_fragment_two;
    }

    @Override
    public void setupActivityComponent() {
        DaggerMainFragmentTwoComponent.builder().appComponent(AppApplication.getAppComponent()).mainFragmentTwoModule(new MainFragmentTwoModule(this)).build().inject(this);
    }

    @Override
    public void initCreate(@Nullable Bundle savedInstanceState) {
        lvImages.setAdapter(mImageListBaiduAdapter);
        lvImages.setScrollListener(ivGoTop);
        addListHeadView();
        rxBinding();
        initListener();
        mPresenter.setView(this);
        mPresenter.start();

    }

    protected void rxBinding() {
        RxTextView.textChanges(headViewholder.etSearchKeyword).subscribe(new Consumer<CharSequence>() {
            @Override
            public void accept(CharSequence charSequence) throws Exception {
                if (charSequence.length() > 0) {
                    headViewholder.btnPicassoImage.setEnabled(true);
                } else {
                    headViewholder.btnPicassoImage.setEnabled(false);
                }
            }
        });

        RxAdapterView.itemClicks(lvImages).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                mPresenter.onItemClick(integer);
            }
        });
    }

    private void addListHeadView() {
        View headView = LayoutInflater.from(getActivity()).inflate(R.layout.test_fragment_two_list_head, lvImages, false);
        headViewholder = new HeadViewHolder(headView);
        lvImages.addHeaderView(headView);
    }

    @Override
    public void setTextView(CharSequence charSequence) {
        headViewholder.tvCache.setText(charSequence);
    }


    @Override
    public String getSearchKeyWord() {
        return headViewholder.etSearchKeyword.getText().toString().trim();
    }


    @Override
    public void setSearchInput(String item) {
        headViewholder.etSearchKeyword.setText(item);
    }


    @Override
    public void startPhotoPreview(ArrayList<PhotoPreviewBean> photoList, int position) {
        PhotoPreviewActivity.startPhotoPreview(getActivity(), photoList, position - 1, PhotoPreviewActivity.IMAGE_TYPE_NETWORK);
    }

    @Override
    public void requestData(List<ImageBaiduBean.BaiduImage> listImage, boolean isMore) {
        //请求的数据
        mImageListBaiduAdapter.addAllListData(listImage);
        mImageListBaiduAdapter.setIsMoreData(isMore);
    }


    private void initListener() {
        mLoadingDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mPresenter.unSubscribe();
            }
        });
        mImageListBaiduAdapter.setLoadMoreListener(new BaseListAdapter.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPresenter.loadMoreImage();
            }
        });
    }


}
