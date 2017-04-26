/**
 * Event.java
 * Created on 08.03.2003, 12:21:40 Alex
 * Package: net.sf.memoranda
 * 
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 *-----------------------------------------------------
 */
package net.sf.memoranda;
import java.util.Date;

import net.sf.memoranda.date.CalendarDate;

// TODO: Auto-generated Javadoc
/**
 * The Interface Event.
 */
/*$Id: Event.java,v 1.4 2004/07/21 17:51:25 ivanrise Exp $*/
public interface Event {
    
    /**
     * Gets the id.
     *
     * @return the id
     */
    String getId();
    
    //CalendarDate getDate();
    
    /**
     * Gets the hour.
     *
     * @return the hour
     */
    int getHour();
    
    /**
     * Gets the minute.
     *
     * @return the minute
     */
    int getMinute();
    
    //Date getTime();
    
    /**
     * Gets the text.
     *
     * @return the text
     */
    String getText();
    
    /**
     * Gets the note.
     *
     * @return the note
     */
    String getNote();

    /**
     * Gets the content.
     *
     * @return the content
     */
    nu.xom.Element getContent();
    
    /**
     * Gets the repeat.
     *
     * @return the repeat
     */
    int getRepeat();
    
    /**
     * Gets the start date.
     *
     * @return the start date
     */
    CalendarDate getStartDate();
    
    /**
     * Gets the end date.
     *
     * @return the end date
     */
    CalendarDate getEndDate();
    
    /**
     * Gets the period.
     *
     * @return the period
     */
    int getPeriod();
    
    /**
     * Checks if is repeatable.
     *
     * @return true, if is repeatable
     */
    boolean isRepeatable();
    
    /**
     * Gets the time.
     *
     * @return the time
     */
    Date getTime();
    
    /**
     * Gets the time string.
     *
     * @return the time string
     */
    String getTimeString();
    
	/**
	 * Gets the working days.
	 *
	 * @return the working days
	 */
	boolean getWorkingDays();
    
    /**
     * Gets the email.
     *
     * @return the email
     */
    String getEmail();
    
    /**
     * Send email.
     *
     * @return true, if successful
     */
    boolean sendEmail();
}
