package org.seraph.mvprxjavaretrofit.mvp.presenter;

import android.app.AlertDialog;
import android.content.DialogInterface;

import org.reactivestreams.Subscription;
import org.seraph.mvprxjavaretrofit.AppApplication;
import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.ui.adapter.ImageListAdapter;
import org.seraph.mvprxjavaretrofit.db.gen.SearchHistoryTableDao;
import org.seraph.mvprxjavaretrofit.db.table.SearchHistoryTable;
import org.seraph.mvprxjavaretrofit.mvp.model.BaiduImageBean;
import org.seraph.mvprxjavaretrofit.mvp.model.PhotoPreviewBean;
import org.seraph.mvprxjavaretrofit.mvp.view.BaseView;
import org.seraph.mvprxjavaretrofit.mvp.view.MainTwoFragmentView;
import org.seraph.mvprxjavaretrofit.io.ApiService;
import org.seraph.mvprxjavaretrofit.io.BaseNetWorkSubscriber;
import org.seraph.mvprxjavaretrofit.utli.FileTools;
import org.seraph.mvprxjavaretrofit.utli.Tools;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

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

    private String title;
    private int logoIcon;

    private Subscription mSubscription;

    private ImageListAdapter imageListAdapter;

    private List<BaiduImageBean.BaiduImage> listImage = new ArrayList<>();

    private int pageNo = 0;

    private String searchKeyWord;


    /**
     * 搜索历史
     */
    private List<SearchHistoryTable> listSearch;


    public void initData() {
        title = " 图片搜索";
        logoIcon = R.drawable.ic_search_black_24dp;
        mView.setTitleAndLogo(title,logoIcon);

        imageListAdapter = new ImageListAdapter(mView.getContext(), listImage);
        mView.setImageAdapter(imageListAdapter);
    }


    @Override
    public void restoreData() {
        super.restoreData();
        mView.setTitleAndLogo(title,logoIcon);
        mView.upDataToolbarAlpha(0);
    }


    public void showCacheFilePath() {
        mView.setTextView(FileTools.getCacheDirectory(mView.getContext(), null).getPath());
    }

    public void searchHistory() {
        //查询本地数据搜索历史（时间倒叙）
        listSearch = AppApplication.getDaoSession().getSearchHistoryTableDao().queryBuilder().where(SearchHistoryTableDao.Properties.UserId.eq(-1)).orderDesc(SearchHistoryTableDao.Properties.SearchTime).list();
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

    private void getBaiduImageList(String keyWord, final int requestPageNo) {
        //获取图片地址 百度图片 标签objURL
        ApiService.doBaiduImage(Tools.getBaiduImagesUrl(keyWord, requestPageNo))
                .doOnSubscribe(new Consumer<Subscription>() {
                    @Override
                    public void accept(Subscription subscription) throws Exception {
                        mSubscription = subscription;
                        mView.showLoading();
                    }
                })
                .map(new Function<BaiduImageBean, List<BaiduImageBean.BaiduImage>>() {
                    @Override
                    public List<BaiduImageBean.BaiduImage> apply(BaiduImageBean baiduImageBean) throws Exception {
                        return baiduImageBean.imgs;
                    }
                })
                .subscribe(new BaseNetWorkSubscriber<List<BaiduImageBean.BaiduImage>>(mView) {
                    @Override
                    public void onSuccess(List<BaiduImageBean.BaiduImage> baiduImages) {
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
                        imageListAdapter.notifyDataSetChanged();
                        pageNo = requestPageNo;
                    }

                    @Override
                    public void onError(String errStr) {
                        mView.showToast(errStr);
                    }

                });
    }

    @Override
    public void unSubscribe() {
        super.unSubscribe();
        if (mSubscription != null) {
            mSubscription.cancel();
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
        SearchHistoryTableDao searchHistoryTableDao = AppApplication.getDaoSession().getSearchHistoryTableDao();
        List<SearchHistoryTable> historyTableList = searchHistoryTableDao.queryBuilder().where(SearchHistoryTableDao.Properties.UserId.eq(-1), SearchHistoryTableDao.Properties.Type.eq("Search Image"), SearchHistoryTableDao.Properties.SearchKey.eq(searchKeyWord)).list();
        for (SearchHistoryTable searchHistoryTable : historyTableList) {
            searchHistoryTableDao.delete(searchHistoryTable);
        }
        SearchHistoryTable searchHistoryTable = new SearchHistoryTable();
        searchHistoryTable.setSearchKey(searchKeyWord);
        searchHistoryTable.setSearchTime(System.currentTimeMillis());
        searchHistoryTable.setType("Search Image");
        searchHistoryTable.setUserId(-1);
        AppApplication.getDaoSession().getSearchHistoryTableDao().save(searchHistoryTable);
    }


    /**
     * 清理当前用户search image类型历史数据库
     */
    private void deleteAllSearchDB() {
        SearchHistoryTableDao searchHistoryTableDao = AppApplication.getDaoSession().getSearchHistoryTableDao();
        List<SearchHistoryTable> historyTableList = searchHistoryTableDao.queryBuilder().where(SearchHistoryTableDao.Properties.UserId.eq(-1), SearchHistoryTableDao.Properties.Type.eq("Search Image")).list();
        for (SearchHistoryTable searchHistoryTable : historyTableList) {
            searchHistoryTableDao.delete(searchHistoryTable);
        }

    }
}
