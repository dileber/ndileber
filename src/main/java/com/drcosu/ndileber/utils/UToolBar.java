package com.drcosu.ndileber.utils;

import com.drcosu.ndileber.R;

/**
 * Created by H2 on 2016/9/7.
 */
public class UToolBar {

    /**
     * toolbar的title资源id
     */
    public int titleId = 0;
    /**
     * toolbar的title
     */
    public String titleString;
    /**
     * toolbar的logo资源id
     */
    public int logoId = 0;
    /**
     * toolbar的返回按钮资源id，默认开启的资源nim_actionbar_dark_back_icon
     */
    public int navigateId = R.mipmap.dileber_actionbar_white_back_icon;
    /**
     * toolbar的返回按钮，默认开启
     */
    public boolean isNeedNavigate = true;

    public int background = 0;

    public UToolBar(){

    }

    public UToolBar(int titleId, String titleString, int logoId, int navigateId, boolean isNeedNavigate, int background) {
        this.titleId = titleId;
        this.titleString = titleString;
        this.logoId = logoId;
        this.navigateId = navigateId;
        this.isNeedNavigate = isNeedNavigate;
        this.background = background;
    }

    public int getTitleId() {
        return titleId;
    }

    public void setTitleId(int titleId) {
        this.titleId = titleId;
    }

    public String getTitleString() {
        return titleString;
    }

    public void setTitleString(String titleString) {
        this.titleString = titleString;
    }

    public int getLogoId() {
        return logoId;
    }

    public void setLogoId(int logoId) {
        this.logoId = logoId;
    }

    public int getNavigateId() {
        return navigateId;
    }

    public void setNavigateId(int navigateId) {
        this.navigateId = navigateId;
    }

    public boolean isNeedNavigate() {
        return isNeedNavigate;
    }

    public void setNeedNavigate(boolean needNavigate) {
        isNeedNavigate = needNavigate;
    }

    public int getBackground() {
        return background;
    }

    public void setBackground(int background) {
        this.background = background;
    }
}
