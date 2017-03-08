package org.seraph.mvprxjavaretrofit.views;

import android.app.Dialog;
import android.content.Context;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.TextView;

import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.utlis.Tools;

/**
 * 透明的等待dialog
 */
public class CustomLoadingDialog extends Dialog {


    public CustomLoadingDialog(Context context) {
        super(context, R.style.CustomDialog);
        setContentView(R.layout.dialog_loading_view);
        setCanceledOnTouchOutside(true);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.alpha = 0.9f;
        getWindow().setAttributes(attributes);
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
            ((TextView) findViewById(R.id.tv_content)).setText(message);
        }
    }
}