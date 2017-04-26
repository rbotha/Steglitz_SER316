package net.sf.memoranda.date;

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving date events.
 * The class that is interested in processing a date
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addDateListener<code> method. When
 * the date event occurs, that object's appropriate
 * method is invoked.
 *
 * @see DateEvent
 */
/*$Id: DateListener.java,v 1.2 2004/01/30 12:17:41 alexeya Exp $*/
public interface DateListener {

  /**
   * Date change.
   *
   * @param date the date
   */
  void dateChange(CalendarDate date);

}