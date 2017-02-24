package com.drcosu.ndileber.tools;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

public class UClipboard {
	/**
	 * 拷贝文字到剪贴版
	 * @param context
	 * @param lable 描述
	 * @param text 内容
     */
	public static final void clipboardCopyText(Context context, CharSequence lable, CharSequence text) {
		ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
		if (cm != null) {
			ClipData clipData = ClipData.newPlainText("simple text",text);
			cm.setPrimaryClip(clipData);
		}
	}


	/**
	 * 获取剪贴版里的文字长度
	 * @param context
	 * @return
     */
	public static final int clipboardTextLength(Context context) {
		ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
		if(cm!=null){
			ClipData clipData=cm.getPrimaryClip();
			if(clipData!=null&&clipData.getItemCount()>0){
				CharSequence text = clipData.getItemAt(0).getText();
				return text!=null?text.length():0;
			}
		}
		return 0;
	}
}
