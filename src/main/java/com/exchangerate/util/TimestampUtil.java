package com.exchangerate.util;

import java.sql.Timestamp;
import java.util.Calendar;

public class TimestampUtil {

    public static Timestamp addToTimestamp(Timestamp temp, int time, int unit) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(temp.getTime());
        calendar.add(unit, time);
        temp = new Timestamp(calendar.getTime().getTime());
        return temp;
    }
}
