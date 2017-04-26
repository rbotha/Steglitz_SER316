/**
 * NoteImpl.java
 * Created on 13.02.2003, 15:36:55 Alex
 * Package: net.sf.memoranda
 * 
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package net.sf.memoranda;

import java.sql.Timestamp;

import net.sf.memoranda.date.CalendarDate;
import nu.xom.Attribute;
import nu.xom.Element;

// TODO: Auto-generated Javadoc
/**
 * The Class NoteImpl.
 */
/*$Id: NoteImpl.java,v 1.6 2004/10/06 19:15:44 ivanrise Exp $*/
public class NoteImpl implements Note, Comparable {
    
    /** The el. */
    private Element _el = null; 
    
    /** The project. */
    private Project _project;
    
    /**
     * Constructor for NoteImpl.
     *
     * @param el the el
     * @param project the project
     */
    public NoteImpl(Element el, Project project) {
        _el = el;
        _project = project;
    }

    /**
     * Gets the date.
     *
     * @return the date
     * @see net.sf.memoranda.Note#getDate()
     */
    public CalendarDate getDate() {
		Element day = (Element)_el.getParent();
		Element month = (Element)day.getParent();
		Element year = (Element)month.getParent();

     //   return new CalendarDate(day.getAttribute("date").getValue());
		
		return new CalendarDate(new Integer(day.getAttribute("day").getValue()).intValue(), 
								new Integer(month.getAttribute("month").getValue()).intValue(),
								new Integer(year.getAttribute("year").getValue()).intValue());

    }
    
    /* (non-Javadoc)
     * @see net.sf.memoranda.Note#getProject()
     */
    public Project getProject() {
        return _project;
    }
    
    /**
     * Gets the title.
     *
     * @return the title
     * @see net.sf.memoranda.Note#getTitle()
     */
    public String getTitle() {
        Attribute ta = _el.getAttribute("title");
        if (ta == null) return "";
        return _el.getAttribute("title").getValue();
    }
    
    /**
     * Sets the title.
     *
     * @param s the new title
     * @see net.sf.memoranda.Note#setTitle(java.lang.String)
     */
    public void setTitle(String s) {
        Attribute ta = _el.getAttribute("title");
        if (ta == null) _el.addAttribute(new Attribute("title", s));
        else 
            ta.setValue(s);
    }
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 * @see net.sf.memoranda.Note#getId
	 */
	
	public String getId() {
		Attribute id = _el.getAttribute("refid");
		if (id==null) return "";
		return _el.getAttribute("refid").getValue();
	}
	
	/**
	 * Sets the id.
	 *
	 * @param s the new id
	 * @see net.sf.memoranda.Note#setId(java.lang.String)
	 */
	 
	public void setId(String s) {
		Attribute id = _el.getAttribute("refid");
		if(id==null) _el.addAttribute(new Attribute("refid", s));
	}
    
    /**
     * Checks if is marked.
     *
     * @return true, if is marked
     * @see net.sf.memoranda.Note#isMarked()
     */
    public boolean isMarked() {
        return _el.getAttribute("bookmark") != null;        
    }
    
    /**
     * Sets the mark.
     *
     * @param mark the new mark
     * @see net.sf.memoranda.Note#setMark(boolean)
     */
    public void setMark(boolean mark) {
        Attribute ma = _el.getAttribute("bookmark");        
        if (ma == null) {
            if (mark)
                _el.addAttribute(new Attribute("bookmark", "yes"));
            return;
        }
        else if (!mark)
            _el.removeAttribute(ma);
    }
	
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	/*
	 * Comparable interface
	 */
	public int compareTo(Object o) {
		Note note = (Note) o;
		if(getDate().getDate().getTime() > note.getDate().getDate().getTime())
			return 1;
		else if(getDate().getDate().getTime() < note.getDate().getDate().getTime())
			return -1;
		else 
			return 0;
	}

	/* (non-Javadoc)
	 * @see net.sf.memoranda.Note#getTimeStamp()
	 */
	@Override
	public Timestamp getEdit() {
		//Get date notes were edited
		Attribute ts = _el.getAttribute("edit");
		if (ts == null)//If timestamp is null/empty
			return null;
		return Timestamp.valueOf(_el.getAttribute("edit").getValue());
	}

	/* (non-Javadoc)
	 * @see net.sf.memoranda.Note#setTimeStamp(java.sql.Timestamp)
	 */
	@Override
	public void setEdit(Timestamp s) {
		// Set date notes were edited
		Attribute ts = _el.getAttribute("edit");
        if (ts == null) _el.addAttribute(new Attribute("edit", s.toString()));
        else 
            ts.setValue(s.toString());
	}
    
}
