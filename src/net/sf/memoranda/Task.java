/**
 * Task.java
 * Created on 11.02.2003, 16:39:13 Alex
 * Package: net.sf.memoranda
 * 
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package net.sf.memoranda;

import java.sql.Timestamp;
import java.util.Collection;

import net.sf.memoranda.date.CalendarDate;

// TODO: Auto-generated Javadoc
/**
 * The Interface Task.
 */
/*$Id: Task.java,v 1.9 2005/06/16 04:21:32 alexeya Exp $*/
public interface Task {
    
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
    
    /** The Constant LOCKED. */
    public static final int LOCKED = 6;
    
    /** The Constant DEADLINE. */
    public static final int DEADLINE = 7;
    
    /** The Constant PRIORITY_LOWEST. */
    public static final int PRIORITY_LOWEST = 0;
    
    /** The Constant PRIORITY_LOW. */
    public static final int PRIORITY_LOW = 1;
    
    /** The Constant PRIORITY_NORMAL. */
    public static final int PRIORITY_NORMAL = 2;
    
    /** The Constant PRIORITY_HIGH. */
    public static final int PRIORITY_HIGH = 3;
    
    /** The Constant PRIORITY_HIGHEST. */
    public static final int PRIORITY_HIGHEST = 4;
    
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
     * Gets the status.
     *
     * @param date the date
     * @return the status
     */
    int getStatus(CalendarDate date);
    
    /**
     * Gets the progress.
     *
     * @return the progress
     */
    int getProgress();
    
    /**
     * Sets the progress.
     *
     * @param p the new progress
     */
    void setProgress(int p);
    
    /**
     * Gets the priority.
     *
     * @return the priority
     */
    int getPriority();
    
    /**
     * Sets the priority.
     *
     * @param p the new priority
     */
    void setPriority(int p);
    
    /**
     * Gets the id.
     *
     * @return the id
     */
    String getID();
    
    /**
     * Gets the text.
     *
     * @return the text
     */
    String getText();
    
    /**
     * Sets the text.
     *
     * @param s the new text
     */
    void setText(String s);
    
    /**
     * Gets the edits the.
     *
     * @return the edits the
     */
    //Set creation date and edit dates
    Timestamp getEdit();
    
    /**
     * Sets the edits the.
     *
     * @param s the new edits the
     */
    void setEdit(Timestamp s);
    
    /*Collection getDependsFrom();
    
    void addDependsFrom(Task task);
    
    void removeDependsFrom(Task task);*/
            
    /**
     * Gets the sub tasks.
     *
     * @return the sub tasks
     */
    Collection getSubTasks();    
    
    /**
     * Gets the sub task.
     *
     * @param id the id
     * @return the sub task
     */
    Task getSubTask(String id);
    
    /**
     * Checks for sub tasks.
     *
     * @param id the id
     * @return true, if successful
     */
    boolean hasSubTasks(String id);
    
    /**
     * Sets the effort.
     *
     * @param effort the new effort
     */
    void setEffort(long effort);
    
    /**
     * Gets the effort.
     *
     * @return the effort
     */
    long getEffort();
    
    /**
     * Sets the est LOC.
     *
     * @param estLOC the new est LOC
     */
    void setEstLOC(long estLOC);
    
    /**
     * Gets the est LOC.
     *
     * @return the est LOC
     */
    int getEstLOC();
    
    /**
     * Sets the act LOC.
     *
     * @param actLOC the new act LOC
     */
    void setActLOC(long actLOC);
    
    /**
     * Gets the act LOC.
     *
     * @return the act LOC
     */
    int getActLOC();
    
    /**
     * Sets the actual effort.
     *
     * @param actualEffort the new actual effort
     */
    // Set/get actual time in a task
    void setActualEffort(long actualEffort);
    
    /**
     * Gets the actual effort.
     *
     * @return the actual effort
     */
    long getActualEffort();
    
    /**
     * Sets the errors added.
     *
     * @param errorsAdded the new errors added
     */
    void setErrorsAdded(int errorsAdded);
    
    /**
     * Gets the errors added.
     *
     * @return the errors added
     */
    int getErrorsAdded();
    
    /**
     * Sets the errors fixed.
     *
     * @param errorsFixed the new errors fixed
     */
    void setErrorsFixed(int errorsFixed);
    
    /**
     * Gets the errors fixed.
     *
     * @return the errors fixed
     */
    int getErrorsFixed();
	
	/**
	 * Sets the timestamp.
	 *
	 * @param timestamp the new timestamp
	 */
	void setTimestamp(long timestamp);
    
    /**
     * Gets the timestamp.
     *
     * @return the timestamp
     */
    long getTimestamp();
    
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
     * Gets the parent task.
     *
     * @return the parent task
     */
    Task getParentTask();
    
    /**
     * Gets the parent id.
     *
     * @return the parent id
     */
    String getParentId();
    
    /**
     * Freeze.
     */
    void freeze();
    
    /**
     * Unfreeze.
     */
    void unfreeze();
	
	/**
	 * Gets the rate.
	 *
	 * @return the rate
	 */
	long getRate();
    
    /**
     * Gets the content.
     *
     * @return the content
     */
    nu.xom.Element getContent();
    
    /**
     * Gets the color.
     *
     * @return the color
     */
    int getColor();
    
    /**
     * Sets the color.
     *
     * @param c the new color
     */
    void setColor(int c);
}
