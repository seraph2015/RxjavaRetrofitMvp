package org.seraph.mvprxjavaretrofit.ui.module.main.presenter;

import android.app.AlertDialog;
import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import org.reactivestreams.Subscription;
import org.seraph.mvprxjavaretrofit.data.db.help.SearchHistoryHelp;
import org.seraph.mvprxjavaretrofit.data.db.help.UserBeanHelp;
import org.seraph.mvprxjavaretrofit.data.db.table.SearchHistoryTable;
import org.seraph.mvprxjavaretrofit.data.network.rx.RxSchedulers;
import org.seraph.mvprxjavaretrofit.data.network.service.ApiBaiduService;
import org.seraph.mvprxjavaretrofit.ui.module.base.ABaseSubscriber;
import org.seraph.mvprxjavaretrofit.ui.module.common.photopreview.PhotoPreviewBean;
import org.seraph.mvprxjavaretrofit.ui.module.main.contract.MainTwoFragmentContract;
import org.seraph.mvprxjavaretrofit.ui.module.main.model.ImageBaiduBean;
import org.seraph.mvprxjavaretrofit.utlis.FileUtils;
import org.seraph.mvprxjavaretrofit.utlis.Tools;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * 第二页P
 * date：2017/2/21 17:10
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class MainTwoFragmentPresenter extends MainTwoFragmentContract.Presenter {

    private RecyclerView mRecyclerView;

    private Subscription mSubscription;

    private ApiBaiduService mApiBaiduService;

    private SearchHistoryHelp mSearchHistoryHelp;

    private UserBeanHelp mUserHelp;

    private Context context;

    @Inject
    MainTwoFragmentPresenter(Context context, ApiBaiduService apiBaiduService, SearchHistoryHelp searchHistoryHelp, UserBeanHelp userHelp) {
        this.mApiBaiduService = apiBaiduService;
        this.mSearchHistoryHelp = searchHistoryHelp;
        this.mUserHelp = userHelp;
        this.context = context;
    }

    /**
     * 搜索历史
     */
    private List<SearchHistoryTable> listSearch;

    private int pageNo = 0;

    private String searchKeyWord;
    //默认用户id
    private int tempId = -1;
    //历史记录type
    private String type = "Search Image";


    private List<ImageBaiduBean.BaiduImage> list = new ArrayList<>();

    @Override
    public void start() {

    }


    public void showCacheFilePath() {
        mView.setTextView(FileUtils.getCacheDirectory(context, null).getPath());
    }

    public void searchHistory() {
        if (mUserHelp.getUserToken() == null) {
            tempId = -1;
        } else {
            tempId = mUserHelp.getUserBean().getId();
        }
        //查询本地数据搜索历史（时间倒叙）
        listSearch = mSearchHistoryHelp.querySearchDB(tempId, type);
        if (listSearch.size() == 0) {
            ToastUtils.showShort("暂无搜索历史");
            return;
        }
        showSearchHistory();
    }


    /**
     * 显示选择框
     */
    private void showSearchHistory() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final String[] items = new String[listSearch.size() + 1];
        for (int i = 0; i < listSearch.size(); i++) {
            items[i] = listSearch.get(i).getSearchKey();
        }
        items[listSearch.size()] = "清除历史记录";
        builder.setItems(items, (dialog, which) -> {
            if (which == listSearch.size()) {
                mSearchHistoryHelp.deleteAllSearchDB(tempId, type);
            } else {
                mView.setSearchInput(items[which]);
            }
        }).show();
    }

    public void startPicassoToImage() {
        searchKeyWord = mView.getSearchKeyWord();
        if (Tools.isNull(searchKeyWord)) {
            ToastUtils.showShort("search is null!");
            return;
        }
        //保存搜索到本地数据库
        mSearchHistoryHelp.saveSearchToDB(tempId, type, searchKeyWord);

        mView.showLoading("正在搜索").setOnDismissListener(dialog -> {
            if (mSubscription != null) {
                mSubscription.cancel();
            }
        });
        getBaiduImageList(searchKeyWord, 1);
    }


    public void doLoadMore() {
        getBaiduImageList(searchKeyWord, pageNo + 1);
    }


    public void getBaiduImageList(String keyWord, final int requestPageNo) {
        //获取图片地址 百度图片 标签objURL
        mApiBaiduService.doBaiduImageUrl(Tools.getBaiduImagesUrl(keyWord, requestPageNo))
                .compose(RxSchedulers.io_main())
                .map(imageBaiduBean -> imageBaiduBean.imgs)
                .doOnSubscribe(subscription -> mSubscription = subscription)
                .as(mView.bindLifecycle())
                .subscribe(new ABaseSubscriber<List<ImageBaiduBean.BaiduImage>>(mView) {
                    @Override
                    public void onSuccess(List<ImageBaiduBean.BaiduImage> baiduImages) {
                        if (requestPageNo == 1) {
                            list.clear();
                        }
                        list.addAll(baiduImages);
                        //如果请求回来的数据是等于请求的分页数据，则显示加载更多按钮，反正显示没有更多数据
                        mView.setListDate(list);
                        pageNo = requestPageNo;
                    }

                    @Override
                    public void onError(String errStr) {
                        ToastUtils.showShort(errStr);
                        //数据失败
                        mView.setListDate(null);
                    }

                });
    }

    public void onPhotoPreview(int position) {
        ArrayList<PhotoPreviewBean> photoList = new ArrayList<>();
        for (ImageBaiduBean.BaiduImage baiduImage : list) {
            PhotoPreviewBean photoPreviewBean = new PhotoPreviewBean();
            photoPreviewBean.objURL = baiduImage.objURL;
            photoPreviewBean.type = baiduImage.type;
            photoPreviewBean.width = baiduImage.width;
            photoPreviewBean.height = baiduImage.height;
            photoList.add(photoPreviewBean);
        }
        mView.startPhotoPreview(photoList, position);
    }


}
