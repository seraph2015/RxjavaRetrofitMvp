package org.seraph.mvprxjavaretrofit.ui.module.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ToastUtils;
import com.yalantis.ucrop.UCrop;

import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.databinding.ActMainFrg1Binding;
import org.seraph.mvprxjavaretrofit.di.scope.ActivityScoped;
import org.seraph.mvprxjavaretrofit.ui.module.base.ABaseFragment;
import org.seraph.mvprxjavaretrofit.ui.module.main.contract.MainOneFragmentContract;
import org.seraph.mvprxjavaretrofit.ui.module.main.presenter.MainOneFragmentPresenter;
import org.seraph.mvprxjavaretrofit.utlis.TakePhoto;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import static android.app.Activity.RESULT_OK;


/**
 * 主界面
 * date：2017/2/20 16:38
 * author：xiongj
 * mail：417753393@qq.com
 **/
@ActivityScoped
public class MainOneFragment extends ABaseFragment<MainOneFragmentContract.Presenter> implements MainOneFragmentContract.View {


    private ActMainFrg1Binding binding;


    @Inject
    public MainOneFragment(){}

    @Override
    protected View initDataBinding(LayoutInflater inflater, ViewGroup container) {
        binding = DataBindingUtil.inflate(inflater, R.layout.act_main_frg1, container, false);
        return binding.getRoot();
    }

    @Inject
    MainOneFragmentPresenter presenter;

    @Override
    protected MainOneFragmentContract.Presenter getMVPPresenter() {
        return presenter;
    }

    @Override
    public void initCreate(@Nullable Bundle savedInstanceState) {
       binding.setPresenter(presenter);
       presenter.start();
    }

    public void setUserTextViewValue(CharSequence charSequence) {
        binding.tvDbUser.setText(charSequence);
    }

    //调用裁剪
    @Override
    public void onUCropImage(Uri sourceUri, Uri destinationUri) {
        UCrop.Options options = new UCrop.Options();
        //裁剪质量
        options.setCompressionQuality(90);
        options.setFreeStyleCropEnabled(true);
        //是否隐藏底部容器，默认显示
        options.setHideBottomControls(true);
        options.setActiveWidgetColor(getResources().getColor(R.color.colorPrimary));
        options.setToolbarColor(getResources().getColor(R.color.colorPrimary));
        options.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        UCrop.of(sourceUri, destinationUri)
                .withOptions(options)
              //  .withMaxResultSize(500, 300)
                .start(getActivity());
    }


    @Override
    public Fragment getFragment() {
        return this;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case TakePhoto.PHOTO_WITH_DATA://画廊
                    presenter.onPhotoComplete(data.getData());
                    break;
                case UCrop.REQUEST_CROP://剪切返回
                    presenter.onUCropResult(UCrop.getOutput(data));
                    break;
            }
        } else if (resultCode == UCrop.RESULT_ERROR) {
            ToastUtils.showShort("图片剪切失败");
        }
    }

}
