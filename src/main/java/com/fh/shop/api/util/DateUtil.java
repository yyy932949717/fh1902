package com.fh.shop.api.util;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static final String YYYY_MM_DD ="yyyy-MM-dd";
    public static final String YYYY_MM_DD_HH_MM_SS  ="yyyy-MM-dd HH:mm:ss";
    public static final String YYYYMMDDHHMMSS  ="yyyyMMddHHmmss";

    public static  String fromData(Date date,String pattern){
        if (date==null){
            return "";
        }
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat(pattern);
        String format = simpleDateFormat.format(date);
        return format;
    }

    public static Date parse(String string,String pattern) {
        if (StringUtils.isEmpty(string)) {
            return null;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date parse = null;
        try {
            parse = simpleDateFormat.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parse;
    }
}
