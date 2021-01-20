package com.yu.swing.utils;

import java.text.SimpleDateFormat;

public class DateUtil {
    public  static Long getSdf(String date)  {
        Long l=0L;
        try {
            l = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date).getTime();
        }catch (Exception e){
            e.printStackTrace();
        }
        return l;
    }


}
