package net.sf.memoranda.ui.htmleditor.filechooser;

import java.io.File;

// TODO: Auto-generated Javadoc
/**
 * The Class Utils.
 */
public class Utils {

    /** The Constant jpeg. */
    public final static String jpeg = "jpeg";
    
    /** The Constant jpg. */
    public final static String jpg = "jpg";
    
    /** The Constant gif. */
    public final static String gif = "gif";
    
    /** The Constant png. */
    public final static String png = "png";

    /**
     * Gets the extension.
     *
     * @param f the f
     * @return the extension
     */
    /*
     * Get the extension of a file.
     */
    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }

}
