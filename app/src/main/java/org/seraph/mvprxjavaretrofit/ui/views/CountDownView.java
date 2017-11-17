package org.seraph.mvprxjavaretrofit.ui.views;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.seraph.mvprxjavaretrofit.R;

/**
 * 倒计时
 * date：2016/3/22 17:16
 * author：xiongj
 * mail：417753393@qq.com
 *   ┏┓　　　┏┓
 * ┏┛┻━━━┛┻┓
 * ┃　　　　　　　┃
 * ┃　　　━　　　┃
 * ┃　┳┛　┗┳　┃
 * ┃　　　　　　　┃
 * ┃　　　┻　　　┃
 * ┃　　　　　　　┃
 * ┗━┓　　　┏━┛
 *     ┃　　　┃  神兽保佑
 *     ┃　　　┃  代码无BUG！
 *     ┃　　　┗━━━┓
 *     ┃　　　　　　　┣┓
 *     ┃　　　　　　　┏┛
 *     ┗┓┓┏━┳┓┏┛
 *       ┃┫┫　┃┫┫
 *       ┗┻┛　┗┻┛
 **/
public class CountDownView extends LinearLayout {

    private TextView hourView, minuteView, secondView;

    private MyCountDownTimer myCountDownTimer;

    public CountDownView(Context context) {
        this(context, null);
    }

    public CountDownView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CountDownView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = View.inflate(context, R.layout.common_view_count_down, null);
        hourView = (TextView) view.findViewById(R.id.tv_hour);
        minuteView = (TextView) view.findViewById(R.id.tv_minute);
        secondView = (TextView) view.findViewById(R.id.tv_second);
        this.addView(view);
    }


    /**
     * 设置倒计时
     *
     * @param millisInFuture    表示以毫秒为单位 倒计时的总数
     * @param countDownInterval 表示 间隔 多少微秒 调用一次 onTick 方法
     */
    public void setDownTimer(long millisInFuture, long countDownInterval) {
        if (myCountDownTimer == null) {
            myCountDownTimer = new MyCountDownTimer(millisInFuture, countDownInterval);
        }
        myCountDownTimer.start();
    }


    private class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            long hour = millisUntilFinished / 1000 / 60 / 60 % 24;
            long minute = millisUntilFinished / 1000 / 60 % 60;
            long second = millisUntilFinished / 1000 % 60;
            hourView.setText(getStrTime(hour));
            minuteView.setText(getStrTime(minute));
            secondView.setText(getStrTime(second));
        }


        String getStrTime(long time) {
            if (time < 10) {
                return "0" + time;
            }
            return String.valueOf(time);
        }

        @Override
        public void onFinish() {
            hourView.setText(getStrTime(0));
            minuteView.setText(getStrTime(0));
            secondView.setText(getStrTime(0));
        }
    }


    public void setCountDownViewBackground(int res) {
        hourView.setBackgroundResource(res);
        minuteView.setBackgroundResource(res);
        secondView.setBackgroundResource(res);
    }
}
