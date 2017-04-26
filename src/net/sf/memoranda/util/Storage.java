/**
 * Storage.java
 * Created on 12.02.2003, 0:58:42 Alex
 * Package: net.sf.memoranda.util
 * 
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package net.sf.memoranda.util;

import net.sf.memoranda.Note;
import net.sf.memoranda.NoteList;
import net.sf.memoranda.Project;
import net.sf.memoranda.ResourcesList;
import net.sf.memoranda.TaskList;
// TODO: Auto-generated Javadoc

/**
 * The Interface Storage.
 */
/*$Id: Storage.java,v 1.4 2004/01/30 12:17:42 alexeya Exp $*/
public interface Storage {
            
    /**
     * Open task list.
     *
     * @param prj the prj
     * @return the task list
     */
    TaskList openTaskList(Project prj);    
    
    /**
     * Store task list.
     *
     * @param tl the tl
     * @param prj the prj
     */
    void storeTaskList(TaskList tl, Project prj);
    
    /**
     * Open note list.
     *
     * @param prj the prj
     * @return the note list
     */
    NoteList openNoteList(Project prj);
    
    /**
     * Store note list.
     *
     * @param nl the nl
     * @param prj the prj
     */
    void storeNoteList(NoteList nl, Project prj);
    
    /**
     * Store note.
     *
     * @param note the note
     * @param doc the doc
     */
    void storeNote(Note note, javax.swing.text.Document doc);    
    
    /**
     * Open note.
     *
     * @param note the note
     * @return the javax.swing.text. document
     */
    javax.swing.text.Document openNote(Note note);
    
    /**
     * Removes the note.
     *
     * @param note the note
     */
    void removeNote(Note note);
    
    /**
     * Gets the note URL.
     *
     * @param note the note
     * @return the note URL
     */
    String getNoteURL(Note note);
    
    /**
     * Open project manager.
     */
    void openProjectManager();    
    
    /**
     * Store project manager.
     */
    void storeProjectManager();
    
    /**
     * Open events manager.
     */
    void openEventsManager();
    
    /**
     * Store events manager.
     */
    void storeEventsManager();
    
    /**
     * Open mime types list.
     */
    void openMimeTypesList();
    
    /**
     * Store mime types list.
     */
    void storeMimeTypesList();
    
    /**
     * Creates the project storage.
     *
     * @param prj the prj
     */
    void createProjectStorage(Project prj);
    
    /**
     * Removes the project storage.
     *
     * @param prj the prj
     */
    void removeProjectStorage(Project prj);
   
    /**
     * Open resources list.
     *
     * @param prj the prj
     * @return the resources list
     */
    ResourcesList openResourcesList(Project prj);
    
    /**
     * Store resources list.
     *
     * @param rl the rl
     * @param prj the prj
     */
    void storeResourcesList(ResourcesList rl, Project prj);
    
    /**
     * Restore context.
     */
    void restoreContext();
    
    /**
     * Store context.
     */
    void storeContext(); 
       
}
