package net.sf.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JInternalFrame;
import javax.swing.border.Border;

import net.sf.memoranda.util.Local;

// TODO: Auto-generated Javadoc
/**
 * The Class CalendarFrame.
 */
/*$Id: CalendarFrame.java,v 1.5 2004/04/05 10:05:44 alexeya Exp $*/
public class CalendarFrame extends JInternalFrame {

  /** The cal. */
  public JNCalendarPanel cal = new JNCalendarPanel();
  
  /** The border 1. */
  Border border1;

  /**
   * Instantiates a new calendar frame.
   */
  public CalendarFrame() {
    try {
      jbInit();
    }
    catch(Exception e) {
      new ExceptionDialog(e);
    }

  }
  
  /**
   * Jb init.
   *
   * @throws Exception the exception
   */
  private void jbInit() throws Exception {
    border1 = BorderFactory.createLineBorder(Color.gray,1);
    this.setClosable(true);
    this.setTitle(Local.getString("Select date"));
    this.setBorder(border1);
    //this.setPreferredSize(new Dimension(200, 200));
    this.setToolTipText("");
    cal.setPreferredSize(new Dimension(this.getContentPane().getWidth(),
    this.getContentPane().getHeight()));
    this.getContentPane().add(cal,  BorderLayout.CENTER);
  }
}