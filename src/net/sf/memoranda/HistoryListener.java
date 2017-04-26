/**
 * HistoryListener.java
 * Created on 23.02.2003, 1:56:52 Alex
 * Package: net.sf.memoranda
 * 
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package net.sf.memoranda;

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving history events.
 * The class that is interested in processing a history
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addHistoryListener<code> method. When
 * the history event occurs, that object's appropriate
 * method is invoked.
 *
 * @see HistoryEvent
 */
/*$Id: HistoryListener.java,v 1.2 2004/01/30 12:17:41 alexeya Exp $*/
public interface HistoryListener {

    /*void historyWasRolledBack();
    
    void historyWasRolledForward();*/
    
    /**
     * History was rolled to.
     *
     * @param item the item
     */
    void historyWasRolledTo(HistoryItem item);

}
