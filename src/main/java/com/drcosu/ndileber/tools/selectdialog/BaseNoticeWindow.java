package com.drcosu.ndileber.tools.selectdialog;

import android.view.View;
import android.widget.PopupWindow;

import com.drcosu.ndileber.mvp.data.model.SelectModel;

public abstract class BaseNoticeWindow  extends PopupWindow {

	public static final int TYPE_DISMISS_NORMAL = 1;
	public static final int TYPE_DISMISS_BUTTON = 2;
	
	protected OnButtonListener mListener = null;

	public OnButtonListener getButtonListener() {
		return mListener;
	}

	public void setButtonListener(OnButtonListener mListener) {
		this.mListener = mListener;
	}

	public interface OnButtonListener<T extends SelectModel> {

		public void onSureListener(View v, T selectModel);

		public void onDiscardListener(View v);
		
		public void onDismissListener(View v, int nType);
	}

}
