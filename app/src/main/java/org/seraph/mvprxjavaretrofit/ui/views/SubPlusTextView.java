package org.seraph.mvprxjavaretrofit.ui.views;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.seraph.mvprxjavaretrofit.R;

/**
 * 购物车商品数量加减控件
 *
 * @author xiongyf
 * @date 2014-11-23
 */
public class SubPlusTextView extends LinearLayout {

    private int goodCount = 1;
    /**
     * 商品数量
     */
    private TextView sp_count;
    private GoodsCountChangeListener goodsCountChangeListener;

    private int minCount = 1;
    private int maxCount = Integer.MAX_VALUE;

    /**
     * 设置商品最小数量
     *
     * @param count
     */
    public void setMinCount(int count) {
        this.minCount = count;
    }

    /**
     * 设置商品最大数量
     *
     * @param count
     */
    public void setMaxCount(int count) {
        this.maxCount = count;
    }

    /**
     * 获取数量
     *
     * @return
     */
    public int getGoodsCount() {
        return Integer.parseInt(sp_count.getText().toString());
    }

    /**
     * 设置数量
     *
     * @param count
     */
    public void setGoodsCount(int count) {
        goodCount = count;
        sp_count.setText(String.valueOf(count));
    }

    public interface GoodsCountChangeListener {
        /**
         * 商品数量改变监听
         *
         * @param count
         */
        void goodsChange(int count);
    }

    public void setGoodsCountChangeListener(
            GoodsCountChangeListener goodsCountChangeListener) {
        this.goodsCountChangeListener = goodsCountChangeListener;
    }

    public SubPlusTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    private void initViews() {
        View sub_plus = View.inflate(getContext(),
                R.layout.common_view_goods_sub_plus, null);

        LayoutParams params = new LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;

        LinearLayout goods_count_plus_ll = sub_plus
                .findViewById(R.id.ll_dialog_count_plus);
        LinearLayout goods_count_sub_ll = sub_plus
                .findViewById(R.id.ll_dialog_count_sub);
        sp_count = sub_plus.findViewById(R.id.tv_dialog_goods_count);
        sp_count.setText(goodCount + "");

        goods_count_plus_ll.setOnClickListener(v -> {
            if (goodCount >= maxCount) {
                return;
            }
            goodCount++;
            sp_count.setText(goodCount + "");
            if (goodsCountChangeListener != null) {
                goodsCountChangeListener.goodsChange(goodCount);
            }
        });

        goods_count_sub_ll.setOnClickListener(v -> {
            if (goodCount <= minCount) {
                return;
            }
            goodCount--;
            sp_count.setText(goodCount + "");
            if (goodsCountChangeListener != null) {
                goodsCountChangeListener.goodsChange(goodCount);
            }
        });

        this.addView(sub_plus, params);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

}
