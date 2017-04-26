/**
 * Resource.java
 * Created on 04.04.2003, 20:59:24 Alex
 * Package: net.sf.memoranda
 *  
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package net.sf.memoranda;

// TODO: Auto-generated Javadoc
/**
 * The Class Resource.
 */
/*$Id: Resource.java,v 1.4 2007/03/20 06:21:46 alexeya Exp $*/
public class Resource {
    
    /** The path. */
    private String _path = null;	// the path to the file
    
    /** The is inet shortcut. */
    private boolean _isInetShortcut = false; // true if Internet shortcut
    
    /** The is project file. */
    private boolean _isProjectFile = false;	// true if file is in project directory 
    
    /**
     * Constructor for Resource.
<<<<<<< HEAD
     *
     * @param path the path
     * @param isInetShortcut the is inet shortcut
     * @param isProjectFile the is project file
=======
     * @param path **The path to the file.**
     * @param isInetShortcut **If the resource is a internet shortcut.**
     * @param isProjectFile **If file is copied to project directory.**
>>>>>>> origin/US-106
     */
    public Resource(String path, boolean isInetShortcut, boolean isProjectFile) {
        _path = path;
        _isInetShortcut = isInetShortcut;
        _isProjectFile = isProjectFile;
    }
    
    /**
     * Instantiates a new resource.
     *
     * @param path the path
     */
    public Resource(String path) {
        _path = path;         
    }
    
    /**
     * Gets the path.
     *
     * @return the path
     */
    public String getPath() {
        return _path;
    }
    
    /**
     * Checks if is inet shortcut.
     *
     * @return true, if is inet shortcut
     */
    public boolean isInetShortcut() {
        return _isInetShortcut;
    }
    
    /**
     * Checks if is project file.
     *
     * @return true, if is project file
     */
    public boolean isProjectFile() {
    	return _isProjectFile;
    }

}
