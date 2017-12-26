//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.drcosu.ndileber.view;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

public abstract class BasePopupWindow extends PopupWindow {
  protected Activity baseActivity;

  public BasePopupWindow(Activity baseActivity) {
    super(baseActivity);
    this.baseActivity = baseActivity;
    this.initWindowAttrs();
    View contentView = this.createWindowContentView();
    this.setContentView(contentView);
    this.onViewCreated(contentView);
  }

  private View createWindowContentView() {
    return LayoutInflater.from(this.baseActivity).inflate(this.getWindowLayoutId(), (ViewGroup)null);
  }

  private void initWindowAttrs() {
    this.setWidth(this.getWindowWidth());
    this.setHeight(this.getWindowHeight());
    this.setFocusable(true);
    this.setOutsideTouchable(this.enableOutsideTouch());
    if(this.enableOutsideTouch()) {
      this.setBackgroundDrawable(new ColorDrawable(0));
    }

    this.otherAttrs();
    this.update();
  }

  protected void otherAttrs() {
  }

  protected boolean enableOutsideTouch() {
    return true;
  }

  protected abstract int getWindowWidth();

  protected abstract int getWindowHeight();

  protected abstract int getWindowLayoutId();

  protected abstract void onViewCreated(View var1);
}
