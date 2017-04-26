/**
 * DefaultTask.java
 * Created on 12.02.2003, 15:30:40 Alex
 * Package: net.sf.memoranda
 *
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package net.sf.memoranda;

import java.util.Collection;
import java.util.Vector;
import java.sql.Timestamp;
import java.util.Calendar;

import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.date.CurrentDate;
import nu.xom.Attribute;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.Node;

// TODO: Auto-generated Javadoc
/**
 * The Class TaskImpl.
 */
/*$Id: TaskImpl.java,v 1.15 2005/12/01 08:12:26 alexeya Exp $*/
public class TaskImpl implements Task, Comparable {

    /** The element. */
    private Element _element = null;
    
    /** The tl. */
    private TaskList _tl = null;

    /**
     * Constructor for DefaultTask.
     *
     * @param taskElement the task element
     * @param tl the tl
     */
    public TaskImpl(Element taskElement, TaskList tl) {
        _element = taskElement;
        _tl = tl;
    }

    /* (non-Javadoc)
     * @see net.sf.memoranda.Task#getContent()
     */
    public Element getContent() {
        return _element;
    }

    /* (non-Javadoc)
     * @see net.sf.memoranda.Task#getStartDate()
     */
    public CalendarDate getStartDate() {
        return new CalendarDate(_element.getAttribute("startDate").getValue());
    }

    /* (non-Javadoc)
     * @see net.sf.memoranda.Task#setStartDate(net.sf.memoranda.date.CalendarDate)
     */
    public void setStartDate(CalendarDate date) {
           setAttr("startDate", date.toString());
    }

    /* (non-Javadoc)
     * @see net.sf.memoranda.Task#getEndDate()
     */
    public CalendarDate getEndDate() {
		String ed = _element.getAttribute("endDate").getValue();
		if (ed != "")
			return new CalendarDate(_element.getAttribute("endDate").getValue());
		Task parent = this.getParentTask();
		if (parent != null)
			return parent.getEndDate();
		Project pr = this._tl.getProject();
		if (pr.getEndDate() != null)
			return pr.getEndDate();
		return this.getStartDate();
        
    }

    /* (non-Javadoc)
     * @see net.sf.memoranda.Task#setEndDate(net.sf.memoranda.date.CalendarDate)
     */
    public void setEndDate(CalendarDate date) {
		if (date == null)
			setAttr("endDate", "");
		else
		setAttr("endDate", date.toString());
    }

    /* (non-Javadoc)
     * @see net.sf.memoranda.Task#getEffort()
     */
    public long getEffort() {
    	Attribute attr = _element.getAttribute("effort");
    	if (attr == null) {
    		return 0;
    	}
    	else {
    		try {
        		return Long.parseLong(attr.getValue());
    		}
    		catch (NumberFormatException e) {
    			return 0;
    		}
    	}
    }

    /* (non-Javadoc)
     * @see net.sf.memoranda.Task#setEffort(long)
     */
    public void setEffort(long effort) {
        setAttr("effort", String.valueOf(effort));
    }
    
	/**
	 * 	Gets the actual effort (in hours) spent on a task.
	 *
	 * @return the actual effort
	 */
    public long getActualEffort() {
    	Attribute attr = _element.getAttribute("actualEffort");
    	if (attr == null) {
    		return 0;
    	}
    	else {
    		try {
        		return Long.parseLong(attr.getValue());
    		}
    		catch (NumberFormatException e) {
    			return 0;
    		}
    	}
    }

	/**
	 * 	Sets the actual effort (in hours) spent on a task.
	 *
	 * @param actualEffort the new actual effort
	 */
    public void setActualEffort(long actualEffort) {
        setAttr("actualEffort", String.valueOf(actualEffort));
    }
    
	/**
	 * 	Gets the number of logged errors in a task.
	 *
	 * @return the errors added
	 */
    public int getErrorsAdded() {
    	Attribute attr = _element.getAttribute("errorsAdded");
    	if (attr == null) {
    		return 0;
    	}
    	else {
    		try {
        		return Integer.parseInt(attr.getValue());
    		}
    		catch (NumberFormatException e) {
    			return 0;
    		}
    	}
    }

	/**
	 * 	Sets the number of logged errors in a task.
	 *
	 * @param errorsAdded the new errors added
	 */
    public void setErrorsAdded(int errorsAdded) {
        setAttr("errorsAdded", String.valueOf(errorsAdded));
    }
    
	/**
	 * 	Gets the number of fixed errors in a task.
	 *
	 * @return the errors fixed
	 */
    public int getErrorsFixed() {
    	Attribute attr = _element.getAttribute("errorsFixed");
    	if (attr == null) {
    		return 0;
    	}
    	else {
    		try {
        		return Integer.parseInt(attr.getValue());
    		}
    		catch (NumberFormatException e) {
    			return 0;
    		}
    	}
    }

	/**
	 * 	Sets the number of fixed errors in a task.
	 *
	 * @param errorsFixed the new errors fixed
	 */
    public void setErrorsFixed(int errorsFixed) {
        setAttr("errorsFixed", String.valueOf(errorsFixed));
    }
	
	/**
	 * 	Returns the timestamped time in milliseconds from the epoch (1970-01-01T00:00:00Z)
	 * 		or -1 if there is no timestamp or error.
	 *
	 * @return the timestamp
	 */
	public long getTimestamp() {
    	Attribute attr = _element.getAttribute("timestamp");
    	if (attr == null) {
    		return -1;
    	}
    	else {
    		try {
        		return Long.parseLong(attr.getValue());
    		}
    		catch (NumberFormatException e) {
    			return -1;
    		}
    	}
    }

	/**
	 * 	Sets the timestamped time in milliseconds from the epoch (1970-01-01T00:00:00Z).
	 *
	 * @param timestamp the new timestamp
	 */
    public void setTimestamp(long timestamp) {
        setAttr("timestamp", String.valueOf(timestamp));
    }
	
	/* (non-Javadoc)
	 * @see net.sf.memoranda.Task#getParentTask()
	 */
	/* 
	 * @see net.sf.memoranda.Task#getParentTask()
	 */
	public Task getParentTask() {
		Node parentNode = _element.getParent();
    	if (parentNode instanceof Element) {
    	    Element parent = (Element) parentNode;
        	if (parent.getLocalName().equalsIgnoreCase("task")) 
        	    return new TaskImpl(parent, _tl);
    	}
    	return null;
	}
	
	/* (non-Javadoc)
	 * @see net.sf.memoranda.Task#getParentId()
	 */
	public String getParentId() {
		Task parent = this.getParentTask();
		if (parent != null)
			return parent.getID();
		return null;
	}

    /* (non-Javadoc)
     * @see net.sf.memoranda.Task#getDescription()
     */
    public String getDescription() {
    	Element thisElement = _element.getFirstChildElement("description");
    	if (thisElement == null) {
    		return null;
    	}
    	else {
       		return thisElement.getValue();
    	}
    }

    /* (non-Javadoc)
     * @see net.sf.memoranda.Task#setDescription(java.lang.String)
     */
    public void setDescription(String s) {
    	Element desc = _element.getFirstChildElement("description");
    	if (desc == null) {
        	desc = new Element("description");
            desc.appendChild(s);
            _element.appendChild(desc);    	
    	}
    	else {
            desc.removeChildren();
            desc.appendChild(s);    	
    	}
    }

    /**
     * s.
     *
     * @param date the date
     * @return the status
     * @see net.sf.memoranda.Task#getStatus()
     */
    public int getStatus(CalendarDate date) {
        CalendarDate start = getStartDate();
        CalendarDate end = getEndDate();
        if (isFrozen())
            return Task.FROZEN;
        if (isCompleted())
                return Task.COMPLETED;
        
		if (date.inPeriod(start, end)) {
            if (date.equals(end))
                return Task.DEADLINE;
            else
                return Task.ACTIVE;
        }
		else if(date.before(start)) {
				return Task.SCHEDULED;
		}
		
		if(start.after(end)) {
			return Task.ACTIVE;
		}

        return Task.FAILED;
    }
    /**
     * Method isDependsCompleted.
     * @return boolean
     */
/*
    private boolean isDependsCompleted() {
        Vector v = (Vector) getDependsFrom();
        boolean check = true;
        for (Enumeration en = v.elements(); en.hasMoreElements();) {
            Task t = (Task) en.nextElement();
            if (t.getStatus() != Task.COMPLETED)
                check = false;
        }
        return check;
    }
*/
    private boolean isFrozen() {
        return _element.getAttribute("frozen") != null;
    }

    /**
     * Checks if is completed.
     *
     * @return true, if is completed
     */
    private boolean isCompleted() {
        return getProgress() == 100;
    }

    /**
     * Gets the id.
     *
     * @return the id
     * @see net.sf.memoranda.Task#getID()
     */
    public String getID() {
        return _element.getAttribute("id").getValue();
    }

    /**
     * Gets the text.
     *
     * @return the text
     * @see net.sf.memoranda.Task#getText()
     */
    public String getText() {
        return _element.getFirstChildElement("text").getValue();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return getText();
    }
    
    /**
     * Sets the text.
     *
     * @param s the new text
     * @see net.sf.memoranda.Task#setText()
     */
    public void setText(String s) {
        _element.getFirstChildElement("text").removeChildren();
        _element.getFirstChildElement("text").appendChild(s);
    }

    /**
     * Freeze.
     *
     * @see net.sf.memoranda.Task#freeze()
     */
    public void freeze() {
        setAttr("frozen", "yes");
    }

    /**
     * Unfreeze.
     *
     * @see net.sf.memoranda.Task#unfreeze()
     */
    public void unfreeze() {
        if (this.isFrozen())
            _element.removeAttribute(new Attribute("frozen", "yes"));
    }

    /**
     * Gets the depends from.
     *
     * @return the depends from
     * @see net.sf.memoranda.Task#getDependsFrom()
     */
    public Collection getDependsFrom() {
        Vector v = new Vector();
        Elements deps = _element.getChildElements("dependsFrom");
        for (int i = 0; i < deps.size(); i++) {
            String id = deps.get(i).getAttribute("idRef").getValue();
            Task t = _tl.getTask(id);
            if (t != null)
                v.add(t);
        }
        return v;
    }
    
    /**
     * Adds the depends from.
     *
     * @param task the task
     * @see net.sf.memoranda.Task#addDependsFrom(net.sf.memoranda.Task)
     */
    public void addDependsFrom(Task task) {
        Element dep = new Element("dependsFrom");
        dep.addAttribute(new Attribute("idRef", task.getID()));
        _element.appendChild(dep);
    }
    
    /**
     * Removes the depends from.
     *
     * @param task the task
     * @see net.sf.memoranda.Task#removeDependsFrom(net.sf.memoranda.Task)
     */
    public void removeDependsFrom(Task task) {
        Elements deps = _element.getChildElements("dependsFrom");
        for (int i = 0; i < deps.size(); i++) {
            String id = deps.get(i).getAttribute("idRef").getValue();
            if (id.equals(task.getID())) {
                _element.removeChild(deps.get(i));
                return;
            }
        }
    }
    
    /**
     * Gets the progress.
     *
     * @return the progress
     * @see net.sf.memoranda.Task#getProgress()
     */
    public int getProgress() {
        return new Integer(_element.getAttribute("progress").getValue()).intValue();
    }
    
    /**
     * Sets the progress.
     *
     * @param p the new progress
     * @see net.sf.memoranda.Task#setProgress(int)
     */
    public void setProgress(int p) {
        if ((p >= 0) && (p <= 100))
            setAttr("progress", new Integer(p).toString());
    }
    
    /**
     * Gets the priority.
     *
     * @return the priority
     * @see net.sf.memoranda.Task#getPriority()
     */
    public int getPriority() {
        Attribute pa = _element.getAttribute("priority");
        if (pa == null)
            return Task.PRIORITY_NORMAL;
        return new Integer(pa.getValue()).intValue();
    }
    
    /**
     * Sets the priority.
     *
     * @param p the new priority
     * @see net.sf.memoranda.Task#setPriority(int)
     */
    public void setPriority(int p) {
        setAttr("priority", String.valueOf(p));
    }

    /**
     * Sets the attr.
     *
     * @param a the a
     * @param value the value
     */
    private void setAttr(String a, String value) {
        Attribute attr = _element.getAttribute(a);
        if (attr == null)
           _element.addAttribute(new Attribute(a, value));
        else
            attr.setValue(value);
    }

	/**
	 * A "Task rate" is an informal index of importance of the task
	 * considering priority, number of days to deadline and current 
	 * progress. 
	 * 
	 * rate = (100-progress) / (numOfDays+1) * (priority+1)
	 *
	 * @param d the d
	 * @return long
	 */

	private long calcTaskRate(CalendarDate d) {
		Calendar endDateCal = getEndDate().getCalendar();
		Calendar dateCal = d.getCalendar();
		int numOfDays = (endDateCal.get(Calendar.YEAR)*365 + endDateCal.get(Calendar.DAY_OF_YEAR)) 
				- (dateCal.get(Calendar.YEAR)*365 + dateCal.get(Calendar.DAY_OF_YEAR));
		if (numOfDays < 0) return -1; //Something wrong ?
		return (100-getProgress()) / (numOfDays+1) * (getPriority()+1);
	}

    /**
     * Gets the rate.
     *
     * @return the rate
     * @see net.sf.memoranda.Task#getRate()
     */
	 
     public long getRate() {
/*	   Task t = (Task)task;
	   switch (mode) {
		   case BY_IMP_RATE: return -1*calcTaskRate(t, date);
		   case BY_END_DATE: return t.getEndDate().getDate().getTime();
		   case BY_PRIORITY: return 5-t.getPriority();
		   case BY_COMPLETION: return 100-t.getProgress();
	   }
       return -1;
*/
		return -1*calcTaskRate(CurrentDate.get());
	 }
	   
	 /*
	  * Comparable interface
	  */
	  
	 /* (non-Javadoc)
 	 * @see java.lang.Comparable#compareTo(java.lang.Object)
 	 */
 	public int compareTo(Object o) {
		 Task task = (Task) o;
		 	if(getRate() > task.getRate())
				return 1;
			else if(getRate() < task.getRate())
				return -1;
			else 
				return 0;
	 }
	 
	 /* (non-Javadoc)
 	 * @see java.lang.Object#equals(java.lang.Object)
 	 */
 	public boolean equals(Object o) {
	     return ((o instanceof Task) && (((Task)o).getID().equals(this.getID())));
	 }

	/* (non-Javadoc)
	 * @see net.sf.memoranda.Task#getSubTasks()
	 */
	/* 
	 * @see net.sf.memoranda.Task#getSubTasks()
	 */
	public Collection getSubTasks() {
		Elements subTasks = _element.getChildElements("task");
            return convertToTaskObjects(subTasks);
	}

	/**
	 * Convert to task objects.
	 *
	 * @param tasks the tasks
	 * @return the collection
	 */
	private Collection convertToTaskObjects(Elements tasks) {
        Vector v = new Vector();
        for (int i = 0; i < tasks.size(); i++) {
            Task t = new TaskImpl(tasks.get(i), _tl);
            v.add(t);
        }
        return v;
    }
	
	/* (non-Javadoc)
	 * @see net.sf.memoranda.Task#getSubTask(java.lang.String)
	 */
	/* 
	 * @see net.sf.memoranda.Task#getSubTask(java.lang.String)
	 */
	public Task getSubTask(String id) {
		Elements subTasks = _element.getChildElements("task");
		for (int i = 0; i < subTasks.size(); i++) {
			if (subTasks.get(i).getAttribute("id").getValue().equals(id))
				return new TaskImpl(subTasks.get(i), _tl);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see net.sf.memoranda.Task#hasSubTasks(java.lang.String)
	 */
	/* 
	 * @see net.sf.memoranda.Task#hasSubTasks()
	 */
	public boolean hasSubTasks(String id) {
		Elements subTasks = _element.getChildElements("task");
		for (int i = 0; i < subTasks.size(); i++) 
			if (subTasks.get(i).getAttribute("id").getValue().equals(id))
				return true;
		return false;
	}
  
	

	/* (non-Javadoc)
	 * @see net.sf.memoranda.Task#getEdit()
	 */
	@Override
	public Timestamp getEdit() {
		//Get date task was edited
		Attribute ts = _element.getAttribute("edit");
		if (ts == null)//If timestamp is null/empty
			return null;
		return Timestamp.valueOf(_element.getAttribute("edit").getValue());
	}

	/* (non-Javadoc)
	 * @see net.sf.memoranda.Task#setEdit(java.sql.Timestamp)
	 */
	@Override
	public void setEdit(Timestamp s) {
		// Set date task was edited
		Attribute ts = _element.getAttribute("edit");
		if (ts == null) _element.addAttribute(new Attribute("edit", s.toString()));
		else 
			ts.setValue(s.toString());
		
	}
  
  /* (non-Javadoc)
   * @see net.sf.memoranda.Task#setEstLOC(long)
   */
  public void setEstLOC(long estLOC) {
	  	setAttr("estloc", String.valueOf(estLOC));
		
	}
	
	/* (non-Javadoc)
	 * @see net.sf.memoranda.Task#getEstLOC()
	 */
	public int getEstLOC() {
		// TODO Auto-generated method stub
		Attribute attr = _element.getAttribute("estloc");
    	if (attr == null) {
    		return 0;
    	}
    	else {
    		try {
        		return Integer.parseInt(attr.getValue());
    		}
    		catch (NumberFormatException e) {
    			return 0;
    		}
    	}
	}

	/* (non-Javadoc)
	 * @see net.sf.memoranda.Task#setActLOC(long)
	 */
	public void setActLOC(long actLOC) {
		setAttr("actloc", String.valueOf(actLOC));
		
	}

	/* (non-Javadoc)
	 * @see net.sf.memoranda.Task#getActLOC()
	 */
	public int getActLOC() {
		Attribute attr = _element.getAttribute("actloc");
    	if (attr == null) {
    		return 0;
    	}
    	else {
    		try {
        		return Integer.parseInt(attr.getValue());
    		}
    		catch (NumberFormatException e) {
    			return 0;
    		}
    	}
	}
	
	/**
	 * This function returns the color value (-1 for none, otherwise 0-9).
	 *
	 * @return color value
	 */
    public int getColor() {
        try {
            return new Integer(_element.getAttribute("taskColor").getValue());
        } catch (Exception e) {
            return -1;
        }
    }
    
    /**
     * This function sets the color field in task.
     *
     * @param c the new color
     */
    public void setColor(int c) {
        if (c >= -1) {
            setAttr("taskColor", ""+c);
        }
    }
}
