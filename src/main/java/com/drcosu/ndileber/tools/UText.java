package com.drcosu.ndileber.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.TextView;
import android.widget.Toast;

/** 
 * @author  SHI: 
 * @date 创建时间：2015年4月7日 上午11:46:15 
 */
public class UText {
	public static boolean checkEditText(String edit,int num){
		if(edit.length()<num){
			return false;
		}
		return true;
	}
	
	public static boolean isMobileNO(String mobiles) {  
	    String telRegex = "[1][3-8]\\d{9}";
	    if (TextUtils.isEmpty(mobiles)) 
	    	return false;  
	    else 
	    	return mobiles.matches(telRegex);  
	   }

	public static final Pattern PATTERN_N_A_Z = Pattern.compile("[0-9a-zA-Z]*");

	public static void getWatcher(Editable s,Pattern pattern){
		if (s.length() > 0) {
			int pos = s.length() - 1;
			char c = s.charAt(pos);
			Matcher isPattern = pattern.matcher(String.valueOf(c));
			if (!isPattern.matches()) {
				//这里限制在字串最后追加#
				s.delete(pos,pos+1);
			}
		}
	}
	
}
