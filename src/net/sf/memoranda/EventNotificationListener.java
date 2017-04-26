/**
 * EventNotificationListener.java
 * Created on 10.03.2003, 20:43:16 Alex
 * Package: net.sf.memoranda
 * 
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package net.sf.memoranda;

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving eventNotification events.
 * The class that is interested in processing a eventNotification
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addEventNotificationListener<code> method. When
 * the eventNotification event occurs, that object's appropriate
 * method is invoked.
 *
 * @see EventNotificationEvent
 */
/*$Id: EventNotificationListener.java,v 1.2 2004/01/30 12:17:41 alexeya Exp $*/
public interface EventNotificationListener {

    /**
     * Event is occured.
     *
     * @param ev the ev
     */
    void eventIsOccured(Event ev);
    
    /**
     * Events changed.
     */
    void eventsChanged();

}
