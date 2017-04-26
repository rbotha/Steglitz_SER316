package net.sf.memoranda;

import java.util.Collection;
import java.util.Vector;

// TODO: Auto-generated Javadoc
/**
 * The Class CurrentNote.
 */
public class CurrentNote {

	/** The current note. */
	private static Note currentNote = null;
    
    /** The note listeners. */
    private static Vector noteListeners = new Vector();

    /**
     * Gets the.
     *
     * @return the note
     */
    public static Note get() {
        return currentNote;
    }

    /**
     * Sets the.
     *
     * @param note the note
     * @param toSaveCurrentNote the to save current note
     */
    public static void set(Note note, boolean toSaveCurrentNote) {
        noteChanged(note, toSaveCurrentNote);
        currentNote = note;
    }

    /**
     * Reset.
     */
    public static void reset() {
//    	 set toSave to true to mimic status quo behaviour only. the appropriate setting could be false
        set(null, true);
    }

    /**
     * Adds the note listener.
     *
     * @param nl the nl
     */
    public static void addNoteListener(NoteListener nl) {
        noteListeners.add(nl);
    }

    /**
     * Gets the change listeners.
     *
     * @return the change listeners
     */
    public static Collection getChangeListeners() {
        return noteListeners;
    }

    /**
     * Note changed.
     *
     * @param note the note
     * @param toSaveCurrentNote the to save current note
     */
    private static void noteChanged(Note note, boolean toSaveCurrentNote) {
        for (int i = 0; i < noteListeners.size(); i++) {
            ((NoteListener)noteListeners.get(i)).noteChange(note,toSaveCurrentNote);
		}
    }
}
