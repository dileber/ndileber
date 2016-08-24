package com.drcosu.ndileber.tools;

import android.app.Activity;
import android.app.Dialog;

import com.drcosu.ndileber.R;
import com.drcosu.ndileber.app.ActivityManager;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by shidawei on 16/8/4.
 */
public class UDialog {


    public static final int DIALOG_ERROR = 1;
    public static final int DIALOG_SUCCESS = 2;
    public static final int DIALOG_NORMAL = 3;
    public static final int DIALOG_WARNING = 4;

    public static Dialog alert(Integer type,String message){
        Activity activity = ActivityManager.peekTopActivity();
        SweetAlertDialog alertDialog = new SweetAlertDialog(activity);
        if(type==null){
            type = DIALOG_NORMAL;
        }
        switch (type){
            case DIALOG_ERROR:
                alertDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                alertDialog.setTitleText(activity.getResources().getString(R.string.dialog_error));
                break;
            case DIALOG_SUCCESS:
                alertDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                alertDialog.setTitleText(activity.getResources().getString(R.string.dialog_success));
                break;
            case DIALOG_NORMAL:
                alertDialog.changeAlertType(SweetAlertDialog.NORMAL_TYPE);
                alertDialog.setTitleText(activity.getResources().getString(R.string.dialog_success));
                break;
            case DIALOG_WARNING:
                alertDialog.changeAlertType(SweetAlertDialog.WARNING_TYPE);
                alertDialog.setTitleText(activity.getResources().getString(R.string.dialog_success));
                break;
        }

        alertDialog.setTitleText(message);
        //alertDialog.show();
        return alertDialog;
    }



    public static Dialog loading(){
        Activity activity = ActivityManager.peekTopActivity();
        Dialog loadalert = new SweetAlertDialog(activity);
        ((SweetAlertDialog)loadalert).changeAlertType(SweetAlertDialog.PROGRESS_TYPE);
        ((SweetAlertDialog)loadalert).setTitleText(activity.getResources().getString(R.string.LOADING));
        //loadalert.show();
        loadalert.setCancelable(false);
        ((SweetAlertDialog)loadalert).getProgressHelper().setBarColor(activity.getResources().getColor(R.color.blue_btn_bg_color));
        return loadalert;
    }

    public static Dialog dialogOk(String content, final DialogLinstener dialogLinstener){
        Activity activity = ActivityManager.peekTopActivity();
        Dialog alert = new SweetAlertDialog(activity);
        ((SweetAlertDialog)alert).changeAlertType(SweetAlertDialog.WARNING_TYPE);
        ((SweetAlertDialog)alert).setTitleText(activity.getResources().getString(R.string.dialog_warning));
        ((SweetAlertDialog)alert).setContentText(content);
        ((SweetAlertDialog)alert).setCancelText(activity.getResources().getString(R.string.dialog_cancel));
        ((SweetAlertDialog)alert).setConfirmText(activity.getResources().getString(R.string.dialog_ok));
        ((SweetAlertDialog)alert).showCancelButton(true);
        ((SweetAlertDialog)alert).setCancelable(false);
        ((SweetAlertDialog)alert).setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                dialogLinstener.cancel(sweetAlertDialog);
            }
        });
        ((SweetAlertDialog)alert).setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                dialogLinstener.confirm(sweetAlertDialog);
            }
        });
        return alert;
    }

}
