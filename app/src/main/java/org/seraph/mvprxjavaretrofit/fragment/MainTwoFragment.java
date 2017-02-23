package org.seraph.mvprxjavaretrofit.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.mvp.presenter.BasePresenter;
import org.seraph.mvprxjavaretrofit.mvp.presenter.MainTwoFragmentPresenter;
import org.seraph.mvprxjavaretrofit.mvp.view.MainTwoFragmentView;

import butterknife.ButterKnife;

/**
 * 第二页
 * date：2017/2/21 17:06
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class MainTwoFragment extends BaseFragment implements MainTwoFragmentView {


    private ListView imageView;

    @Override
    protected int getContextView() {
        return R.layout.fragment_two;
    }

    MainTwoFragmentPresenter mainTwoFragmentPresenter;

    @Override
    protected BasePresenter getPresenter() {
        mainTwoFragmentPresenter = new MainTwoFragmentPresenter();
        return mainTwoFragmentPresenter;
    }

    TextView tvCache;
    Button getCache;
    Button picassoImage;
    EditText inputSearch;

    @Override
    protected void init(Bundle savedInstanceState) {
        imageView = ButterKnife.findById(rootView, R.id.lv_images);

        addListHeadView();
        mainTwoFragmentPresenter.initData();
    }


    private void addListHeadView() {
        View headView = LayoutInflater.from(getActivity()).inflate(R.layout.list_head, imageView, false);
        tvCache = ButterKnife.findById(headView, R.id.tv_cache);
        getCache = ButterKnife.findById(headView, R.id.btn_get_cache);
        picassoImage = ButterKnife.findById(headView, R.id.btn_picasso_image);
        inputSearch = ButterKnife.findById(headView, R.id.et_search_keyword);
        getCache.setOnClickListener(this::onClick);
        picassoImage.setOnClickListener(this::onClick);
        imageView.addHeaderView(headView);
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_get_cache:
                mainTwoFragmentPresenter.getCacheFilePath();
                break;
            case R.id.btn_picasso_image:
                mainTwoFragmentPresenter.startPicassoToImage();
                break;
        }
    }


    @Override
    public void setTextView(CharSequence charSequence) {
        tvCache.setText(charSequence);
    }

    @Override
    public void setImageAdapter(ListAdapter adapter) {
        imageView.setAdapter(adapter);
    }

    @Override
    public String getSearchKeyWord() {
        return inputSearch.getText().toString().trim();
    }


}
