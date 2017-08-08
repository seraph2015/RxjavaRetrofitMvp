package org.seraph.mvprxjavaretrofit.ui.module.welcome;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import org.seraph.mvprxjavaretrofit.R;

import javax.inject.Inject;

/**
 * 引导页切换
 * date：2017/5/3 10:53
 * author：xiongj
 * mail：417753393@qq.com
 **/
class UltraPagerAdapter extends PagerAdapter {

    public interface PagerItemClickListener {

        void onItemClick(int position);

    }

    private PagerItemClickListener pagerItemClickListener;


    private Integer[] listImage;

    private Context mContext;


    @Inject
    UltraPagerAdapter(Context context) {
        this.mContext = context;
    }

    public void setListImage(Integer[] listImage) {
        this.listImage = listImage;
    }

    public void setOnClickListener(PagerItemClickListener pagerItemClickListener) {
        this.pagerItemClickListener = pagerItemClickListener;
    }

    @Override
    public int getCount() {
        return listImage == null ? 0 : listImage.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        RelativeLayout linearLayout = (RelativeLayout) LayoutInflater.from(container.getContext()).inflate(R.layout.welcome_item_guide_pager_layout, null);
        ImageView imageView = (ImageView) linearLayout.findViewById(R.id.iv_guide_page);
        imageView.setImageResource(listImage[position]);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pagerItemClickListener != null) {
                    pagerItemClickListener.onItemClick(position);
                }
            }
        });
        container.addView(linearLayout);
//        linearLayout.getLayoutParams().width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 180, container.getContext().getResources().getDisplayMetrics());
//        linearLayout.getLayoutParams().height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 400, container.getContext().getResources().getDisplayMetrics());
        return linearLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        RelativeLayout view = (RelativeLayout) object;
        container.removeView(view);
    }
}
