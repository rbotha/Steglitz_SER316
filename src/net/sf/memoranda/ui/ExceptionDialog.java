package net.sf.memoranda.ui;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import net.sf.memoranda.util.*;

// TODO: Auto-generated Javadoc
/**
 * The Class ExceptionDialog.
 */
/*$Id: ExceptionDialog.java,v 1.2 2004/10/18 19:09:10 ivanrise Exp $*/
public class ExceptionDialog extends JDialog {
  
  /** The panel 1. */
  JPanel panel1 = new JPanel();
  
  /** The border layout 1. */
  BorderLayout borderLayout1 = new BorderLayout();
  
  /** The j panel 1. */
  private JPanel jPanel1 = new JPanel();
  
  /** The j label 1. */
  private JLabel jLabel1 = new JLabel();
  
  /** The j panel 2. */
  private JPanel jPanel2 = new JPanel();
  
  /** The j label 2. */
  private JLabel jLabel2 = new JLabel();
  
  /** The border layout 2. */
  private BorderLayout borderLayout2 = new BorderLayout();
  
  /** The border layout 3. */
  private BorderLayout borderLayout3 = new BorderLayout();
  
  /** The desc label. */
  private JLabel descLabel = new JLabel();

  /** The description. */
  private String description;
  
  /** The tip. */
  private String tip;
  
  /** The trace. */
  private String trace;
  
  /** The j panel 3. */
  private JPanel jPanel3 = new JPanel();
  
  /** The j scroll pane 1. */
  private JScrollPane jScrollPane1 = new JScrollPane();
  
  /** The trace text area. */
  private JTextArea traceTextArea = new JTextArea();
  
  /** The report B. */
  private JButton reportB = new JButton();
  
  /** The close B. */
  private JButton closeB = new JButton();
  
  /** The flow layout 1. */
  private FlowLayout flowLayout1 = new FlowLayout();
  
  /** The j panel 4. */
  private JPanel jPanel4 = new JPanel();
  
  /** The copy B. */
  private JButton copyB = new JButton();
  
  /** The border layout 4. */
  private BorderLayout borderLayout4 = new BorderLayout();
  
  /** The owner. */
  private Frame owner; 
    
  /**
   * Instantiates a new exception dialog.
   *
   * @param exc the exc
   * @param description the description
   * @param tip the tip
   */
  public ExceptionDialog(Exception exc, String description, String tip) {
    super(App.getFrame(), "Problem", true);
    exc.printStackTrace();
    owner = App.getFrame();
    if ((description != null) && (description.length() > 0))        
        this.description = description;
    else if (exc.getMessage() != null)
        this.description = exc.getMessage();
    else
        this.description = "Unknown error";
    this.tip = tip;
    StringWriter sw = new StringWriter();
    exc.printStackTrace(new PrintWriter(sw));
    this.trace = sw.toString();
    try {
      jbInit();      
      setVisible(true);
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }
  
  /**
   * Instantiates a new exception dialog.
   *
   * @param exc the exc
   */
  public ExceptionDialog(Exception exc) {
      this(exc, "", "");
  }

  /**
   * Instantiates a new exception dialog.
   */
  public ExceptionDialog() {
    this(null, "", "");
  }

  /**
   * Jb init.
   *
   * @throws Exception the exception
   */
  private void jbInit() throws Exception {    
    panel1.setLayout(borderLayout1);    
    jPanel1.setBackground(Color.white);
    jPanel1.setLayout(borderLayout3);
    jLabel1.setFont(new java.awt.Font("Dialog", 1, 16));
    jLabel1.setHorizontalAlignment(SwingConstants.LEFT);
    jLabel1.setHorizontalTextPosition(SwingConstants.RIGHT);
    jLabel1.setText("Problem occured");
    jLabel1.setIcon(new ImageIcon(net.sf.memoranda.ui.ExceptionDialog.class.getResource(
            "resources/icons/error.png")));
        
    jLabel2.setFont(new java.awt.Font("Dialog", 0, 11));
    jLabel2.setText("<html>An internal exception occured. It is may be a result of bug in the " 
    + "program, corrupted data, incorrect configuration or hardware failure.<br><br>" 
    + "Click on <b>Report bug..</b> button to submit a bug to the Memoranda bugs tracker on SourceForge.net </html>");
    jPanel2.setLayout(borderLayout2);
    jPanel2.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
    borderLayout3.setVgap(5);
    String labelText = "<html><b>Description:</b><br>"+description;
    if ((tip != null) && (tip.length() > 0))
      labelText = labelText + "<br><br><b>Tip:</b><br>"+tip;
    labelText = labelText + "<br><br><b>Stack trace:</b></html>";
    descLabel.setText(labelText);
    descLabel.setFont(new java.awt.Font("Dialog", 0, 12));
    jScrollPane1.setEnabled(false);
    reportB.setMaximumSize(new Dimension(120, 25));
    reportB.setMinimumSize(new Dimension(120, 25));
    reportB.setPreferredSize(new Dimension(120, 25));
    reportB.setText("Report bug...");
    reportB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        reportB_actionPerformed(e);
      }
    });
    closeB.setMaximumSize(new Dimension(120, 25));
    closeB.setMinimumSize(new Dimension(120, 25));
    closeB.setPreferredSize(new Dimension(120, 25));
    closeB.setText("Close");
    closeB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        closeB_actionPerformed(e);
      }
    });
    this.getRootPane().setDefaultButton(closeB);
    jPanel3.setLayout(flowLayout1);
    flowLayout1.setAlignment(FlowLayout.RIGHT);
    copyB.setText("Copy to clipboard");
    copyB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        copyB_actionPerformed(e);
      }
    });
    copyB.setHorizontalAlignment(SwingConstants.RIGHT);
    jPanel4.setLayout(borderLayout4);
    traceTextArea.setText(trace);
    traceTextArea.setEditable(false);
    borderLayout1.setVgap(5);   
    getContentPane().add(panel1);
    panel1.add(jPanel1, BorderLayout.NORTH);
    jPanel1.add(jLabel1,  BorderLayout.NORTH);
    jPanel1.add(jLabel2, BorderLayout.CENTER);
    panel1.add(jPanel2, BorderLayout.CENTER);
    jPanel2.add(descLabel, BorderLayout.NORTH);
    jPanel2.add(jScrollPane1, BorderLayout.CENTER);
    jPanel2.add(jPanel4,  BorderLayout.SOUTH);
    jPanel4.add(copyB,  BorderLayout.WEST);
    jScrollPane1.getViewport().add(traceTextArea, null);
    panel1.add(jPanel3,  BorderLayout.SOUTH);
    jPanel3.add(closeB, null);
    jPanel3.add(reportB, null);
    Dimension dlgSize = new Dimension(400, 500);
    this.setSize(dlgSize); 
    if (owner != null) {
        Dimension frmSize = owner.getSize();
        Point loc = owner.getLocation();
        this.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
    }
  }

  /**
   * Copy B action performed.
   *
   * @param e the e
   */
  void copyB_actionPerformed(ActionEvent e) {
    traceTextArea.selectAll();
    traceTextArea.copy();
    traceTextArea.setSelectionEnd(0);
  }

  /**
   * Close B action performed.
   *
   * @param e the e
   */
  void closeB_actionPerformed(ActionEvent e) {
    this.dispose();
  }

  /**
   * Report B action performed.
   *
   * @param e the e
   */
  void reportB_actionPerformed(ActionEvent e) {
      Util.runBrowser(App.BUGS_TRACKER_URL);
  }
}