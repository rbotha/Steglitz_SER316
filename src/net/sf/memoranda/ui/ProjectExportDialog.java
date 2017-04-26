package net.sf.memoranda.ui;

import java.awt.event.ActionEvent;

import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicFileChooserUI;

import net.sf.memoranda.util.Local;
import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;

// TODO: Auto-generated Javadoc
/**
 * The Class ProjectExportDialog.
 */
/*$Id: ProjectExportDialog.java,v 1.3 2004/04/05 10:05:44 alexeya Exp $*/
public class ProjectExportDialog extends JDialog {

  /** The border layout 1. */
  BorderLayout borderLayout1 = new BorderLayout();
  
  /** The j panel 2. */
  JPanel jPanel2 = new JPanel();
  
  /** The file chooser. */
  public JFileChooser fileChooser = null;
  
  /** The border layout 3. */
  BorderLayout borderLayout3 = new BorderLayout();
  
  /** The j panel 3. */
  JPanel jPanel3 = new JPanel();
  
  /** The ok B. */
  JButton okB = new JButton();
  
  /** The cancel B. */
  JButton cancelB = new JButton();
  
  /** The flow layout 1. */
  FlowLayout flowLayout1 = new FlowLayout();

  /** The cancelled. */
  public boolean CANCELLED = true;
  
  /** The border 1. */
  Border border1;
  
  /** The border 2. */
  Border border2;
  
  /** The j panel 4. */
  JPanel jPanel4 = new JPanel();
  
  /** The j panel 1. */
  JPanel jPanel1 = new JPanel();
  
  /** The border layout 2. */
  BorderLayout borderLayout2 = new BorderLayout();
  
  /** The enc CB. */
  JComboBox encCB = new JComboBox(new String[]{Local.getString("System default"), "UTF-8", "ANSI"});
  
  /** The j label 1. */
  JLabel jLabel1 = new JLabel();
  
  /** The grid layout 1. */
  GridLayout gridLayout1 = new GridLayout();
  
  /** The split ch B. */
  JCheckBox splitChB = new JCheckBox();
  
  /** The titles as headers ch B. */
  JCheckBox titlesAsHeadersChB = new JCheckBox();

  /**
   * Instantiates a new project export dialog.
   *
   * @param frame the frame
   * @param title the title
   * @param chooser the chooser
   */
  public ProjectExportDialog(Frame frame, String title, JFileChooser chooser) {
    super(frame, title, true);
    try {
      fileChooser = chooser;
      jbInit();
      pack();
    }
    catch(Exception ex) {
      new ExceptionDialog(ex);
    }
  }


  /**
   * Jb init.
   *
   * @throws Exception the exception
   */
  void jbInit() throws Exception {
    this.setResizable(false);

    border1 = BorderFactory.createEmptyBorder(10,10,0,10);
    border2 = BorderFactory.createEmptyBorder(5,10,5,5);
    fileChooser.setBorder(null);
    fileChooser.setControlButtonsAreShown(false);
    jPanel2.setLayout(borderLayout3);
    okB.setMaximumSize(new Dimension(100, 26));
    okB.setPreferredSize(new Dimension(100, 26));
    okB.setText(Local.getString("Save"));
    okB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        okB_actionPerformed(e);
		if (fileChooser.getUI() instanceof BasicFileChooserUI) //Added to fix problem with export note
		//jcscoobyrs 17-Nov-2003 at 08:36:14 AM
		{//Added to fix problem with export note jcscoobyrs 17-Nov-2003 at 08:36:14 AM
			BasicFileChooserUI ui = (BasicFileChooserUI)fileChooser.getUI();//Added to fix problem with export note
			//jcscoobyrs 17-Nov-2003 at 08:36:14 AM
			ui.getApproveSelectionAction().actionPerformed(e);//Added to fix problem with export note
			//jcscoobyrs 17-Nov-2003 at 08:36:14 AM
		}//Added to fix problem with export note jcscoobyrs 17-Nov-2003 at 08:36:14 AM
      }
    });
    this.getRootPane().setDefaultButton(okB);
    cancelB.setMaximumSize(new Dimension(100, 26));
    cancelB.setPreferredSize(new Dimension(100, 26));
    cancelB.setText(Local.getString("Cancel"));
    cancelB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cancelB_actionPerformed(e);
      }
    });
    jPanel3.setLayout(flowLayout1);
    flowLayout1.setAlignment(FlowLayout.RIGHT);
    borderLayout3.setHgap(5);
    borderLayout3.setVgap(5);
    jPanel2.setBorder(border1);
    jPanel3.setBorder(border2);
    jPanel1.setLayout(borderLayout2);
    jLabel1.setMaximumSize(new Dimension(155, 16));
    jLabel1.setPreferredSize(new Dimension(80, 16));
    jLabel1.setText(Local.getString("Encoding")+":");
    jPanel4.setLayout(gridLayout1);
    splitChB.setText(Local.getString("Split notes into separate files"));
    gridLayout1.setColumns(1);
    gridLayout1.setRows(3);
    titlesAsHeadersChB.setText(Local.getString("Notes titles as headers"));
    this.getContentPane().add(jPanel2,  BorderLayout.CENTER);
    jPanel2.add(jPanel4,  BorderLayout.SOUTH);
    jPanel4.add(jPanel1, null);
    jPanel1.add(encCB, BorderLayout.CENTER);
    jPanel1.add(jLabel1, BorderLayout.WEST);
    jPanel4.add(splitChB, null);
    jPanel2.add(fileChooser, BorderLayout.NORTH);
    this.getContentPane().add(jPanel3,  BorderLayout.SOUTH);
    jPanel3.add(okB, null);
    jPanel3.add(cancelB, null);
    jPanel4.add(titlesAsHeadersChB, null);
    
    getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
            KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Cancel"); //$NON-NLS-1$
        getRootPane().getActionMap().put("Cancel", new AbstractAction(){ //$NON-NLS-1$
            public void actionPerformed(ActionEvent e)
            {
              dispose();
            }
        });
    
   
  }

   /**
    * Cancel B action performed.
    *
    * @param e the e
    */
   void cancelB_actionPerformed(ActionEvent e) {
        this.dispose();
    }

    /**
     * Ok B action performed.
     *
     * @param e the e
     */
    void okB_actionPerformed(ActionEvent e) {
        CANCELLED = false;
        this.dispose();
    }
}