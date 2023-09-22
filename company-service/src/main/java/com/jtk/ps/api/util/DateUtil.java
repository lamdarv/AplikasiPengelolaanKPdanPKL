package com.jtk.ps.api.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    private DateUtil(){throw new IllegalStateException("Utility class");}

    private static String[] months = {"Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"};

    public static String parseDateToString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        return dateFormat.format(date);
    }

    public static String getDateIdNow() {
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH);
        String monthString = months[month];
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int year = cal.get(Calendar.YEAR);
        return day + " " + monthString + " " + year;
    }

    public static String parseDateToStringIdNow(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        int month = cal.get(Calendar.MONTH);
        String monthString = months[month];
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int year = cal.get(Calendar.YEAR);
        return day + " " + monthString + " " + year;
    }

    public static Date stringToDate(String date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        try {
            return dateFormat.parse(date);
        } catch (Exception e) {
            return null;
        }
    }

    public static Boolean checkNowDate(String startDate, String endDate) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        try {
            Date nowDate = new Date();
            Date dateStart = dateFormat.parse(startDate);
            Date dateEnd = dateFormat.parse(endDate);

            return nowDate.after(dateStart) && nowDate.before(dateEnd);
        } catch (Exception e) {
            return null;
        }
    }
}
