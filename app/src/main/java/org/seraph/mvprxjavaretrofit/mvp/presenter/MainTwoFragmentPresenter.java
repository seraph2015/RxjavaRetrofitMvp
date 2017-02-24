package org.seraph.mvprxjavaretrofit.mvp.presenter;

import android.content.Context;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.seraph.mvprxjavaretrofit.activity.MainActivity;
import org.seraph.mvprxjavaretrofit.adapter.ImageListAdapter;
import org.seraph.mvprxjavaretrofit.mvp.model.BaiduImageBean;
import org.seraph.mvprxjavaretrofit.mvp.view.BaseView;
import org.seraph.mvprxjavaretrofit.mvp.view.MainTwoFragmentView;
import org.seraph.mvprxjavaretrofit.request.ApiService;
import org.seraph.mvprxjavaretrofit.request.exception.ServerErrorCode;
import org.seraph.mvprxjavaretrofit.utlis.FileTools;
import org.seraph.mvprxjavaretrofit.utlis.Tools;

import java.util.ArrayList;
import java.util.List;

/**
 * 第二页P
 * date：2017/2/21 17:10
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class MainTwoFragmentPresenter extends BasePresenter {

    private MainActivity mainActivity;


    @Override
    public void onAttach(Context context) {
        mainActivity = (MainActivity) context;
        super.onAttach(context);
    }

    private MainTwoFragmentView mView;

    @Override
    public void attachView(BaseView view) {
        super.attachView(view);
        mView = (MainTwoFragmentView) view;
    }

    private Subscription subscription;

    private String title;

    private ImageListAdapter imageListAdapter;

    private List<BaiduImageBean.BaiduImage> listImage = new ArrayList<>();

    private int pageNo = 0;

    private String searchKeyWord;

    public void initData() {
        title = " Search Image";
        setTitle(title);
        imageListAdapter = new ImageListAdapter(mainActivity, listImage);
        mView.setImageAdapter(imageListAdapter);
    }


    @Override
    public void restoreData() {
        super.restoreData();
        setTitle(title);
        upDataToolbarAlpha(0);
    }

    public void setTitle(String title) {
        mainActivity.setTitle(title);
    }

    /**
     * 更新头部背景透明度
     *
     * @param percentScroll 进度百分比
     */
    private void upDataToolbarAlpha(float percentScroll) {
        mainActivity.mPresenter.upDataToolbarAlpha(percentScroll);
    }

    public void getCacheFilePath() {
        mView.setTextView(FileTools.getCacheDirectory(mainActivity, null).getPath());
    }

    public void startPicassoToImage() {
        searchKeyWord = mView.getSearchKeyWord();
        if (Tools.isNull(searchKeyWord)) {
            mainActivity.showToast("serach is null!");
            return;
        }
        getBaiduImageList(searchKeyWord, 1);
    }

    public void loadMoreImage() {
        getBaiduImageList(searchKeyWord, ++pageNo);
    }

    private void getBaiduImageList(String keyWord, int requestPageNo) {
        //获取图片地址 百度图片 标签objURL
        ApiService.doBaiduImage(Tools.getBaiduImagesUrl(keyWord, requestPageNo)).doOnSubscribe(subscription -> mView.showLoading()).map(baiduImageBean -> baiduImageBean.imgs).subscribe(new Subscriber<List<BaiduImageBean.BaiduImage>>() {

            @Override
            public void onSubscribe(Subscription s) {
                subscription = s;
                subscription.request(1);
            }

            @Override
            public void onNext(List<BaiduImageBean.BaiduImage> baiduImages) {
                mView.hideLoading();
                if (requestPageNo == 1) {
                    listImage.clear();
                }
                listImage.addAll(baiduImages);
                //如果请求回来的数据是等于请求的分页数据，则显示加载更多按钮，反正显示没有更多数据
                if (baiduImages.size() < 48) {
                    mView.setListFootText(0);
                } else {
                    mView.setListFootText(1);
                }
                pageNo = requestPageNo;
                subscription.request(1);
            }

            @Override
            public void onError(Throwable t) {
                mView.hideLoading();
                ServerErrorCode.errorCodeToMessageShow(t, mainActivity);
            }

            @Override
            public void onComplete() {
                mView.hideLoading();
                imageListAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void unSubscribe() {
        super.unSubscribe();
        if (subscription != null) {
            subscription.cancel();
        }
    }


    public void onItemClick(int position) {
        if (position > listImage.size()) {
            return;
        }
        listImage.get(position - 1).isShowTitle = !listImage.get(position - 1).isShowTitle;
        imageListAdapter.notifyDataSetChanged();
    }
}
