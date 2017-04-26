package net.sf.memoranda;

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving note events.
 * The class that is interested in processing a note
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addNoteListener<code> method. When
 * the note event occurs, that object's appropriate
 * method is invoked.
 *
 * @see NoteEvent
 */
public interface NoteListener {
  
  /**
   * Note change.
   *
   * @param note the note
   * @param toSaveCurrentNote the to save current note
   */
  void noteChange(Note note, boolean toSaveCurrentNote);
}
