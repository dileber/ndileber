package com.drcosu.ndileber.mvp.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
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

    private static final String START = "start";
    private static final String LAST = "last";

    private int mStart;
    private int mLast;

    private OnFragmentInteractionListener mListener;

    public EmojiFragment() {
    }

    public static EmojiFragment newInstance(int start, int last) {
        EmojiFragment fragment = new EmojiFragment();
        Bundle args = new Bundle();
        args.putInt(START, start);
        args.putInt(LAST, last);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mStart = getArguments().getInt(START);
            mLast = getArguments().getInt(LAST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_emoji, container, false);
        Context context = view.getContext();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.emoji_recycle);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 8));
        recyclerView.setAdapter(new EmojiAdaper(EmojiRepository.icon.subList(mStart,mLast),mListener));
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String icon);
    }
}
