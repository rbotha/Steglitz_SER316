/**
 * ProjectManager.java
 * Created on 11.02.2003, 17:50:27 Alex
 * Package: net.sf.memoranda
 *
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package net.sf.memoranda;

import java.util.Vector;

import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.util.CurrentStorage;
import net.sf.memoranda.util.Local;
import net.sf.memoranda.util.Util;
import nu.xom.Attribute;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;

// TODO: Auto-generated Javadoc
/**
 * The Class ProjectManager.
 */
/*$Id: ProjectManager.java,v 1.9 2005/12/01 08:12:26 alexeya Exp $*/
public class ProjectManager {
//    public static final String NS_JNPROJECT = "http://www.openmechanics.org/2003/jnotes-projects-file";

    /** The doc. */
public static Document _doc = null;
    
    /** The root. */
    static Element _root = null;
    
    static {
    	init();
    }

    /**
     * Inits the.
     */
    public static void init() {
        CurrentStorage.get().openProjectManager();
        if (_doc == null) {
            _root = new Element("projects-list");
//            _root.addNamespaceDeclaration("jnotes", NS_JNPROJECT);
//            _root.appendChild(new Comment("This is JNotes 2 data file. Do not modify."));
            _doc = new Document(_root);
            createProject("__default", Local.getString("Default project"), CalendarDate.today(), null);
        }
        else
            _root = _doc.getRootElement();
    }

    /**
     * Gets the project.
     *
     * @param id the id
     * @return the project
     */
    public static Project getProject(String id) {
        Elements prjs = _root.getChildElements("project");
        for (int i = 0; i < prjs.size(); i++) {
            String pid = ((Element) prjs.get(i)).getAttribute("id").getValue();
            if (pid.equals(id)) {
                return new ProjectImpl((Element) prjs.get(i));
            }
        }
        return null;
    }

    /**
     * Gets the all projects.
     *
     * @return the all projects
     */
    public static Vector getAllProjects() {
        Elements prjs = _root.getChildElements("project");
        Vector v = new Vector();
        for (int i = 0; i < prjs.size(); i++)
            v.add(new ProjectImpl((Element) prjs.get(i)));
        return v;
    }

    /**
     * Gets the all projects number.
     *
     * @return the all projects number
     */
    public static int getAllProjectsNumber() {
		int i;
        try {
			i = ((Elements)_root.getChildElements("project")).size();
		}
		catch (NullPointerException e) {
			i = 1;
		}
		return i;
    }

    /**
     * Gets the active projects.
     *
     * @return the active projects
     */
    public static Vector getActiveProjects() {
        Elements prjs = _root.getChildElements("project");
        Vector v = new Vector();
        for (int i = 0; i < prjs.size(); i++) {
            Project prj = new ProjectImpl((Element) prjs.get(i));
            if (prj.getStatus() == Project.ACTIVE)
                v.add(prj);
        }
        return v;
    }
		
    /**
     * Gets the active projects number.
     *
     * @return the active projects number
     */
    public static int getActiveProjectsNumber() {
        Elements prjs = _root.getChildElements("project");
        int count = 0;
        for (int i = 0; i < prjs.size(); i++) {
            Project prj = new ProjectImpl((Element) prjs.get(i));
            if (prj.getStatus() == Project.ACTIVE)
                count++;
        }
        return count;
    }

    /**
     * Creates the project.
     *
     * @param id the id
     * @param title the title
     * @param startDate the start date
     * @param endDate the end date
     * @return the project
     */
    public static Project createProject(String id, String title, CalendarDate startDate, CalendarDate endDate) {
        Element el = new Element("project");
        el.addAttribute(new Attribute("id", id));
        _root.appendChild(el);
        Project prj = new ProjectImpl(el);
        prj.setTitle(title);
        prj.setStartDate(startDate);
        prj.setEndDate(endDate);
        CurrentStorage.get().createProjectStorage(prj);
        return prj;
    }

    /**
     * Creates the project.
     *
     * @param title the title
     * @param startDate the start date
     * @param endDate the end date
     * @return the project
     */
    public static Project createProject(String title, CalendarDate startDate, CalendarDate endDate) {
        return createProject(Util.generateId(), title, startDate, endDate);
    }
    
    /**
     * Removes the project.
     *
     * @param id the id
     */
    public static void removeProject(String id) {
        Project prj = getProject(id);
        if (prj == null)
            return;
        History.removeProjectHistory(prj);
        CurrentStorage.get().removeProjectStorage(prj);
        Elements prjs = _root.getChildElements("project");
        for (int i = 0; i < prjs.size(); i++) {
            String pid = ((Element) prjs.get(i)).getAttribute("id").getValue();
            if (pid.equals(id)) {
                _root.removeChild(prjs.get(i));
                return;
            }
        }
    }

}
