package com.drcosu.ndileber.repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jing on 2016/8/19.
 */
public class EmojiRepository {

    public static final List<String> icon = new ArrayList<>();
    static {
        int start = 0x1F601;
        int last = 0x1F64F;
        while (true) {
            if(start==0x1F641||start==0x1F642||start==0x1F643||start==0x1F644){
                start++;
                continue;
            }
            if (start > last) {
                break;
            }
            String emojiString = new String(Character.toChars(start++));
            icon.add(emojiString);
        }
    }

}
