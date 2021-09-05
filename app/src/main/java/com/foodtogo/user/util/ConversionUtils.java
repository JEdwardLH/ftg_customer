package com.foodtogo.user.util;

import android.util.Log;

import com.foodtogo.user.base.AppConstants;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ConversionUtils implements AppConstants {

    private static String FORMAT_CFM_DATA = "%.2f";
    private static final String GOOD_MORNING = "Good Morning ";
    private static final String GOOD_AFTERNOON = "Good Afternoon ";
    private static final String GOOD_EVENING = "Good Evening ";
    private static final String GOOD_NIGHT = "Good Night ";

    public static String md5Format(String s) {
        StringBuffer sb = null;
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(s.getBytes());
            sb = new StringBuffer();
            for (byte anArray : array) {
                sb.append(Integer.toHexString((anArray & 0xFF) | 0x100).substring(1, 3));
            }
        } catch (java.security.NoSuchAlgorithmException e) {
            Log.e(EXCEPTION, e.toString());
        }
        assert sb != null;
        return sb.toString();
    }

    public static String getDisplayOfDay() {
        String displayOfDay = "";
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
        if (timeOfDay >= 0 && timeOfDay < 12) {
            displayOfDay = GOOD_MORNING;
        } else if (timeOfDay >= 12 && timeOfDay < 16) {
            displayOfDay = GOOD_AFTERNOON;
        } else if (timeOfDay >= 16 && timeOfDay < 21) {
            displayOfDay = GOOD_EVENING;
        } else if (timeOfDay >= 21 && timeOfDay < 24) {
            displayOfDay = GOOD_NIGHT;
        }
        return displayOfDay;
    }

    public static String getFormatDate(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("E,  MMM dd");
        return dateFormat.format(date);
    }

    public static String getFormatTime(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
        return dateFormat.format(date).toUpperCase();
    }

    public static String getFormatDateTime(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return dateFormat.format(date);
    }

    public static String getFormatMonthYear(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("MM / yy");
        return dateFormat.format(date);
    }


    public static String getFormatDateTime(String dateTime) {
        if (dateTime != null) {
            DateFormat spf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date newDate = null;
            try {
                newDate = spf.parse(dateTime);
                spf = new SimpleDateFormat("d MMM yyyy, hh:mm a");
                dateTime = spf.format(newDate);
                return dateTime;
            } catch (ParseException e) {
                return dateTime;
            }
        } else {
            return dateTime;
        }
    }

    public static String getFormatDateTime1(String dateTime) {
        if (dateTime != null) {
            DateFormat spf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            Date newDate = null;
            try {
                newDate = spf.parse(dateTime);
                spf = new SimpleDateFormat("dd MMM yy, hh:mm a");
                dateTime = spf.format(newDate);
                return dateTime;
            } catch (ParseException e) {
                return dateTime;
            }
        } else {
            return dateTime;
        }
    }
 public static String getFormatDateHistory(String dateTime) {
        if (dateTime != null) {
            DateFormat spf = new SimpleDateFormat("MM/dd/yyyy");
            Date newDate = null;
            try {
                newDate = spf.parse(dateTime);
                spf = new SimpleDateFormat("d MMM yyyy");
                dateTime = spf.format(newDate);
                return dateTime;
            } catch (ParseException e) {
                return dateTime;
            }
        } else {
            return dateTime;
        }
    }


    public static String getFormatDate(String dateTime) {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date newDate = null;
        try {
            newDate = dateFormat.parse(dateTime);
            dateFormat = new SimpleDateFormat("MMMM yyyy");
            DateFormat newDayFormat = new SimpleDateFormat("dd");
            dateTime = dateFormat.format(newDate);
            String day = newDayFormat.format(newDate);
            return Integer.valueOf(day) + getDayNumberSuffix(Integer.valueOf(day)) + SPACE + dateTime;
        } catch (ParseException e) {
            return dateTime;
        }
    }


    private static String getDayNumberSuffix(int day) {
        if (day >= 11 && day <= 13) {
            return "th";
        }
        switch (day % 10) {
            case 1:
                return "st";
            case 2:
                return "nd";
            case 3:
                return "rd";
            default:
                return "th";
        }
    }

   public static String formatTwoDigit(long number){
       return String.format("%02d",number);
   }

   public static String currencyFormat(double number){
       String COUNTRY = "TH";
       String LANGUAGE = "th";
       String str = NumberFormat.getCurrencyInstance(new Locale(LANGUAGE, COUNTRY)).format(number);

       String s = String.format("%,d", number).replace(',', ' ');
       return s;
   }

}
