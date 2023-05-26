package com.lihuzi.takenotes.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by cocav on 2017/11/15.
 */

public class DateUtils
{
    private static String _format = "yyyy-MM-dd HH:mm";
    private static ConcurrentHashMap<String, SimpleDateFormat> _map = new ConcurrentHashMap();

    public static String getTimeFormatString(long dateTime)
    {
        return getTimeFormatString(_format, dateTime);
    }

    public static String getTimeFormatString(String format, long dateTime)
    {
        String key = format + Thread.currentThread().getId();
        SimpleDateFormat formatter = _map.get(key);
        if (formatter == null)
        {
            formatter = new SimpleDateFormat(format);
            _map.put(key, formatter);
        }
        return formatter.format(dateTime);
    }

    public static Date getDate(long dateTime)
    {
        String format = "yyyy-MM-dd";
        String key = format + Thread.currentThread().getId();
        SimpleDateFormat formatter = _map.get(key);
        if (formatter == null)
        {
            formatter = new SimpleDateFormat(format);
            _map.put(key, formatter);
        }
        String time = formatter.format(dateTime) + " 00:00";
        try
        {
            return formatter.parse(time);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
