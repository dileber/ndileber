package com.drcosu.ndileber.repository;

import java.util.ArrayList;
import java.util.List;

/**
 * 控制字体
 * Created by jing on 2016/8/19.
 */
public class EmojiRepository {

    public static int lie = 8;
    public static int hang = 3;
    public static String del = "DEL";

    private final static int PAGE = lie*hang;

    public static final List<String> icon = new ArrayList<>();
    static {
        int start = 0x1F601;
        int last = 0x1F64F;
        int count = 0;
        while (true) {
            if(start==0x1F641||start==0x1F642||start==0x1F643||start==0x1F644){
                start++;
                continue;
            }
            if (start > last) {
                if(count%PAGE != 0){
                    icon.add(del);
                }
                break;
            }
            count++;
            if(count%PAGE == 0){
                if(count!=0){
                    icon.add(del);
                    count++;
                }
            }

           String emojiString = new String(Character.toChars(start++));
            icon.add(emojiString);
        }
    }

    public static List<String> getIcon(int page){
        int i = page-1;
        if(page==getPage()){
            return icon.subList(i*PAGE,icon.size());
        }else{
            return icon.subList(i*PAGE,PAGE*(1+i));
        }
    }

    public static int getPage(){
        int temp = icon.size();
        int page = temp/PAGE;
        double dpage = (double)temp/(double)PAGE;
        if(dpage>page){
            return page+1;
        }else{
            return page;
        }
    }

}
