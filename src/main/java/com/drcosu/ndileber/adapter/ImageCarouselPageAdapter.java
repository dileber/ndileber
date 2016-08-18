package com.drcosu.ndileber.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * Created by shidawei on 16/8/14.
 */
public class ImageCarouselPageAdapter extends PagerAdapter{

    private List<View> viewlist;

    public ImageCarouselPageAdapter(List<View> viewlist) {
        this.viewlist = viewlist;
    }

    @Override
    public int getCount() {
        //设置成最大，使用户看不到边界
        return Integer.MAX_VALUE;
    }

    /**
     * 判断出去的view是否等于进来的view 如果为true直接复用
     */
    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    /**
     * 销毁预加载以外的view对象, 会把需要销毁的对象的索引位置传进来，就是position，
     * 因为mImageViewList只有五条数据，而position将会取到很大的值， 所以使用取余数的方法来获取每一条数据项。
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Logger.w("移除了那里viewpager的显示页面"+position);
        // container.removeView(mImageViewList.get(position%mImageViewList.size()));
    }

    /**
     * 创建一个view，
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        try {
            container.addView(viewlist.get(position
                    % viewlist.size()));
        } catch (Exception e) {

        }

        return viewlist.get(position % viewlist.size());

    }

}
