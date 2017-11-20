package com.usc.zsurani.grubmate.com.usc.zsurani.grubmate.repos;
import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Shivangi on 11/19/17.
 */

public class Time {
    public long calculateTimeDifference(String begin, String end){
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        try {
            Date date1 = format.parse(begin);
            Date date2 = format.parse(end);
            long difference = date2.getTime() - date1.getTime();
            if (difference < 0){
                return date1.getTime() - date2.getTime();
            }
            return difference;
        }
        catch (ParseException e){
            System.out.println("do nothing");
        }
        return -1;
    }
    public String getCurrentTime(){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return  sdf.format(cal.getTime());
    }
}
