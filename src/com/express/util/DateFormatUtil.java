package com.express.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatUtil {
    private static SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-d HH:mm:ss");

    public static String format(Date date)//将传递过来的日期转换为固定的格式的字符串然后返回
    {
        return format.format(date);
    }
    public static long toTime(String formatString){
        try {
            return format.parse(formatString).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
