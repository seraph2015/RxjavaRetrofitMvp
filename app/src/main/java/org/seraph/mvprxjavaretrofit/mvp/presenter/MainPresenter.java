package org.seraph.mvprxjavaretrofit.mvp.presenter;

import android.graphics.Color;
import android.support.v4.app.Fragment;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.fragment.BaseFragment;
import org.seraph.mvprxjavaretrofit.fragment.MainOneFragment;
import org.seraph.mvprxjavaretrofit.fragment.MainTwoFragment;
import org.seraph.mvprxjavaretrofit.mvp.view.BaseView;
import org.seraph.mvprxjavaretrofit.mvp.view.MainActivityView;
import org.seraph.mvprxjavaretrofit.utlis.FragmentController;

import io.reactivex.Flowable;

/**
 * mian逻辑处理层
 * date：2017/2/15 11:27
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class MainPresenter extends BaseActivityPresenter {

    private MainActivityView mainView;

    @Override
    public void attachView(BaseView mView) {
        super.attachView(mView);
        this.mainView = (MainActivityView) mView;
    }


    private FragmentController mFragmentController;

    private String[] tags = new String[]{"one", "two", "three", "four"};
    //icon
    private int[] unSelectIconImage = new int[]{R.mipmap.icon_main_logo2, R.mipmap.icon_main_logo2, R.mipmap.icon_main_logo2, R.mipmap.icon_main_logo2};
    private int[] selectIconImage = new int[]{R.mipmap.icon_main_logo, R.mipmap.icon_main_logo, R.mipmap.icon_main_logo, R.mipmap.icon_main_logo};
    //bgColor,textColor
    private int selectedBgColor = Color.parseColor("#3b77db");
    private int selectedTextColor = Color.parseColor("#ffffff");
    private int unSelectedBgColor = Color.parseColor("#f8f8f8");
    private int unSelectedTextColor = Color.parseColor("#666666");

    private int childCount = 0;

    public void initData() {
        mFragmentController = new FragmentController(mainView.getMainActivity(), R.id.fl_home);
        mFragmentController.setFragmentTags(tags);
        childCount = mainView.getMenuChildCount();
    }




    /**
     * 设置选中
     */
    private void setSelectedMenu(int position) {
        mainView.setMenuItem(position, selectedBgColor, selectIconImage[position], selectedTextColor);
    }

    /**
     * 设置未选中
     */
    private void setUnSelectedMenu(int position) {
        mainView.setMenuItem(position, unSelectedBgColor, unSelectIconImage[position], unSelectedTextColor);
    }


    /**
     * 选中某项，改变状态
     */
    public void changeCurrentClickState(int positionIndex) {
        Flowable.range(0, childCount).subscribe(new Subscriber<Integer>() {
            Subscription subscription;

            @Override
            public void onSubscribe(Subscription s) {
                subscription = s;
                subscription.request(1);
            }

            @Override
            public void onNext(Integer integer) {
                setUnSelectedMenu(integer);
                subscription.request(1);
            }

            @Override
            public void onError(Throwable t) {
            }

            @Override
            public void onComplete() {
                setSelectedMenu(positionIndex);
                setSelectedFragment(positionIndex);
            }
        });

    }

    /**
     * 设置选中的碎片,切换主题
     */
    private void setSelectedFragment(int positionIndex) {
        Class<? extends Fragment> clazz;
        switch (positionIndex) {
            case 0:
                clazz = MainOneFragment.class;
                break;
            case 1:
                clazz = MainTwoFragment.class;
                break;
            case 2:
                clazz = MainOneFragment.class;
                break;
            case 3:
                clazz = MainOneFragment.class;
                break;
            default:
                clazz = MainOneFragment.class;
                break;
        }
        BaseFragment fragment = (BaseFragment) mFragmentController.add(clazz, tags[positionIndex], null);
        fragment.restoreData();
    }


}
