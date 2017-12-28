package org.seraph.mvprxjavaretrofit.ui.views;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.widget.ImageView;

import org.seraph.mvprxjavaretrofit.R;
import org.seraph.mvprxjavaretrofit.data.network.ImageLoad.glide.GlideApp;

import javax.inject.Inject;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * 透明的等待dialog
 */
public class CustomLoadingDialog2 extends Dialog {

    private ImageView imageView;

    @Inject
    public CustomLoadingDialog2(Context context) {
        super(context, R.style.progress_dialog);
        setContentView(R.layout.default_dialog_loading2);
        setCanceledOnTouchOutside(true);
        getWindow().getAttributes().gravity = Gravity.CENTER;
        setCancelable(false);
        imageView = findViewById(R.id.iv_loading);
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


    @Override
    public void show() {
        GlideApp.with(getContext())
                .asGif()
                .load(R.mipmap.common_gif_loading)
                .transform(new RoundedCornersTransformation(8, 5))
                .placeholder(new ColorDrawable(Color.TRANSPARENT)).into(imageView);
        super.show();

    }
}