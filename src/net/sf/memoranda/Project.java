/**
 * Project.java
 * Created on 11.02.2003, 16:11:47 Alex
 * Package: net.sf.memoranda
 * 
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package net.sf.memoranda;

import net.sf.memoranda.date.CalendarDate;

// TODO: Auto-generated Javadoc
/**
 * The Interface Project.
 */

/*$Id: Project.java,v 1.5 2004/11/22 10:02:37 alexeya Exp $*/
public interface Project {
    
    /** The Constant SCHEDULED. */
    public static final int SCHEDULED = 0;
   
    /** The Constant ACTIVE. */
    public static final int ACTIVE = 1;
    
    /** The Constant COMPLETED. */
    public static final int COMPLETED = 2;
    
    /** The Constant FROZEN. */
    public static final int FROZEN = 4;
    
    /** The Constant FAILED. */
    public static final int FAILED = 5;
    
    /**
     * Gets the id.
     *
     * @return the id
     */
    String getID();
       
    /**
     * Gets the start date.
     *
     * @return the start date
     */
    CalendarDate getStartDate();
    
    /**
     * Sets the start date.
     *
     * @param date the new start date
     */
    void setStartDate(CalendarDate date);
    
    /**
     * Gets the end date.
     *
     * @return the end date
     */
    CalendarDate getEndDate();
    
    /**
     * Sets the end date.
     *
     * @param date the new end date
     */
    void setEndDate(CalendarDate date);
    
    /**
     * Gets the title.
     *
     * @return the title
     */
    String getTitle();
    
    /**
     * Sets the title.
     *
     * @param title the new title
     */
    void setTitle(String title);
    
    /**
     * Sets the description.
     *
     * @param description the new description
     */
    void setDescription(String description);
    
    /**
     * Gets the description.
     *
     * @return the description
     */
    String getDescription();
    
    /**
     * Gets the status.
     *
     * @return the status
     */
    int getStatus();
            
    //int getProgress();
    
    //TaskList getTaskList();
    
    //NoteList getNoteList();
    
    //ResourcesList getResourcesList();
    
    /**
     * Freeze.
     */
    void freeze();
    
    /**
     * Unfreeze.
     */
    void unfreeze();  
    
}
