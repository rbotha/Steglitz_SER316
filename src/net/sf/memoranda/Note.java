/**
 * Note.java
 * Created on 11.02.2003, 17:05:27 Alex
 * Package: net.sf.memoranda
 * 
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package net.sf.memoranda;

import java.sql.Timestamp;

import net.sf.memoranda.date.CalendarDate;
// TODO: Auto-generated Javadoc

/**
 * The Interface Note.
 */
/*$Id: Note.java,v 1.4 2004/09/30 17:19:52 ivanrise Exp $*/
public interface Note {
    
    /**
     * Gets the date.
     *
     * @return the date
     */
    CalendarDate getDate();
    
    /**
     * Gets the title.
     *
     * @return the title
     */
    String getTitle();
    
    /**
     * Sets the title.
     *
     * @param s the new title
     */
    void setTitle(String s);
    
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	String getId();
	
	/**
	 * Sets the id.
	 *
	 * @param s the new id
	 */
	void setId(String s);
	
	/**
	 * Gets the edits the.
	 *
	 * @return the edits the
	 */
	Timestamp getEdit();
	
	/**
	 * Sets the edits the.
	 *
	 * @param s the new edits the
	 */
	void setEdit(Timestamp s);
	
    /**
     * Checks if is marked.
     *
     * @return true, if is marked
     */
    boolean isMarked();
    
    /**
     * Sets the mark.
     *
     * @param mark the new mark
     */
    void setMark(boolean mark);
        
    /**
     * Gets the project.
     *
     * @return the project
     */
    Project getProject();
}
