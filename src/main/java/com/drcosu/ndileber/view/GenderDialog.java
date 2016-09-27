package com.drcosu.ndileber.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.drcosu.ndileber.R;

/**
 * Created by shidawei on 2016/9/25.
 */
public class GenderDialog extends Dialog{
    public GenderDialog(Context context) {
        super(context);
    }

    public GenderDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected GenderDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public static class Builder {
        private Context context;
        private DialogInterface.OnClickListener menButtonClickListener;
        private DialogInterface.OnClickListener womenButtonClickListener;

        public Builder(Context context) {
            this.context = context;
        }


        public Builder setWomenButton(DialogInterface.OnClickListener listener) {
            this.womenButtonClickListener = listener;
            return this;
        }

        public Builder setMenButton(DialogInterface.OnClickListener listener) {
            this.menButtonClickListener= listener;
            return this;
        }



        public GenderDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final GenderDialog dialog = new GenderDialog(context,R.style.gender_dialog);
            View layout = inflater.inflate(R.layout.gender_dialog, null);
            dialog.addContentView(layout, new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            TextView women = (TextView) layout.findViewById(R.id.gender_dialog_women);
            TextView men = (TextView) layout.findViewById(R.id.gender_dialog_men);
            women.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    womenButtonClickListener.onClick(dialog,
                            0);
                    dialog.dismiss();
                }
            });
            men.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    menButtonClickListener.onClick(dialog,1);
                    dialog.dismiss();
                }
            });
            return dialog;
        }
    }

}
