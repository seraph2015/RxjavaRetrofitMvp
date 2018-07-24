package org.seraph.mvprxjavaretrofit.ui.module.welcome;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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

    @Inject
    UltraPagerAdapter() {
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
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.welcome_activity_guide_pages_item, container, false);
        ImageView imageView = view.findViewById(R.id.iv_guide_page);
        imageView.setImageResource(listImage[position]);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pagerItemClickListener != null) {
                    pagerItemClickListener.onItemClick(position);
                }
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}
