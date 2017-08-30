package com.drcosu.ndileber.view.recycle;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by caizepeng on 16/5/18.
 */
public class DragItemCallback extends ItemTouchHelper.Callback {
    private DragItemData dragItemData;
    private RecyclerView.Adapter<RecyclerView.ViewHolder> mTargetAdapter;
    private int currPost;
    /**
     * 不可移动的pos
     */
    private List<Integer> invalidPositons = new ArrayList<Integer>();
    private DragStateCallback mDragStateCallback;

    public List<Integer> getInvalidPositons() {
        return invalidPositons;
    }

    public void setInvalidPositons(List<Integer> invalidPositons) {
        this.invalidPositons = invalidPositons;
    }

    public DragStateCallback getmDragStateCallback() {
        return mDragStateCallback;
    }

    public void setmDragStateCallback(DragStateCallback mDragStateCallback) {
        this.mDragStateCallback = mDragStateCallback;
    }

    public DragItemData getDragItemData() {
        return dragItemData;
    }

    public void setDragItemData(DragItemData dragItemData) {
        this.dragItemData = dragItemData;
    }

    public DragItemCallback(DragItemData dragItemData, RecyclerView.Adapter<RecyclerView.ViewHolder> targetAdapter) {
        this.dragItemData = dragItemData;
        this.mTargetAdapter = targetAdapter;

    }

    public void addinvalidPositons(Integer... positions) {
        List<Integer> temp = Arrays.asList(positions);
        invalidPositons.addAll(temp);
    }

    public void clearInvalidPositons() {
        invalidPositons.clear();
    }

    public void addinvalidPositons(List<Integer> integers) {
        invalidPositons.addAll(integers);
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        currPost = viewHolder.getLayoutPosition();
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            final int swipeFlags = 0;
            return makeMovementFlags(dragFlags, swipeFlags);
        } else {
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            final int swipeFlags = 0;
            return makeMovementFlags(dragFlags, swipeFlags);
        }
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        //得到拖动iten的position
        int fromPosition = viewHolder.getAdapterPosition();
        //得到目标item的position
        int toPosition = target.getAdapterPosition();
        //如果目标Position是不可
        if(!invalidPositons.contains(toPosition)){
            if (fromPosition < toPosition) {
                for (int i = fromPosition; i < toPosition; i++) {
                    Collections.swap(dragItemData.getDatas(), i, i + 1);
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--) {
                    Collections.swap(dragItemData.getDatas(), i, i - 1);
                }
            }
            mTargetAdapter.notifyItemMoved(fromPosition, toPosition);
        }

        return false;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return !invalidPositons.contains(currPost);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        dragItemData.getDatas().remove(position);
        dragItemData.notifyItemRemoved(position);

    }

    //拖拽开始
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (mDragStateCallback != null) {
            Log.e("DEMO", "select  == " + mDragStateCallback + " holder == " + viewHolder + " === ac" + actionState);
            mDragStateCallback.onSelectedChanged(viewHolder, actionState);
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    //拖拽完成
    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if (mDragStateCallback != null) {
            Log.e("DEMO", "clearView  == " + mDragStateCallback + " holder == " + viewHolder);
            mDragStateCallback.clearView(recyclerView, viewHolder);
        }
        super.clearView(recyclerView, viewHolder);
    }

    public interface DragStateCallback {
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState);

        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder);
    }
}
