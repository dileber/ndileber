package com.drcosu.ndileber.tools;

import android.content.res.ColorStateList;
import android.support.design.widget.TextInputLayout;

import java.lang.reflect.Field;

/**
 * TextInputLayout工具
 * Created by hyy on 2017/12/18.
 */

public class UTextInputLayout {

    public static void changeTextInputLayoutLabelColor(TextInputLayout layout, int color) {
        if (layout != null) {
            try {
                Field field = layout.getClass().getDeclaredField("mFocusedTextColor");
                field.setAccessible(true);
                field.set(layout, createColorStateList(color, 0, 0, 0));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public static ColorStateList createColorStateList(int normal, int pressed, int select,
                                                      int unable) {
        if (pressed == 0) {
            pressed = normal;
        }
        if (select == 0) {
            select = normal;
        }
        if (unable == 0) {
            unable = normal;
        }
        int[] colors = new int[] {
                pressed, select, unable, normal
        };
        int[][] states = new int[4][];
        states[0] = new int[] {
                android.R.attr.state_pressed, android.R.attr.state_enabled
        };
        states[1] = new int[] {
                android.R.attr.state_enabled, android.R.attr.state_selected
        };
        states[2] = new int[] {
                -android.R.attr.state_enabled
        };
        states[3] = new int[] {
                android.R.attr.state_enabled
        };
        return new ColorStateList(states, colors);
    }

}
