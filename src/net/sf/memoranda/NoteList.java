/**
 * NoteList.java
 * Created on 21.02.2003, 15:40:46 Alex
 * Package: net.sf.memoranda
 * 
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package net.sf.memoranda;
import java.util.Collection;

import net.sf.memoranda.date.CalendarDate;
// TODO: Auto-generated Javadoc

/**
 * The Interface NoteList.
 */
/*$Id: NoteList.java,v 1.5 2004/10/07 21:33:36 ivanrise Exp $*/
public interface NoteList {
    
    /**
     * Gets the all notes.
     *
     * @return the all notes
     */
    Collection getAllNotes();
    
    /**
     * Gets the marked notes.
     *
     * @return the marked notes
     */
    Collection getMarkedNotes();

    /**
     * Gets the notes for period.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @return the notes for period
     */
    Collection getNotesForPeriod(CalendarDate startDate, CalendarDate endDate);

    /**
     * Gets the note for date.
     *
     * @param date the date
     * @return the note for date
     */
    Note getNoteForDate(CalendarDate date);
    
    /**
     * Creates the note for date.
     *
     * @param date the date
     * @return the note
     */
    Note createNoteForDate(CalendarDate date);
    
/**
 * Removes the note.
 *
 * @param date the date
 * @param id the id
 */
//    void removeNoteForDate(CalendarDate date);
	void removeNote(CalendarDate date, String id);

    /**
     * Gets the active note.
     *
     * @return the active note
     */
    Note getActiveNote();
    
    /**
     * Gets the XML content.
     *
     * @return the XML content
     */
    nu.xom.Document getXMLContent();

}
