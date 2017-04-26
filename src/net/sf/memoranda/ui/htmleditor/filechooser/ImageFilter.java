package net.sf.memoranda.ui.htmleditor.filechooser;

import java.io.File;

import javax.swing.filechooser.FileFilter;

// TODO: Auto-generated Javadoc
/**
 * The Class ImageFilter.
 */
public class ImageFilter extends FileFilter {

    /* (non-Javadoc)
     * @see javax.swing.filechooser.FileFilter#accept(java.io.File)
     */
    // Accept all directories and all gif, jpg, or tiff files.
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

        String extension = Utils.getExtension(f);
	if (extension != null) {
            if (extension.equals(Utils.png) 
            	|| extension.equals(Utils.gif) 
                || extension.equals(Utils.jpeg) 
                || extension.equals(Utils.jpg)) {
                    return true;
            } else {
                return false;
            }
    	}

        return false;
    }

    /* (non-Javadoc)
     * @see javax.swing.filechooser.FileFilter#getDescription()
     */
    // The description of this filter
    public String getDescription() {
        return "Images (GIF, JPEG, PNG)";
    }
}
