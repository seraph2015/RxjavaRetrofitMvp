package org.seraph.mvprxjavaretrofit.ui.module.main;

import android.app.AlertDialog;
import android.content.DialogInterface;

import org.reactivestreams.Subscription;
import org.seraph.mvprxjavaretrofit.data.local.db.gen.DaoSession;
import org.seraph.mvprxjavaretrofit.data.local.db.gen.SearchHistoryTableDao;
import org.seraph.mvprxjavaretrofit.data.local.db.table.SearchHistoryTable;
import org.seraph.mvprxjavaretrofit.data.network.ApiManager;
import org.seraph.mvprxjavaretrofit.ui.module.base.BaseNetWorkSubscriber;
import org.seraph.mvprxjavaretrofit.ui.module.common.photopreview.PhotoPreviewBean;
import org.seraph.mvprxjavaretrofit.utlis.FileUtils;
import org.seraph.mvprxjavaretrofit.utlis.Tools;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * 第二页P
 * date：2017/2/21 17:10
 * author：xiongj
 * mail：417753393@qq.com
 **/
class MainTwoFragmentPresenter implements MainTwoFragmentContract.Presenter {

    private MainTwoFragmentContract.View mView;

    @Override
    public void setView(MainTwoFragmentContract.View view) {
        this.mView = view;
    }

    private ApiManager mApiManager;

    private DaoSession mDaoSession;

    @Inject
    MainTwoFragmentPresenter(ApiManager apiManager, DaoSession daoSession) {
        this.mApiManager = apiManager;
        this.mDaoSession = daoSession;
    }

    private Subscription mSubscription;


    private List<ImageBaiduBean.BaiduImage> listImage = new ArrayList<>();
    /**
     * 搜索历史
     */
    private List<SearchHistoryTable> listSearch;

    private int pageNo = 0;

    private String searchKeyWord;


    @Override
    public void start() {

    }

    @Override
    public void unSubscribe() {
        if (mSubscription != null) {
            mSubscription.cancel();
        }
    }

    @Override
    public void showCacheFilePath() {
        mView.setTextView(FileUtils.getCacheDirectory(mView.getContext(), null).getPath());
    }

    @Override
    public void searchHistory() {
        //查询本地数据搜索历史（时间倒叙）
        listSearch = mDaoSession.getSearchHistoryTableDao().queryBuilder().where(SearchHistoryTableDao.Properties.UserId.eq(-1)).orderDesc(SearchHistoryTableDao.Properties.SearchTime).list();
        if (listSearch.size() == 0) {
            mView.showToast("暂无搜索历史");
            return;
        }
        showSearchHistory();
    }


    /**
     * 显示选择框
     */
    private void showSearchHistory() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mView.getContext());
        final String[] items = new String[listSearch.size() + 1];
        for (int i = 0; i < listSearch.size(); i++) {
            items[i] = listSearch.get(i).getSearchKey();
        }
        items[listSearch.size()] = "清除历史记录";
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == listSearch.size()) {
                    deleteAllSearchDB();
                } else {
                    mView.setSearchInput(items[which]);
                }
            }
        }).show();
    }

    @Override
    public void startPicassoToImage() {
        searchKeyWord = mView.getSearchKeyWord();
        if (Tools.isNull(searchKeyWord)) {
            mView.showToast("search is null!");
            return;
        }
        //保存搜索到本地数据库
        saveSearchToDB();
        mView.showLoading("正在搜索");
        getBaiduImageList(searchKeyWord, 1);
    }

    @Override
    public void loadMoreImage() {
        getBaiduImageList(searchKeyWord, ++pageNo);
    }

    private void getBaiduImageList(String keyWord, final int requestPageNo) {
        //获取图片地址 百度图片 标签objURL
        mApiManager.doBaiduImage(Tools.getBaiduImagesUrl(keyWord, requestPageNo))
                .doOnSubscribe(new Consumer<Subscription>() {
                    @Override
                    public void accept(Subscription subscription) throws Exception {
                        mSubscription = subscription;
                    }
                })
                .map(new Function<ImageBaiduBean, List<ImageBaiduBean.BaiduImage>>() {
                    @Override
                    public List<ImageBaiduBean.BaiduImage> apply(ImageBaiduBean imageBaiduBean) throws Exception {
                        return imageBaiduBean.imgs;
                    }
                })
                .subscribe(new BaseNetWorkSubscriber<List<ImageBaiduBean.BaiduImage>, MainTwoFragmentContract.View>(mView) {
                    @Override
                    public void onSuccess(List<ImageBaiduBean.BaiduImage> baiduImages) {
                        if (requestPageNo == 1) {
                            listImage.clear();
                        }
                        listImage.addAll(baiduImages);
                        //如果请求回来的数据是等于请求的分页数据，则显示加载更多按钮，反正显示没有更多数据
                        if (baiduImages.size() < 48) {
                            mView.noMoreData();
                        }
                        pageNo = requestPageNo;

                        mView.requestData(listImage);
                    }

                    @Override
                    public void onError(String errStr) {
                        mView.showToast(errStr);
                    }

                });
    }



    @Override
    public void onItemClick(int position) {
        if (position > listImage.size()) {
            return;
        }
        ArrayList<PhotoPreviewBean> photoList = new ArrayList<>();
        for (ImageBaiduBean.BaiduImage baiduImage : listImage) {
            PhotoPreviewBean photoPreviewBean = new PhotoPreviewBean();
            photoPreviewBean.objURL = baiduImage.objURL;
            photoPreviewBean.type = baiduImage.type;
            photoPreviewBean.width = baiduImage.width;
            photoPreviewBean.height = baiduImage.height;
            photoList.add(photoPreviewBean);
        }
        mView.startPhotoPreview(photoList, position);
    }

    /**
     * 保存到数据库
     */
    private void saveSearchToDB() {
        //清理之前在同一用户种同一类型的重复的key
        SearchHistoryTableDao searchHistoryTableDao = mDaoSession.getSearchHistoryTableDao();
        List<SearchHistoryTable> historyTableList = searchHistoryTableDao.queryBuilder().where(SearchHistoryTableDao.Properties.UserId.eq(-1), SearchHistoryTableDao.Properties.Type.eq("Search Image"), SearchHistoryTableDao.Properties.SearchKey.eq(searchKeyWord)).list();
        for (SearchHistoryTable searchHistoryTable : historyTableList) {
            searchHistoryTableDao.delete(searchHistoryTable);
        }
        SearchHistoryTable searchHistoryTable = new SearchHistoryTable();
        searchHistoryTable.setSearchKey(searchKeyWord);
        searchHistoryTable.setSearchTime(System.currentTimeMillis());
        searchHistoryTable.setType("Search Image");
        searchHistoryTable.setUserId(-1);
        mDaoSession.getSearchHistoryTableDao().save(searchHistoryTable);
    }


    /**
     * 清理当前用户search image类型历史数据库
     */
    private void deleteAllSearchDB() {
        SearchHistoryTableDao searchHistoryTableDao = mDaoSession.getSearchHistoryTableDao();
        List<SearchHistoryTable> historyTableList = searchHistoryTableDao.queryBuilder().where(SearchHistoryTableDao.Properties.UserId.eq(-1), SearchHistoryTableDao.Properties.Type.eq("Search Image")).list();
        for (SearchHistoryTable searchHistoryTable : historyTableList) {
            searchHistoryTableDao.delete(searchHistoryTable);
        }

    }

}
