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

    public boolean isInRange(String beginTime, String endTime, String searchParameter){
        try {
            String string1 = beginTime;
            Date time1 = new SimpleDateFormat("HH:mm:ss").parse(string1);
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(time1);

            String string2 = endTime;
            Date time2 = new SimpleDateFormat("HH:mm:ss").parse(string2);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(time2);
            calendar2.add(Calendar.DATE, 1);

            String someRandomTime = searchParameter;
            Date d = new SimpleDateFormat("HH:mm:ss").parse(someRandomTime);
            Calendar calendar3 = Calendar.getInstance();
            calendar3.setTime(d);
            calendar3.add(Calendar.DATE, 1);

            Date x = calendar3.getTime();
            if (x.after(calendar1.getTime()) && x.before(calendar2.getTime())) {
                //checkes whether the current time is between 14:49:00 and 20:11:13.
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }
}
