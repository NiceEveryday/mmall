package com.mmall.util;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static final String DEFAULT_FORMATTER = "yyyy-MM-dd HH:mm:ss";

    public static Date strToDate(String str,String formatter){
        DateTimeFormatter dateTimeFormat = DateTimeFormat.forPattern(formatter);
        DateTime dateTime = dateTimeFormat.parseDateTime(str);
        return dateTime.toDate();
    }

    public static String dateToStr(Date date,String formatter){
        if(date == null){
            return null;
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(formatter);
    }

    public static Date strToDate(String str){
        DateTimeFormatter dateTimeFormat = DateTimeFormat.forPattern(DEFAULT_FORMATTER);
        DateTime dateTime = dateTimeFormat.parseDateTime(str);
        return dateTime.toDate();
    }

    public static String dateToStr(Date date){
        if(date == null){
            return null;
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(DEFAULT_FORMATTER);
    }


}
