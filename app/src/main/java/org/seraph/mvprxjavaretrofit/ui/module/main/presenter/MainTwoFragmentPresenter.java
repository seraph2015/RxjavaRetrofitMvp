package org.seraph.mvprxjavaretrofit.ui.module.main.presenter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;

import org.reactivestreams.Subscription;
import org.seraph.mvprxjavaretrofit.data.local.db.help.SearchHistoryHelp;
import org.seraph.mvprxjavaretrofit.data.local.db.help.UserBeanHelp;
import org.seraph.mvprxjavaretrofit.data.local.db.table.SearchHistoryTable;
import org.seraph.mvprxjavaretrofit.data.network.rx.RxSchedulers;
import org.seraph.mvprxjavaretrofit.data.network.service.ApiBaiduService;
import org.seraph.mvprxjavaretrofit.ui.module.base.ABaseNetWorkSubscriber;
import org.seraph.mvprxjavaretrofit.ui.module.common.photopreview.PhotoPreviewBean;
import org.seraph.mvprxjavaretrofit.ui.module.main.adapter.ImageListBaiduAdapter;
import org.seraph.mvprxjavaretrofit.ui.module.main.contract.MainTwoFragmentContract;
import org.seraph.mvprxjavaretrofit.ui.module.main.model.ImageBaiduBean;
import org.seraph.mvprxjavaretrofit.utlis.FileUtils;
import org.seraph.mvprxjavaretrofit.utlis.Tools;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * 第二页P
 * date：2017/2/21 17:10
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class MainTwoFragmentPresenter implements MainTwoFragmentContract.Presenter {

    private MainTwoFragmentContract.View mView;

    private RecyclerView mRecyclerView;

    private Subscription mSubscription;

    @Override
    public void setView(MainTwoFragmentContract.View view) {
        this.mView = view;
        mRecyclerView = mView.getRecyclerView();
    }

    private Context mContext;

    private ApiBaiduService mApiBaiduService;

    private SearchHistoryHelp mSearchHistoryHelp;

    private UserBeanHelp mUserHelp;

    private ImageListBaiduAdapter mAdapter;

    @Inject
    MainTwoFragmentPresenter(Context context, ApiBaiduService apiBaiduService, SearchHistoryHelp searchHistoryHelp, UserBeanHelp userHelp, ImageListBaiduAdapter adapter) {
        this.mContext = context;
        this.mApiBaiduService = apiBaiduService;
        this.mSearchHistoryHelp = searchHistoryHelp;
        this.mUserHelp = userHelp;
        this.mAdapter = adapter;
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

    @Override
    public void start() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter.bindToRecyclerView(mRecyclerView);
        mAdapter.addHeaderView(mView.getHeadView());
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                onPhotoPreview(position);
            }
        });
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getBaiduImageList(searchKeyWord, pageNo + 1);
            }
        }, mRecyclerView);
    }


    @Override
    public void showCacheFilePath() {
        mView.setTextView(FileUtils.getCacheDirectory(mContext, null).getPath());
    }

    @Override
    public void searchHistory() {
        if (mUserHelp.getUserToken() == null) {
            tempId = -1;
        } else {
            tempId = mUserHelp.getUserBean().getId();
        }
        //查询本地数据搜索历史（时间倒叙）
        listSearch = mSearchHistoryHelp.querySearchDB(tempId, type);
        if (listSearch.size() == 0) {
            ToastUtils.showShortToast("暂无搜索历史");
            return;
        }
        showSearchHistory();
    }


    /**
     * 显示选择框
     */
    private void showSearchHistory() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        final String[] items = new String[listSearch.size() + 1];
        for (int i = 0; i < listSearch.size(); i++) {
            items[i] = listSearch.get(i).getSearchKey();
        }
        items[listSearch.size()] = "清除历史记录";
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == listSearch.size()) {
                    mSearchHistoryHelp.deleteAllSearchDB(tempId, type);
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
            ToastUtils.showShortToast("search is null!");
            return;
        }
        //保存搜索到本地数据库
        mSearchHistoryHelp.saveSearchToDB(tempId, type, searchKeyWord);

        mView.showLoading("正在搜索").setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (mSubscription != null) {
                    mSubscription.cancel();
                }
            }
        });
        getBaiduImageList(searchKeyWord, 1);
    }


    private void getBaiduImageList(String keyWord, final int requestPageNo) {
        //获取图片地址 百度图片 标签objURL

        mApiBaiduService.doBaiduImageUrl(Tools.getBaiduImagesUrl(keyWord, requestPageNo))
                .compose(RxSchedulers.<ImageBaiduBean>io_main(mView))
                .map(new Function<ImageBaiduBean, List<ImageBaiduBean.BaiduImage>>() {
                    @Override
                    public List<ImageBaiduBean.BaiduImage> apply(ImageBaiduBean imageBaiduBean) throws Exception {
                        return imageBaiduBean.imgs;
                    }
                })
                .doOnSubscribe(new Consumer<Subscription>() {
                    @Override
                    public void accept(@NonNull Subscription subscription) throws Exception {
                        mSubscription = subscription;
                    }
                })
                .subscribe(new ABaseNetWorkSubscriber<List<ImageBaiduBean.BaiduImage>>(mView) {
                    @Override
                    public void onSuccess(List<ImageBaiduBean.BaiduImage> baiduImages) {
                        if (requestPageNo == 1) {
                            mAdapter.replaceData(baiduImages);
                        } else {
                            mAdapter.addData(baiduImages);
                        }
                        //如果请求回来的数据是等于请求的分页数据，则显示加载更多按钮，反正显示没有更多数据
                        if (baiduImages.size() >= 48) {
                            mAdapter.loadMoreComplete();
                        } else {
                            mAdapter.loadMoreEnd();
                        }
                        pageNo = requestPageNo;
                    }

                    @Override
                    public void onError(String errStr) {
                        ToastUtils.showShortToast(errStr);
                        //数据失败
                        mAdapter.loadMoreFail();
                    }

                });
    }

    public void onPhotoPreview(int position) {
        ArrayList<PhotoPreviewBean> photoList = new ArrayList<>();
        for (ImageBaiduBean.BaiduImage baiduImage : mAdapter.getData()) {
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
