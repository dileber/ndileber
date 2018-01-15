package com.drcosu.ndileber.view.recycle;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.drcosu.ndileber.R;

/**
 * Created by shidawei on 2017/4/26.
 */

public class FooterHolder extends DileberHolder {

    public FooterState item;
    LinearLayout foot_layout;
    ProgressBar foot_progress;
    TextView foot_text;

    public FooterHolder(View itemView) {
        super(itemView);
        foot_layout = (LinearLayout)itemView.findViewById(R.id.foot_layout);
        foot_progress = (ProgressBar)itemView.findViewById(R.id.foot_progress);
        foot_text = (TextView)itemView.findViewById(R.id.foot_text);
    }

    @Override
    public void load(Context context) {
        if(item!=null){
            switch (item){
                case Normal:
                    foot_progress.setVisibility(View.GONE);
                    foot_text.setText(R.string.foot_normal);
                    break;
                case Loading:
                    foot_progress.setVisibility(View.VISIBLE);
                    foot_text.setText(R.string.foot_loading);
                    break;
                case End:
                    foot_progress.setVisibility(View.GONE);
                    foot_text.setText(R.string.foot_end);
                    break;
                case NetWorkError:
                    foot_progress.setVisibility(View.GONE);
                    foot_text.setText(R.string.foot_networkerror);
                    break;
                default:
            }
        }
    }
    public static int getLayout(){
        return R.layout.list_foot_loading;
    }

    public enum FooterState{
        Normal,Loading,End,NetWorkError
    }

}
