package org.seraph.mvprxjavaretrofit.ui.views;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.KeyEvent;
import android.widget.TextView;

import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.utli.Tools;

/**
 * 透明的等待dialog
 */
public class CustomLoadingDialog extends Dialog {


    public CustomLoadingDialog(Context context) {
        super(context, R.style.progress_dialog);
        setContentView(R.layout.dialog_loading);
        setCanceledOnTouchOutside(true);
        getWindow().getAttributes().gravity = Gravity.CENTER;
        setCancelable(false);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                dismiss();
                break;
        }
        return true;
    }


    public void setDialogMessage(String message) {
        show();
        if (!Tools.isNull(message)) {
            ((TextView) findViewById(R.id.tv_loading_msg)).setText(message);
        }
    }
}