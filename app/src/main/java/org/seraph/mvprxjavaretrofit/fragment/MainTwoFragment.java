package org.seraph.mvprxjavaretrofit.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.jakewharton.rxbinding2.widget.RxAdapterView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.activity.MainActivity;
import org.seraph.mvprxjavaretrofit.activity.PhotoPreviewActivity;
import org.seraph.mvprxjavaretrofit.mvp.model.PhotoPreviewBean;
import org.seraph.mvprxjavaretrofit.mvp.presenter.BasePresenter;
import org.seraph.mvprxjavaretrofit.mvp.presenter.MainTwoFragmentPresenter;
import org.seraph.mvprxjavaretrofit.mvp.view.MainTwoFragmentView;
import org.seraph.mvprxjavaretrofit.views.GoTopListView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;

/**
 * 第二页
 * date：2017/2/21 17:06
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class MainTwoFragment extends BaseFragment implements MainTwoFragmentView, View.OnClickListener {


    private GoTopListView listImageView;

    private ImageView ivGoTop;

    @Override
    protected int getContextView() {
        return R.layout.fragment_two;
    }

    MainTwoFragmentPresenter mPresenter;

    @Override
    protected BasePresenter getPresenter() {
        mPresenter = new MainTwoFragmentPresenter();
        return mPresenter;
    }

    MainActivity mainActivity;

    TextView tvCache;
    Button getCache;
    Button picassoImage;
    EditText inputSearch;
    Button btnSearchHistory;
    TextView tvMore;

    @Override
    protected void init(Bundle savedInstanceState) {
        mainActivity = (MainActivity) getActivity();
        listImageView = ButterKnife.findById(rootView, R.id.lv_images);
        ivGoTop = ButterKnife.findById(rootView, R.id.iv_go_top);
        listImageView.setScrollListener(ivGoTop);
        addListHeadView();
        RxTextView.textChanges(inputSearch).subscribe(new Consumer<CharSequence>() {
            @Override
            public void accept(CharSequence charSequence) throws Exception {
                if (charSequence.length() > 0) {
                    picassoImage.setEnabled(true);
                } else {
                    picassoImage.setEnabled(false);
                }

            }
        });

        RxAdapterView.itemClicks(listImageView).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                mPresenter.onItemClick(integer);
            }
        });


        mPresenter.initData();
    }

    private void addListHeadView() {
        View headView = LayoutInflater.from(getActivity()).inflate(R.layout.list_head, listImageView, false);
        tvCache = ButterKnife.findById(headView, R.id.tv_cache);
        getCache = ButterKnife.findById(headView, R.id.btn_get_cache);
        picassoImage = ButterKnife.findById(headView, R.id.btn_picasso_image);
        inputSearch = ButterKnife.findById(headView, R.id.et_search_keyword);
        btnSearchHistory = ButterKnife.findById(headView, R.id.btn_search_history);
        getCache.setOnClickListener(this);
        btnSearchHistory.setOnClickListener(this);
        picassoImage.setOnClickListener(this);
        listImageView.addHeaderView(headView);
    }


    private void addListFootView() {
        View footView = LayoutInflater.from(getActivity()).inflate(R.layout.list_foot_view, listImageView, false);
        tvMore = ButterKnife.findById(footView, R.id.tv_more);
        tvMore.setOnClickListener(this);
        listImageView.addFooterView(footView);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_get_cache:
                mPresenter.showCacheFilePath();
                break;
            case R.id.btn_picasso_image:
                mPresenter.startPicassoToImage();
                break;
            case R.id.btn_search_history:
                mPresenter.searchHistory();
                break;
            case R.id.tv_more:
                if (tvMore.getTag() != null && (int) tvMore.getTag() == 1) {
                    mPresenter.loadMoreImage();
                }
                break;
        }
    }


    @Override
    public void setTextView(CharSequence charSequence) {
        tvCache.setText(charSequence);
    }

    @Override
    public void setImageAdapter(ListAdapter adapter) {
        listImageView.setAdapter(adapter);
    }

    @Override
    public String getSearchKeyWord() {
        return inputSearch.getText().toString().trim();
    }

    @Override
    public void setSearchInput(String item) {
        inputSearch.setText(item);
    }

    @Override
    public void setTitle(String title) {
        mainActivity.setTitle(title);
    }

    @Override
    public void upDataToolbarAlpha(int i) {
        mainActivity.mPresenter.upDataToolbarAlpha(i);
    }

    @Override
    public void startPhotoPreview(ArrayList<PhotoPreviewBean> photoList, int position) {
        Intent intent = new Intent(getActivity(), PhotoPreviewActivity.class);
        intent.putExtra(PhotoPreviewActivity.PHOTO_LIST, photoList);
        intent.putExtra(PhotoPreviewActivity.CURRENT_POSITION, position - 1);
        startActivity(intent);
    }


    @Override
    public void setListFootText(int type) {
        if (listImageView.getFooterViewsCount() == 0) {
            addListFootView();
        }
        if (type == 1) {
            tvMore.setTextColor(Color.parseColor("#193DFF"));
            tvMore.setText("加载更多");
        } else {
            tvMore.setTextColor(Color.parseColor("#cccccc"));
            tvMore.setText("没有更多");
        }
        tvMore.setTag(type);
    }


}
