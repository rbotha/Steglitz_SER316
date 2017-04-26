
package net.sf.memoranda.ui;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;
import net.sf.memoranda.util.Local;

// TODO: Auto-generated Javadoc
/**
 * The Class FileExportDialog.
 *
 * @author  Alex
 */
public class FileExportDialog extends javax.swing.JDialog {
    
    /** The cancelled. */
    public boolean CANCELLED = true;
    
    
    /**
     *  Creates new form ExportDialog.
     *
     * @param parent the parent
     * @param title the title
     * @param chooser the chooser
     */
    public FileExportDialog(java.awt.Frame parent, String title, JFileChooser chooser) {
        super(parent, title, true);
        fileChooser = chooser;
        initComponents();
    }
    
   
    /**
     * Inits the components.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
    	
    	 /**
        Method: Closes window when esc pressed
        Inputs: keyEvent
        Returns: Void

        Description:Although technically not a method, this block of code does the job.
      */
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
	            KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Cancel"); //$NON-NLS-1$
	        getRootPane().getActionMap().put("Cancel", new AbstractAction(){ //$NON-NLS-1$
	            public void actionPerformed(ActionEvent e)
	            {
	             dispose();
	            }
	        });
        jPanel2 = new javax.swing.JPanel();
        okB = new javax.swing.JButton();
        cancelB = new javax.swing.JButton();
        filePanel = new javax.swing.JPanel();
        //fileChooser = new javax.swing.JFileChooser();
        optionsPanel = new javax.swing.JPanel();
        encPanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        encCB = new JComboBox(new String[]{Local.getString("System default"), "UTF-8", "ANSI"});
        usetemplChB = new javax.swing.JCheckBox();
        xhtmlChB = new javax.swing.JCheckBox();
        templPanel = new javax.swing.JPanel();
        templF = new javax.swing.JTextField();
        templF.setEditable(false);
        templBrowseB = new javax.swing.JButton();
        numentChB = new javax.swing.JCheckBox();
        jPanel6 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setModal(true);
        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        okB.setText(Local.getString("Save"));
        okB.setPreferredSize(new java.awt.Dimension(90, 25));
        okB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CANCELLED = false;
                dispose();
            }
        });
        okB.setEnabled(false);
        jPanel2.add(okB);

        cancelB.setText(Local.getString("Cancel"));
        cancelB.setPreferredSize(new java.awt.Dimension(90, 25));
        cancelB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {                
                dispose();
            }
        });
        jPanel2.add(cancelB);

        getContentPane().add(jPanel2, java.awt.BorderLayout.SOUTH);

        filePanel.setLayout(new java.awt.BorderLayout());

        filePanel.setBorder(new javax.swing.border.EtchedBorder());
        fileChooser.setControlButtonsAreShown(false);
        fileChooser.addPropertyChangeListener(new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                chooserActionPerformed();
                
            }
        
        });
        /*fileChooser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chooserActionPerformed();
            }
        });*/
        
        

        filePanel.add(fileChooser, java.awt.BorderLayout.CENTER);

        optionsPanel.setLayout(new java.awt.GridLayout(3, 2, 5, 0));

        optionsPanel.setBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(5, 5, 5, 5)));
        encPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 0));

        jLabel2.setText(Local.getString("Encoding")+":");
        encPanel.add(jLabel2);

        encCB.setMaximumSize(new java.awt.Dimension(32767, 19));
        encPanel.add(encCB);

        optionsPanel.add(encPanel);

        usetemplChB.setText(Local.getString("Use template")+":");
        usetemplChB.setMargin(new java.awt.Insets(0, 0, 0, 0));
        usetemplChB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {                
                if (usetemplChB.isSelected()) {
                    templF.setEnabled(true);
                    templBrowseB.setEnabled(true);
                }
                else {
                    templF.setEnabled(false);
                    templBrowseB.setEnabled(false);                    
                }
            }
        });
        optionsPanel.add(usetemplChB);

        xhtmlChB.setText(Local.getString("Save as XHTML"));
        xhtmlChB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xhtmlChBActionPerformed(evt);
            }
        });

        optionsPanel.add(xhtmlChB);

        templPanel.setLayout(new java.awt.BorderLayout());
        templF.setEnabled(false);
        templPanel.add(templF, java.awt.BorderLayout.CENTER);

        templBrowseB.setText("Browse");
        templBrowseB.setEnabled(false);
        templBrowseB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
               browseTemplate();
            }
        });
        templPanel.add(templBrowseB, java.awt.BorderLayout.EAST);

        optionsPanel.add(templPanel);

        numentChB.setText("Use numeric entities");
        optionsPanel.add(numentChB);

        jPanel6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        optionsPanel.add(jPanel6);

        filePanel.add(optionsPanel, java.awt.BorderLayout.SOUTH);

        getContentPane().add(filePanel, java.awt.BorderLayout.CENTER);
        getRootPane().setDefaultButton(okB);
        pack();
        
       
    }//GEN-END:initComponents

    /**
     * Xhtml ch B action performed.
     *
     * @param evt the evt
     */
    private void xhtmlChBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xhtmlChBActionPerformed
        // TODO add your handling code here:
    }

    /**
     * Chooser action performed.
     */
    private void chooserActionPerformed() {//GEN-FIRST:event_chooserActionPerformed
        okB.setEnabled(fileChooser.getSelectedFile() != null);            
    }
    
    /**
     * Browse template.
     */
    private void browseTemplate() {
    	JFileChooser chooser = new JFileChooser();
        chooser.setFileHidingEnabled(false);
        chooser.setDialogTitle(Local.getString("Select file"));
        chooser.setAcceptAllFileFilterUsed(true);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if (templF.getText().length() >0)
        	chooser.setCurrentDirectory(new java.io.File(templF.getText()));
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
        	templF.setText(chooser.getSelectedFile().getPath());
    }
    
    /** The cancel B. */
    private javax.swing.JButton cancelB;
    
    /** The file chooser. */
    private javax.swing.JFileChooser fileChooser;
    
    /** The enc panel. */
    private javax.swing.JPanel encPanel;
    
    /** The file panel. */
    private javax.swing.JPanel filePanel;
    
    /** The j label 2. */
    private javax.swing.JLabel jLabel2;
    
    /** The j panel 2. */
    private javax.swing.JPanel jPanel2;
    
    /** The j panel 6. */
    private javax.swing.JPanel jPanel6;
    
    /** The nument ch B. */
    public javax.swing.JCheckBox numentChB;
    
    /** The ok B. */
    private javax.swing.JButton okB;
    
    /** The options panel. */
    private javax.swing.JPanel optionsPanel;
    
    /** The templ browse B. */
    private javax.swing.JButton templBrowseB;
    
    /** The templ F. */
    public javax.swing.JTextField templF;
    
    /** The templ panel. */
    private javax.swing.JPanel templPanel;
    
    /** The usetempl ch B. */
    public javax.swing.JCheckBox usetemplChB;
    
    /** The xhtml ch B. */
    public javax.swing.JCheckBox xhtmlChB;
    
    /** The enc CB. */
    public JComboBox encCB;
    // End of variables declaration//GEN-END:variables
    
}
