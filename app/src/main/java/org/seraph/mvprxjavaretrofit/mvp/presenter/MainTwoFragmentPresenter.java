package org.seraph.mvprxjavaretrofit.mvp.presenter;

import android.app.AlertDialog;
import android.content.DialogInterface;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.seraph.mvprxjavaretrofit.App;
import org.seraph.mvprxjavaretrofit.adapter.ImageListAdapter;
import org.seraph.mvprxjavaretrofit.db.gen.SearchHistoryTableDao;
import org.seraph.mvprxjavaretrofit.db.table.SearchHistoryTable;
import org.seraph.mvprxjavaretrofit.mvp.model.BaiduImageBean;
import org.seraph.mvprxjavaretrofit.mvp.model.PhotoPreviewBean;
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

    /**
     * 搜索历史
     */
    private List<SearchHistoryTable> listSearch;


    public void initData() {
        title = " Search Image";
        mView.setTitle(title);
        imageListAdapter = new ImageListAdapter(mView.getContext(), listImage);
        mView.setImageAdapter(imageListAdapter);
    }


    @Override
    public void restoreData() {
        super.restoreData();
        mView.setTitle(title);
        mView.upDataToolbarAlpha(0);
    }


    public void showCacheFilePath() {
        mView.setTextView(FileTools.getCacheDirectory(mView.getContext(), null).getPath());
    }

    public void searchHistory() {
        //查询本地数据搜索历史（时间倒叙）
        listSearch = App.getDaoSession().getSearchHistoryTableDao().queryBuilder().where(SearchHistoryTableDao.Properties.UserId.eq(-1)).orderDesc(SearchHistoryTableDao.Properties.SearchTime).list();
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
        builder.setItems(items, (DialogInterface dialog, int which) -> {
            if (which == listSearch.size()) {
                deleteAllSearchDB();
            } else {
                mView.setSearchInput(items[which]);
            }
        }).show();

    }


    public void startPicassoToImage() {
        searchKeyWord = mView.getSearchKeyWord();
        if (Tools.isNull(searchKeyWord)) {
            mView.showToast("serach is null!");
            return;
        }
        //保存搜索到本地数据库
        saveSearchToDB();
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
                mView.showToast(ServerErrorCode.errorCodeToMessageShow(t));
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
        ArrayList<PhotoPreviewBean> photoList = new ArrayList<>();
        for (BaiduImageBean.BaiduImage baiduImage : listImage) {
            PhotoPreviewBean photoPreviewBean = new PhotoPreviewBean();
            photoPreviewBean.objURL = baiduImage.objURL;
            photoPreviewBean.type = baiduImage.type;
            photoPreviewBean.width = baiduImage.width;
            photoPreviewBean.height = baiduImage.height;
            photoList.add(photoPreviewBean);
        }
        mView.startPhotoPreview(photoList, position);

//        listImage.get(position - 1).isShowTitle = !listImage.get(position - 1).isShowTitle;
//        imageListAdapter.notifyDataSetChanged();

    }

    /**
     * 保存到数据库
     */
    private void saveSearchToDB() {
        //清理之前在同一用户种同一类型的重复的key
        SearchHistoryTableDao searchHistoryTableDao = App.getDaoSession().getSearchHistoryTableDao();
        List<SearchHistoryTable> historyTableList = searchHistoryTableDao.queryBuilder().where(SearchHistoryTableDao.Properties.UserId.eq(-1), SearchHistoryTableDao.Properties.Type.eq("Search Image"), SearchHistoryTableDao.Properties.SearchKey.eq(searchKeyWord)).list();
        for (SearchHistoryTable searchHistoryTable : historyTableList) {
            searchHistoryTableDao.delete(searchHistoryTable);
        }
        SearchHistoryTable searchHistoryTable = new SearchHistoryTable();
        searchHistoryTable.setSearchKey(searchKeyWord);
        searchHistoryTable.setSearchTime(System.currentTimeMillis());
        searchHistoryTable.setType("Search Image");
        searchHistoryTable.setUserId(-1);
        App.getDaoSession().getSearchHistoryTableDao().save(searchHistoryTable);
    }


    /**
     * 清理当前用户search image类型历史数据库
     */
    private void deleteAllSearchDB() {
        SearchHistoryTableDao searchHistoryTableDao = App.getDaoSession().getSearchHistoryTableDao();
        List<SearchHistoryTable> historyTableList = searchHistoryTableDao.queryBuilder().where(SearchHistoryTableDao.Properties.UserId.eq(-1), SearchHistoryTableDao.Properties.Type.eq("Search Image")).list();
        for (SearchHistoryTable searchHistoryTable : historyTableList) {
            searchHistoryTableDao.delete(searchHistoryTable);
        }

    }
}
