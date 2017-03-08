package org.seraph.mvprxjavaretrofit.mvp.presenter;

import org.reactivestreams.Subscription;
import org.seraph.mvprxjavaretrofit.mvp.view.BaseView;
import org.seraph.mvprxjavaretrofit.mvp.view.MainThreeFragmentView;
import org.seraph.mvprxjavaretrofit.request.ApiService;

/**
 * FragmentThee逻辑处理层
 * date：2017/2/15 11:27
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class MainThreeFragmentPresenter extends BasePresenter {


    private MainThreeFragmentView mView;

    private float percentScroll = 0;

    @Override
    public void attachView(BaseView view) {
        super.attachView(view);
        this.mView = (MainThreeFragmentView) view;
    }


    private Subscription subscription;

    private String title;

    public void initData() {
        title = " Three";
        mView.setTitle(title);
    }


    @Override
    public void restoreData() {
        super.restoreData();
        mView.setTitle(title);
        mView.upDataToolbarAlpha(0);
    }

    public void post12306Https() {
        ApiService.do12306().doOnSubscribe(subscription -> mView.showLoading()).subscribe(s -> {
        }, e -> {
            mView.hideLoading();
            //访问的为12306网站测试证书，所以无法用gson解析
            if (e instanceof javax.net.ssl.SSLHandshakeException) {
                mView.setTextView("缺少https证书");
            } else if (e instanceof com.google.gson.stream.MalformedJsonException) {
                mView.setTextView("访问成功");
            }
        });

    }

    public void showEmoji() {
        String tempInput = mView.getInputValue();
        //把输入的表情进行转义成16进制
        mView.showEmojiValue(tempInput + "------> " + tempInput.codePointCount(0, tempInput.length()) + "===" + new String(Character.toChars(0x1f602)));
        mView.getContext();
    }
}
