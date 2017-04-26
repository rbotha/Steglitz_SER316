package net.sf.memoranda;

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving project events.
 * The class that is interested in processing a project
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addProjectListener<code> method. When
 * the project event occurs, that object's appropriate
 * method is invoked.
 *
 * @see ProjectEvent
 */
/*$Id: ProjectListener.java,v 1.3 2004/01/30 12:17:41 alexeya Exp $*/
public interface ProjectListener {
  
  /**
   * Project change.
   *
   * @param prj the prj
   * @param nl the nl
   * @param tl the tl
   * @param rl the rl
   */
  void projectChange(Project prj, NoteList nl, TaskList tl, ResourcesList rl);
  
  /**
   * Project was changed.
   */
  void projectWasChanged();
}