package net.sf.memoranda.ui;

import javax.swing.JOptionPane;

import net.sf.memoranda.util.Local;

// TODO: Auto-generated Javadoc
/**
 * The Class ImportSticker.
 */
public class ImportSticker {

/** The name. */
String name;        
        
        /**
         * Instantiates a new import sticker.
         *
         * @param x the x
         */
        public ImportSticker(String x) {
                name = x;
        }

        /**
         * Import file.
         *
         * @return true, if successful
         */
        public boolean import_file(){
                /*
                 We are working on this =)
                  
                  
                  */
                
                JOptionPane.showMessageDialog(null,Local.getString("We still cannot import your document"));
                return true;
        }
        
        
}
