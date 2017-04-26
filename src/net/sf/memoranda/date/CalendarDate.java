/**
 * CalendarDate.java
 * Created on 11.02.2003, 18:02:02 Alex
 * Package: net.sf.memoranda
 *
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package net.sf.memoranda.date;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import net.sf.memoranda.util.Local;
import net.sf.memoranda.util.Util;

// TODO: Auto-generated Javadoc
/**
 * The Class CalendarDate.
 */
/*$Id: CalendarDate.java,v 1.3 2004/01/30 12:17:41 alexeya Exp $*/
public class CalendarDate {

    /** The year. */
    private int _year;
    
    /** The month. */
    private int _month;
    
    /** The day. */
    private int _day;

    /**
     * Date to calendar.
     *
     * @param date the date
     * @return the calendar
     */
    public static Calendar dateToCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    /**
     * Instantiates a new calendar date.
     */
    public CalendarDate() {
        this(Calendar.getInstance());
    }

    /**
     * Instantiates a new calendar date.
     *
     * @param day the day
     * @param month the month
     * @param year the year
     */
    public CalendarDate(int day, int month, int year) {
        _year = year;
        _month = month;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, _year);
        cal.set(Calendar.MONTH, _month);cal.getTime();
        int dmax = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        if (day <= dmax)
          _day = day;
        else
          _day = dmax;

    }

    /**
     * Instantiates a new calendar date.
     *
     * @param cal the cal
     */
    public CalendarDate(Calendar cal) {
        _year = cal.get(Calendar.YEAR);
        _day = cal.get(Calendar.DAY_OF_MONTH);
        _month = cal.get(Calendar.MONTH);
    }

    /**
     * Instantiates a new calendar date.
     *
     * @param date the date
     */
    public CalendarDate(Date date) {
        this(dateToCalendar(date));
    }

    /**
     * Instantiates a new calendar date.
     *
     * @param date the date
     */
    public CalendarDate(String date) {
        int[] d = Util.parseDateStamp(date);
        _day = d[0];
        _month = d[1];
        _year = d[2];

    }

    /**
     * Today.
     *
     * @return the calendar date
     */
    public static CalendarDate today() {
        return new CalendarDate();
    }

    /**
     * Yesterday.
     *
     * @return the calendar date
     */
    public static CalendarDate yesterday() {
        Calendar cal = Calendar.getInstance();
        cal.roll(Calendar.DATE, false);
        return new CalendarDate(cal);
    }

    /**
     * Tomorrow.
     *
     * @return the calendar date
     */
    public static CalendarDate tomorrow() {
        Calendar cal = Calendar.getInstance();
        cal.roll(Calendar.DATE, true);
        return new CalendarDate(cal);
    }

    /**
     * To calendar.
     *
     * @param day the day
     * @param month the month
     * @param year the year
     * @return the calendar
     */
    public static Calendar toCalendar(int day, int month, int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.getTime();
        return cal;
    }

    /**
     * To date.
     *
     * @param day the day
     * @param month the month
     * @param year the year
     * @return the date
     */
    public static Date toDate(int day, int month, int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        return cal.getTime();
    }

    /**
     * Gets the calendar.
     *
     * @return the calendar
     */
    public Calendar getCalendar() {
        return toCalendar(_day, _month, _year);
    }

    /**
     * Gets the date.
     *
     * @return the date
     */
    public Date getDate() {
        return toDate(_day, _month, _year);
    }

    /**
     * Gets the day.
     *
     * @return the day
     */
    public int getDay() {
        return _day;
    }

    /**
     * Gets the month.
     *
     * @return the month
     */
    public int getMonth() {
        return _month;
    }

    /**
     * Gets the year.
     *
     * @return the year
     */
    public int getYear() {
        return _year;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object object) {
        if (object.getClass().isInstance(CalendarDate.class)) {
            CalendarDate d2 = (CalendarDate) object;
            return ((d2.getDay() == getDay()) && (d2.getMonth() == getMonth()) && (d2.getYear() == getYear()));
        }
        else if (object.getClass().isInstance(Calendar.class)) {
            Calendar cal = (Calendar) object;
            return this.equals(new CalendarDate(cal));
        }
        else if (object.getClass().isInstance(Date.class)) {
            Date d = (Date) object;
            return this.equals(new CalendarDate(d));
        }
        return super.equals(object);
    }

    /**
     * Equals.
     *
     * @param date the date
     * @return true, if successful
     */
    public boolean equals(CalendarDate date) {
        if (date == null) return false;
        return ((date.getDay() == getDay()) && (date.getMonth() == getMonth()) && (date.getYear() == getYear()));
    }

    /**
     * Before.
     *
     * @param date the date
     * @return true, if successful
     */
    public boolean before(CalendarDate date) {
        if (date == null) return true;
        return this.getCalendar().before(date.getCalendar());
    }

    /**
     * After.
     *
     * @param date the date
     * @return true, if successful
     */
    public boolean after(CalendarDate date) {
        if (date == null) return true;
        return this.getCalendar().after(date.getCalendar());
    }

    /**
     * In period.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @return true, if successful
     */
    public boolean inPeriod(CalendarDate startDate, CalendarDate endDate) {
        return (after(startDate) && before(endDate)) || equals(startDate) || equals(endDate);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return Util.getDateStamp(this);
    }  
    
    /**
     * Gets the full date string.
     *
     * @return the full date string
     */
    public String getFullDateString() {
        return Local.getDateString(this, DateFormat.FULL);
    }
    
    /**
     * Gets the medium date string.
     *
     * @return the medium date string
     */
    public String getMediumDateString() {
        return Local.getDateString(this, DateFormat.MEDIUM);
    }
    
    /**
     * Gets the long date string.
     *
     * @return the long date string
     */
    public String getLongDateString() {
        return Local.getDateString(this, DateFormat.LONG);
    }
    
    /**
     * Gets the short date string.
     *
     * @return the short date string
     */
    public String getShortDateString() {
        return Local.getDateString(this, DateFormat.SHORT);
    }
    

}
