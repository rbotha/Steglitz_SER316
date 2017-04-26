/**
 * HistoryItem.java
 * Created on 07.03.2003, 18:31:39 Alex
 * Package: net.sf.memoranda
 * 
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package net.sf.memoranda;

import net.sf.memoranda.date.CalendarDate;

// TODO: Auto-generated Javadoc
/**
 * The Class HistoryItem.
 */
/*$Id: HistoryItem.java,v 1.4 2004/10/06 19:15:43 ivanrise Exp $*/
public class HistoryItem {
    
    /** The date. */
    private CalendarDate _date;
    
    /** The project. */
    private Project _project;
    
    /**
     * Constructor for HistoryItem.
     *
     * @param date the date
     * @param project the project
     */
    public HistoryItem(CalendarDate date, Project project) {
        _date = date;
        _project = project;
    }
    
    /**
     * Instantiates a new history item.
     *
     * @param note the note
     */
    public HistoryItem(Note note) {
        _date = note.getDate();
        _project = note.getProject();
    }
    
    /**
     * Gets the date.
     *
     * @return the date
     */
    public CalendarDate getDate() {
       return _date;
    }
    
    /**
     * Gets the project.
     *
     * @return the project
     */
    public Project getProject() {
       return _project;
    }
    
    /**
     * Equals.
     *
     * @param i the i
     * @return true, if successful
     */
    public boolean equals(HistoryItem i) {
       return i.getDate().equals(_date) && i.getProject().getID().equals(_project.getID());
    } 

}
