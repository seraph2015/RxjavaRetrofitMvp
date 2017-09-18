package org.seraph.mvprxjavaretrofit.ui.module.test;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;

import com.blankj.utilcode.util.ToastUtils;
import com.jakewharton.rxbinding2.view.RxView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.di.component.DaggerTestsComponent;
import org.seraph.mvprxjavaretrofit.di.component.base.AppComponent;
import org.seraph.mvprxjavaretrofit.di.module.base.ActivityModule;
import org.seraph.mvprxjavaretrofit.ui.module.base.ABaseActivity;
import org.seraph.mvprxjavaretrofit.ui.module.base.IBaseContract;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * 测试video
 * date：2017/9/15 11:51
 * author：Seraph
 * mail：417753393@qq.com
 **/
public class SendVideoActivity extends ABaseActivity {

    @BindView(R.id.tv_toolbar_title)
    TextView mTitle;
    @BindView(R.id.btn_send_video)
    Button btnSendVideo;
    @BindView(R.id.video_view)
    VideoView mVideoView;

    @Override
    public int getContextView() {
        return R.layout.test_act_send_video;
    }

    @Override
    public void setupActivityComponent(AppComponent appComponent, ActivityModule activityModule) {
        DaggerTestsComponent.builder().appComponent(appComponent).activityModule(activityModule).build().inject(this);
    }

    @Override
    protected IBaseContract.IBaseActivityPresenter getMVPPresenter() {
        return null;
    }

    static final int REQUEST_VIDEO_CAPTURE = 1;

    @Inject
    RxPermissions mRxPermissions;

    @Override
    public void initCreate(@Nullable Bundle savedInstanceState) {
        mTitle.setText("测试录像");
        RxView.clicks(btnSendVideo)
                .compose(mRxPermissions.ensure(Manifest.permission.CAMERA))
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            dispatchTakeVideoIntent();
                        } else {
                            ToastUtils.showShortToast("获取权限失败");
                        }
                    }
                });
    }

    private void dispatchTakeVideoIntent() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            Uri videoUri = data.getData();
            mVideoView.setVideoURI(videoUri);
            play();
        }
    }

    //开始播放
    private void play() {
        if (!mVideoView.isPlaying()) {
            mVideoView.start();
        }
    }

    //暂停播放
    private void pause() {
        if (mVideoView.isPlaying()) {
            mVideoView.pause();
        }
    }

    //停止播放
    private void stop() {
        if (mVideoView.isPlaying()) {
            mVideoView.stopPlayback();
        }
    }

    //重新播放
    private void resume() {
        mVideoView.resume();
    }

    //释放资源
    private void suspend() {
        mVideoView.suspend();
    }

    @Override
    protected void onResume() {
        super.onResume();
        resume();
    }

    @Override
    protected void onPause() {
        pause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        suspend();
        super.onDestroy();
    }
}
