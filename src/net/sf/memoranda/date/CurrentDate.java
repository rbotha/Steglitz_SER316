/**
 * CurrentDate.java
 * Created on 13.02.2003, 2:11:03 Alex
 * Package: net.sf.memoranda.date
 *
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package net.sf.memoranda.date;
import java.util.Collection;
import java.util.Vector;

// TODO: Auto-generated Javadoc
/**
 * The Class CurrentDate.
 */
/*$Id: CurrentDate.java,v 1.4 2004/10/06 16:00:12 ivanrise Exp $*/
public class CurrentDate {

    /** The date. */
    private static CalendarDate _date = new CalendarDate();
    
    /** The date listeners. */
    private static Vector dateListeners = new Vector();

    /**
     * Gets the.
     *
     * @return the calendar date
     */
    public static CalendarDate get() {
        return _date;
    }

    /**
     * Sets the.
     *
     * @param date the date
     */
    public static void set(CalendarDate date) {
        if (date.equals(_date)) return;
        _date = date;
        dateChanged(date);
    }

    /**
     * Reset.
     */
    public static void reset() {
        set(new CalendarDate());
    }

    /**
     * Adds the date listener.
     *
     * @param dl the dl
     */
    public static void addDateListener(DateListener dl) {
        dateListeners.add(dl);
    }

    /**
     * Gets the change listeners.
     *
     * @return the change listeners
     */
    public static Collection getChangeListeners() {
        return dateListeners;
    }

    /**
     * Date changed.
     *
     * @param date the date
     */
    private static void dateChanged(CalendarDate date) {
        for (int i = 0; i < dateListeners.size(); i++)
            ((DateListener)dateListeners.get(i)).dateChange(date);
    }
}
