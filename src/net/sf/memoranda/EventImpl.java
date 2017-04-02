/**
 * EventImpl.java
 * Created on 08.03.2003, 13:20:13 Alex
 * Package: net.sf.memoranda
 *
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package net.sf.memoranda;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.util.Local;
import nu.xom.Attribute;
import nu.xom.Element;

/**
 *
 */
/*$Id: EventImpl.java,v 1.9 2004/10/06 16:00:11 ivanrise Exp $*/
public class EventImpl implements Event, Comparable {

    private Element _elem = null;

    /**
     * Constructor for EventImpl.
     */
    public EventImpl(Element elem) {
        _elem = elem;
    }


    /**
     * @see net.sf.memoranda.Event#getHour()
     */
    public int getHour() {
        return new Integer(_elem.getAttribute("hour").getValue()).intValue();
    }

    /**
     * @see net.sf.memoranda.Event#getMinute()
     */
    public int getMinute() {
        return new Integer(_elem.getAttribute("min").getValue()).intValue();
    }

    public String getTimeString() {
        return Local.getTimeString(getHour(), getMinute());
    }


    /**
     * @see net.sf.memoranda.Event#getText()
     */
    public String getText() {
        return _elem.getValue();
    }

    /**
     * Description: This method is a getter for the email.
     * @return the email or null if there is no email
     */
    public String getEmail() {
        try {
            return new String(_elem.getAttribute("email").getValue().toString());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Description: This method is a getter for note.
     * @return The note added to the
     */
    @Override
	public String getNote() {
		return _elem.getAttribute("note").getValue().toString();
	}

    /**
     * @see net.sf.memoranda.Event#getContent()
     */
    public Element getContent() {
        return _elem;
    }
    /**
     * @see net.sf.memoranda.Event#isRepeatable()
     */
    public boolean isRepeatable() {
        return getStartDate() != null;
    }
    /**
     * @see net.sf.memoranda.Event#getStartDate()
     */
    public CalendarDate getStartDate() {
        Attribute a = _elem.getAttribute("startDate");
        if (a != null) return new CalendarDate(a.getValue());
        return null;
    }
    /**
     * @see net.sf.memoranda.Event#getEndDate()
     */
    public CalendarDate getEndDate() {
        Attribute a = _elem.getAttribute("endDate");
        if (a != null) return new CalendarDate(a.getValue());
        return null;
    }
    /**
     * @see net.sf.memoranda.Event#getPeriod()
     */
    public int getPeriod() {
        Attribute a = _elem.getAttribute("period");
        if (a != null) return new Integer(a.getValue()).intValue();
        return 0;
    }
    /**
     * @see net.sf.memoranda.Event#getId()
     */
    public String getId() {
        Attribute a = _elem.getAttribute("id");
        if (a != null) return a.getValue();
        return null;
    }
    /**
     * @see net.sf.memoranda.Event#getRepeat()
     */
    public int getRepeat() {
        Attribute a = _elem.getAttribute("repeat-type");
        if (a != null) return new Integer(a.getValue()).intValue();
        return 0;
    }
    /**
     * @see net.sf.memoranda.Event#getTime()
     */
    public Date getTime() {
    	//Deprecated methods
		//Date d = new Date();
		//d.setHours(getHour());
		//d.setMinutes(getMinute());
		//d.setSeconds(0);
		//End deprecated methods

		Date d = new Date(); //Revision to fix deprecated methods (jcscoobyrs) 12-NOV-2003 14:26:00
		Calendar calendar = new GregorianCalendar(Local.getCurrentLocale()); //Revision to fix deprecated methods (jcscoobyrs) 12-NOV-2003 14:26:00
		calendar.setTime(d); //Revision to fix deprecated methods (jcscoobyrs) 12-NOV-2003 14:26:00
		calendar.set(Calendar.HOUR_OF_DAY, getHour()); //Revision to fix deprecated methods (jcscoobyrs) 12-NOV-2003 14:26:00
		calendar.set(Calendar.MINUTE, getMinute()); //Revision to fix deprecated methods (jcscoobyrs) 12-NOV-2003 14:26:00
		calendar.set(Calendar.SECOND, 0); //Revision to fix deprecated methods (jcscoobyrs) 12-NOV-2003 14:26:00
		d = calendar.getTime(); //Revision to fix deprecated methods (jcscoobyrs) 12-NOV-2003 14:26:00
        return d;
    }

	/**
     * @see net.sf.memoranda.Event#getWorkinDays()
     */
	public boolean getWorkingDays() {
        Attribute a = _elem.getAttribute("workingDays");
        if (a != null && a.getValue().equals("true")) return true;
        return false;
	}

	public int compareTo(Object o) {
		Event event = (Event) o;
		return (getHour() * 60 + getMinute()) - (event.getHour() * 60 + event.getMinute());
	}

    private void setAttr(String a, String value) {
        Attribute attr = _elem.getAttribute(a);
        if (attr == null)
           _elem.addAttribute(new Attribute(a, value));
        else
            attr.setValue(value);
    }

    /**
     * This sends an email about the event to the email address
     * @return false for failure, true for success
     */
    public boolean sendEmail() {
        String destEmail = getEmail();

        //System.out.println("SEND EMAIL HAS BEEN CALLED: currently deactivated");
        //destEmail = null;

        if (destEmail != null) {
            String title, message;
            message = "";

            title = "Reminder for "+getText();
            if (getStartDate() != null) {
                title += " on "+getStartDate().toString();
            }
            if (getTime() != null) {
                message += "This event starts at "+getTime().toString();
            }
            if (getEndDate() != null) {
                message += " and ends on "+getEndDate().toString();
            }
            if (getId() != null) {
                message += ".\n\nHere is the event id:\n"+getId();
            } else {
                message += ".\n\n";
            }

            try {
              GoogleMail.Send("dummyser316", "dummypassword", destEmail, title, message);

              return true;
            } catch (Exception e) {
              e.printStackTrace();
            }
        }

        return false;
    }
}
