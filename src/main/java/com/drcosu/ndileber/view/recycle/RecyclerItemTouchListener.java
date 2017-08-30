package com.drcosu.ndileber.view.recycle;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * 触摸监视器
 */
public abstract class RecyclerItemTouchListener implements RecyclerView.OnItemTouchListener{
    private RecyclerView recyclerView;
    private GestureDetectorCompat mGestureDetectorCompat;

    public RecyclerItemTouchListener(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        mGestureDetectorCompat = new GestureDetectorCompat(
                recyclerView.getContext(),new ItemTouchHelperGestureListener()
        );
    }

    /**
     * onInterceptTouchEvent是在ViewGroup里面定义的。Android中的layout布局类一般都是继承此类的。
     * onInterceptTouchEvent是用于拦截手势事件的，每个手势事件都会先调用onInterceptTouchEvent。
     * @param rv
     * @param e
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetectorCompat.onTouchEvent(e);
        return false;
    }

    /**
     * onTouchEvent同样也是在view中定义的一个方法。处理传递到view 的手势事件。
     * 手势事件类型包括ACTION_DOWN,ACTION_MOVE,ACTION_UP,ACTION_CANCEL四种事件。
     * @param rv
     * @param e
     */
    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetectorCompat.onTouchEvent(e);
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
    //手势识别类
    class ItemTouchHelperGestureListener extends GestureDetector.SimpleOnGestureListener{
        public ItemTouchHelperGestureListener() {
        }

        //普通的单击事件
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            View child = recyclerView.findChildViewUnder(e.getX(),e.getY());
            if(child!=null){
                RecyclerView.ViewHolder viewHolder = recyclerView.getChildViewHolder(child);
                onItemClick(viewHolder,e,viewHolder.getLayoutPosition());
            }
            return true;
        }
        //长按屏幕,超过一定时长就会触发
        @Override
        public void onLongPress(MotionEvent e) {
            super.onLongPress(e);
            View child = recyclerView.findChildViewUnder(e.getX(),e.getY());
            if(child!=null){
                RecyclerView.ViewHolder viewHolder =
                        recyclerView.findContainingViewHolder(child);
                onLongClick(viewHolder,e,viewHolder.getLayoutPosition());
            }
        }
    }

    public abstract  void  onItemClick(RecyclerView.ViewHolder viewHolder,MotionEvent e,int position);
    public abstract  void  onLongClick(RecyclerView.ViewHolder viewHolder,MotionEvent e,int position);
}
