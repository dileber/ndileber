package com.drcosu.ndileber.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.drcosu.ndileber.R;
import com.drcosu.ndileber.mvp.fragment.EmojiFragment;

import java.util.List;

/**
 * 表情adapter
 * Created by jing on 2016/8/19.
 */
public class EmojiAdaper extends RecyclerView.Adapter<EmojiAdaper.EmojiHodler> {

    private final List<String> mValues;

    private final EmojiFragment.OnFragmentInteractionListener mListener;


    public EmojiAdaper(List<String> items,EmojiFragment.OnFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public EmojiHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_emoji, parent, false);
        return new EmojiHodler(view);
    }

    @Override
    public void onBindViewHolder(EmojiHodler holder, int position) {
        holder.item = mValues.get(position);
        holder.icon.setText( mValues.get(position));
        if(mListener!=null){
            final int p = position;
            holder.icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onFragmentInteraction(mValues.get(p));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    class EmojiHodler extends RecyclerView.ViewHolder{
        public final View mView;

        private String item;
        private final TextView icon;

        public EmojiHodler(View itemView) {
            super(itemView);
            mView = itemView;
            icon = (TextView) itemView.findViewById(R.id.recycler_emoji_icon);
        }
    }


}
