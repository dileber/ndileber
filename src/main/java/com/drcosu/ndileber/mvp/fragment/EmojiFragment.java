package com.drcosu.ndileber.mvp.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.drcosu.ndileber.R;
import com.drcosu.ndileber.adapter.EmojiAdaper;
import com.drcosu.ndileber.repository.EmojiRepository;

public class EmojiFragment extends BaseFragment {

    private static final String PAGE = "page";
    private int mPage;

    private OnFragmentInteractionListener mListener;

    public EmojiFragment() {
    }

    public static EmojiFragment newInstance(int page) {
        EmojiFragment fragment = new EmojiFragment();
        Bundle args = new Bundle();
        args.putInt(PAGE, page);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPage = getArguments().getInt(PAGE);
        }
    }

    public int layoutViewId() {
        return R.layout.fragment_emoji;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        RecyclerView recyclerView = findView(R.id.emoji_recycle);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), EmojiRepository.lie));
        recyclerView.setAdapter(new EmojiAdaper(EmojiRepository.getIcon(mPage),mListener));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Fragment fragment = getParentFragment();
        if(fragment!=null){
            setInteraction(fragment);
        }else{
            setInteraction(context);
        }

    }

    private void setInteraction(Object o){
        if (o instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) o;
        } else {
            throw new RuntimeException(o.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String icon);
    }
}
