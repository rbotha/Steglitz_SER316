/**
 * TaskList.java
 * Created on 21.02.2003, 12:25:16 Alex
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
 * The Interface TaskList.
 */
/*$Id: TaskList.java,v 1.8 2005/12/01 08:12:26 alexeya Exp $*/
public interface TaskList {

	/**
	 * Gets the project.
	 *
	 * @return the project
	 */
	Project getProject();
    
    /**
     * Gets the task.
     *
     * @param id the id
     * @return the task
     */
    Task getTask(String id);

    /**
     * Creates the task.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @param text the text
     * @param priority the priority
     * @param effort the effort
     * @param actualEffort the actual effort
     * @param timestamp the timestamp
     * @param description the description
     * @param parentTaskId the parent task id
     * @param errorsAdded the errors added
     * @param errorsFixed the errors fixed
     * @param estLOC the est LOC
     * @param actLOC the act LOC
     * @param edit the edit
     * @return the task
     */
    Task createTask(CalendarDate startDate, CalendarDate endDate, String text, int priority, long effort, long actualEffort,
    		long timestamp, String description, String parentTaskId, int errorsAdded, int errorsFixed, int estLOC, int actLOC, Timestamp edit);

    /**
     * Removes the task.
     *
     * @param task the task
     */
    void removeTask(Task task);

    /**
     * Checks for sub tasks.
     *
     * @param id the id
     * @return true, if successful
     */
    public boolean hasSubTasks(String id);
    
	/**
	 * Checks for parent task.
	 *
	 * @param id the id
	 * @return true, if successful
	 */
	public boolean hasParentTask(String id);

	/**
	 * Gets the top level tasks.
	 *
	 * @return the top level tasks
	 */
	public Collection getTopLevelTasks();
	
	/**
	 * Gets the all task.
	 *
	 * @return the all task
	 */
	public Collection getAllTask();
    
    /**
     * Gets the all sub tasks.
     *
     * @param taskId the task id
     * @return the all sub tasks
     */
    public Collection getAllSubTasks(String taskId);
    
    /**
     * Gets the active sub tasks.
     *
     * @param taskId the task id
     * @param date the date
     * @return the active sub tasks
     */
    public Collection getActiveSubTasks(String taskId,CalendarDate date);
    
//    public void adjustParentTasks(Task t);
    
    /**
 * Calculate total effort from sub tasks.
 *
 * @param t the t
 * @return the long
 */
public long calculateTotalEffortFromSubTasks(Task t);
    
    /**
     * Gets the latest end date from sub tasks.
     *
     * @param t the t
     * @return the latest end date from sub tasks
     */
    public CalendarDate getLatestEndDateFromSubTasks(Task t);
    
    /**
     * Gets the earliest start date from sub tasks.
     *
     * @param t the t
     * @return the earliest start date from sub tasks
     */
    public CalendarDate getEarliestStartDateFromSubTasks(Task t);
    
    /**
     * Calculate completion from sub tasks.
     *
     * @param t the t
     * @return the long[]
     */
    public long[] calculateCompletionFromSubTasks(Task t);

    /**
     * Gets the XML content.
     *
     * @return the XML content
     */
    nu.xom.Document getXMLContent();

}
