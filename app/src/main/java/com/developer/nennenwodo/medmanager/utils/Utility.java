package com.developer.nennenwodo.medmanager.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class Utility {

    private Utility(){} //prevent this class from being instantiated

    /**
     * Takes in the month as numbers and returns the string equivalent
     * @param month
     * @return MonthString
     */
    public static String toMonthString(String month){

        switch(month) {
            case "01":
                return "January";
            case "02":
                return "February";
            case "03":
                return "March";
            case "04":
                return "April";
            case "05":
                return "May";
            case "06":
                return "June";
            case "07":
                return "July";
            case "08":
                return "August";
            case "09":
                return "September";
            case "10":
                return "October";
            case "11":
                return "November";
            case "12":
                return "December";
            default:
                return null;

        }
    }

    /**
     * Takes in the day,month and year and returns the date in the yyyy-mm-dd format
     * @param day
     * @param month
     * @param year
     * @return
     */
    public static String formatDate(int day, int month, int year){

        String monthString, dayString, startDate;

        if(month < 10){
            monthString = "0"+month;
        }else{
            monthString = ""+month;
        }

        if(day < 10){
            dayString = "0"+day;
        }else{
            dayString = ""+day;
        }

        startDate = year + "-" + monthString + "-" + dayString;

        return startDate;
    }

    /**
     * Returns the year from a given date
     * @param date
     * @return
     */
    public static int getYear(String date){
        String[] dayMonthYear = date.split("-");
        String year = dayMonthYear[0];

        return Integer.parseInt(year);
    }

    /**
     * Returns the month from a given date
     * @param date
     * @return
     */
    public static int getMonth(String date){
        String[] dayMonthYear = date.split("-");
        String month = dayMonthYear[1];

        return Integer.parseInt(month) - 1;
    }

    /**
     * Returns the day from a given date
     * @param date
     * @return
     */
    public static int getDay(String date){
        String[] dayMonthYear = date.split("-");
        String day = dayMonthYear[2];

        return Integer.parseInt(day);
    }

    /**
     * Returns the hour from a given time string
     * @param time
     * @return
     */
    public static int getHour(String time){
        String[] hourMinuteArray = time.split(":");
        String hour = hourMinuteArray[0];

        return Integer.parseInt(hour);
    }

    /**
     * Returns the minute from a given time string
     * @param time
     * @return
     */
    public static int getMinute(String time){
        String[] hourMinuteArray = time.split(":");
        String minute = hourMinuteArray[1];

        return Integer.parseInt(minute);
    }


    /**
     * Takes in the hourOfDay and minute and returns the time in the hh:mm:ss format
     * @param hourOfDay
     * @param minute
     * @return
     */
    public static String formatTime(int hourOfDay, int minute){

        //hh:mm:ss

        String hourString, minuteString, time;

        if(hourOfDay < 10){
            hourString = "0"+hourOfDay;
        }else{
            hourString = ""+hourOfDay;
        }

        if(minute < 10){
            minuteString = "0"+minute;
        }else{
            minuteString = ""+minute;
        }

        time = hourString + ":" + minuteString + ":00";

        return time;

    }

    /**
     * Compares startDate and endDate and returns true if startDate comes before endDate
     * @param startDate
     * @param endDate
     * @return
     */
    public static boolean isBefore(String startDate, String endDate){

        try{

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date start = sdf.parse(startDate);
            Date end = sdf.parse(endDate);

            if(start.before(end)){
                return true;
            }else{
                return false;
            }

        }
        catch(ParseException ex){
            ex.printStackTrace();
        }

        return false;
    }

    /**
     * Gets the number of days between two calendar dates
     * @param startDate
     * @param endDate
     * @return
     */
    public static long daysBetween(Calendar startDate, Calendar endDate) {

        //Make sure we don't change the parameter passed
        Calendar newStart = Calendar.getInstance();
        newStart.setTimeInMillis(startDate.getTimeInMillis());
        newStart.set(Calendar.HOUR_OF_DAY, 0);
        newStart.set(Calendar.MINUTE, 0);
        newStart.set(Calendar.SECOND, 0);
        newStart.set(Calendar.MILLISECOND, 0);

        Calendar newEnd = Calendar.getInstance();
        newEnd.setTimeInMillis(endDate.getTimeInMillis());
        newEnd.set(Calendar.HOUR_OF_DAY, 0);
        newEnd.set(Calendar.MINUTE, 0);
        newEnd.set(Calendar.SECOND, 0);
        newEnd.set(Calendar.MILLISECOND, 0);

        long end = newEnd.getTimeInMillis();
        long start = newStart.getTimeInMillis();

        return TimeUnit.MILLISECONDS.toDays((end - start));
    }


    /**
     * Gets the number of days between two calendar dates
     * @param startDate
     * @param endDate
     * @return
     */
    public static long daysBetween(String startDate, String endDate) {

        int startYear = getYear(startDate);
        int startMonth = getMonth(startDate) - 1;
        int startDay = getDay(startDate);

        int endYear = getYear(endDate);
        int endMonth = getMonth(endDate) - 1;
        int endDay = getDay(endDate);

        //Make sure we don't change the parameter passed
        Calendar newStart = Calendar.getInstance();
        newStart.set(Calendar.YEAR, startYear);
        newStart.set(Calendar.MONTH, startMonth);
        newStart.set(Calendar.DAY_OF_MONTH, startDay);
        newStart.set(Calendar.HOUR_OF_DAY, 0);
        newStart.set(Calendar.MINUTE, 0);
        newStart.set(Calendar.SECOND, 0);
        newStart.set(Calendar.MILLISECOND, 0);

        Calendar newEnd = Calendar.getInstance();
        newEnd.set(Calendar.YEAR, endYear);
        newEnd.set(Calendar.MONTH, endMonth);
        newEnd.set(Calendar.DAY_OF_MONTH, endDay);
        newEnd.set(Calendar.HOUR_OF_DAY, 0);
        newEnd.set(Calendar.MINUTE, 0);
        newEnd.set(Calendar.SECOND, 0);
        newEnd.set(Calendar.MILLISECOND, 0);

        long end = newEnd.getTimeInMillis();
        long start = newStart.getTimeInMillis();

        return TimeUnit.MILLISECONDS.toDays((end - start));
    }


    /**
     * Gets the all the dates and times the user would take the medication for
     * @param startDate
     * @param startTime
     * @param endDate
     * @param interval
     * @return
     */
    public static List<List<String>> getMedicationTimes(String startDate, String startTime, String endDate, int interval){

        List<List<String>> allScheduleList = new ArrayList<>(); //today-0, past-1, upcoming-2
        List<String> todayScheduleList = new ArrayList<>();
        List<String> pastScheduleList = new ArrayList<>();
        List<String> upcomingScheduleList = new ArrayList<>();

        //init calendar start date
        Calendar calStartDate = Calendar.getInstance();
        calStartDate.set(Calendar.YEAR, Utility.getYear(startDate));
        calStartDate.set(Calendar.MONTH, Utility.getMonth(startDate));
        calStartDate.set(Calendar.DAY_OF_MONTH, Utility.getDay(startDate));
        calStartDate.set(Calendar.HOUR_OF_DAY, Utility.getHour(startTime));
        calStartDate.set(Calendar.MINUTE, Utility.getMinute(startTime));

        //init calendar end date
        Calendar calEndDate = Calendar.getInstance();
        calEndDate.set(Calendar.YEAR, Utility.getYear(endDate));
        calEndDate.set(Calendar.MONTH, Utility.getMonth(endDate));
        calEndDate.set(Calendar.DAY_OF_MONTH, Utility.getDay(endDate));
        calEndDate.set(Calendar.HOUR_OF_DAY, Utility.getHour(startTime));
        calEndDate.set(Calendar.MINUTE, Utility.getMinute(startTime));

        int daysBetweenStartAndFinish = (int) Utility.daysBetween(startDate, endDate);

        int numberOfIterations = interval * daysBetweenStartAndFinish;

        Date today = new Date();

        //loop and add specific time for medication into the past, today (present) and upcoming (future) list
        for(int i = 0; i < numberOfIterations; i++){

            Date dateTime = calStartDate.getTime();
            if(dateTime.before(today)){
                pastScheduleList.add(dateTime.toString());
            }else if(isSameDay(dateTime,today)){
                todayScheduleList.add(dateTime.toString());
            }else{
                upcomingScheduleList.add(dateTime.toString());
            }
            calStartDate.add(Calendar.HOUR, 24/interval);
        }

        allScheduleList.add(todayScheduleList);
        allScheduleList.add(pastScheduleList);
        allScheduleList.add(upcomingScheduleList);

        return allScheduleList;

    }

    /**
     * Checks if two dates are on the same day
     * @param date1
     * @param date2
     * @return true if the dates are on the same day and false otherwise
     */
    private static boolean isSameDay(Date date1, Date date2){
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);

        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }


}
