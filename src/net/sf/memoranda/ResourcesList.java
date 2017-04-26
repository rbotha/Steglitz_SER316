/**
 * ResourcesList.java
 * Created on 24.03.2003, 18:25:59 Alex
 * Package: net.sf.memoranda
 * 
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package net.sf.memoranda;

import java.util.Vector;

import nu.xom.Document;
// TODO: Auto-generated Javadoc

/**
 * The Interface ResourcesList.
 */
/*$Id: ResourcesList.java,v 1.4 2007/03/20 06:21:46 alexeya Exp $*/
public interface ResourcesList {
    
    /**
     * Gets the all resources.
     *
     * @return the all resources
     */
    Vector getAllResources();
    
    //Vector getResourcesForTask(String taskId);
    
    /**
     * Gets the resource.
     *
     * @param path the path
     * @return the resource
     */
    Resource getResource(String path);
    
    /**
     * Adds the resource.
     *
     * @param path the path
     * @param isInternetShortcut the is internet shortcut
     * @param isProjectFile the is project file
     */
    void addResource(String path, boolean isInternetShortcut, boolean isProjectFile);
    
    /**
     * Adds the resource.
     *
     * @param path the path
     */
    void addResource(String path);
    
    //void addResource(String path, String taskId);
    
    /**
     * Removes the resource.
     *
     * @param path the path
     */
    void removeResource(String path);
        
    /**
     * Gets the all resources count.
     *
     * @return the all resources count
     */
    int getAllResourcesCount();
    
    /**
     * Gets the XML content.
     *
     * @return the XML content
     */
    Document getXMLContent();

}
