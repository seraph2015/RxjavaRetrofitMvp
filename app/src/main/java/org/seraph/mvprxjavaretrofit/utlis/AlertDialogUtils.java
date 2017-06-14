package org.seraph.mvprxjavaretrofit.utlis;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;

import org.seraph.mvprxjavaretrofit.R;

import javax.inject.Inject;

/**
 * 常用的一些弹出框
 * date：2017/5/11 11:35
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class AlertDialogUtils {


    public interface SelectedTextListener {
        void onSelectedText(String str);
    }

    public interface SelectedItemListener {
        void onSelectedItem(int position);
    }

    private Activity mContext;

    @Inject
    public AlertDialogUtils(Activity mContext) {
        this.mContext = mContext;
    }

    /**
     * 选择性别
     */
    public void createGenderSelectedDialog(final SelectedTextListener selectedTextListener) {
        View view = View.inflate(mContext, R.layout.common_dialog_gender_selected, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(mContext).setView(view).show();

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedTextListener != null) {
                    switch (v.getId()) {
                        case R.id.ll_gender_female:
                            selectedTextListener.onSelectedText("女");
                            break;
                        case R.id.ll_gender_male:
                            selectedTextListener.onSelectedText("男");
                            break;
                    }
                }
                alertDialog.dismiss();
            }
        };
        view.findViewById(R.id.ll_gender_female).setOnClickListener(onClickListener);
        view.findViewById(R.id.ll_gender_male).setOnClickListener(onClickListener);

    }


    /**
     * 头像选择
     */
    public void createHeadSelectedDialog(View parent, final SelectedItemListener selectedItemListener) {
        createButtonSelectedDialog(parent, selectedItemListener, "拍照", "从手机相册选择");
    }

    /**
     * 选择文件（照片），小视频（视频或者照片）
     */
    public void createFileSelectedDialog(View parent, final SelectedItemListener selectedItemListener) {
        View view = View.inflate(mContext, R.layout.common_dialog_file_selected, null);
        final PopupWindow pop = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setAnimationStyle(R.style.common_pop_head_selected);
        pop.setFocusable(true);
        pop.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pop.setOutsideTouchable(true);
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                // 关闭蒙层效果
                Tools.setWindowAlpha(mContext, 1f);
            }
        });
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.dismiss();
                if (selectedItemListener != null) {
                    switch (v.getId()) {
                        case R.id.tv_option0:
                            selectedItemListener.onSelectedItem(0);
                            break;
                        case R.id.tv_option1:
                            selectedItemListener.onSelectedItem(1);
                            break;
                        case R.id.tv_option2:
                            selectedItemListener.onSelectedItem(2);
                            break;
                    }
                }
            }
        };
        view.findViewById(R.id.tv_option0).setOnClickListener(onClickListener);
        view.findViewById(R.id.tv_option1).setOnClickListener(onClickListener);
        view.findViewById(R.id.tv_option2).setOnClickListener(onClickListener);
        view.findViewById(R.id.tv_cancel).setOnClickListener(onClickListener);
        if (!pop.isShowing()) {
            pop.showAtLocation(parent, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            Tools.setWindowAlpha(mContext, 0.5f);
        }
    }


    private void createButtonSelectedDialog(View parent, final SelectedItemListener selectedItemListener, String btn1Text, String btn2Text) {
        View view = View.inflate(mContext, R.layout.common_dialog_head_selected, null);
        final PopupWindow pop = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setAnimationStyle(R.style.common_pop_head_selected);
        pop.setFocusable(true);
        pop.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pop.setOutsideTouchable(true);
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                // 关闭蒙层效果
                Tools.setWindowAlpha(mContext, 1f);
            }
        });
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.dismiss();
                if (selectedItemListener != null) {
                    switch (v.getId()) {
                        case R.id.tv_option1:
                            selectedItemListener.onSelectedItem(1);
                            break;
                        case R.id.tv_option2:
                            selectedItemListener.onSelectedItem(2);
                            break;
                    }
                }
            }
        };
        TextView option1 = (TextView) view.findViewById(R.id.tv_option1);
        if (!StringUtils.isEmpty(btn1Text)) {
            option1.setText(btn1Text);
        }
        option1.setOnClickListener(onClickListener);
        TextView option2 = (TextView) view.findViewById(R.id.tv_option2);
        if (!StringUtils.isEmpty(btn2Text)) {
            option2.setText(btn2Text);
        }
        option2.setOnClickListener(onClickListener);
        view.findViewById(R.id.tv_cancel).setOnClickListener(onClickListener);
        if (!pop.isShowing()) {
            pop.showAtLocation(parent, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            Tools.setWindowAlpha(mContext, 0.5f);
        }
    }

}
