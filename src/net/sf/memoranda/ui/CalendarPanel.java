/**
 * 
 */
package net.sf.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import net.sf.memoranda.date.CurrentDate;
import net.sf.memoranda.util.Local;
import net.sf.memoranda.util.Util;

/**
 * Creates a calendar panel.
 * Apr 19, 2017
 * @author Jesus Rodriguez Jr
 *
 */
@SuppressWarnings("serial")
public class CalendarPanel extends JPanel implements ComponentListener {
	 public JNCalendarPanel cal = new JNCalendarPanel();
	 private WorkPanel parentPanel = null;
	  Border border1;
	  BorderLayout borderLayout;

	  /**
	   * Default Constructor
	   *
	   */
	  public CalendarPanel() {
	    try {
	      jbInit();
	    }catch(Exception e) {
	    	Util.debug(e.getMessage());
	    }

	  }
	  
	  /**
	   * Method for getting parent
	   * @param parentPanel  parents panel for WorkPanel
	   * 					Gives access to home calendar.
	   */
	  public void setParent(WorkPanel parentPanel) {
		  this.parentPanel = parentPanel;
		  cal.setParentPanel(this.parentPanel);
	  }
	  //Private method for creating panel
	  private void jbInit() throws Exception {
	    border1 = BorderFactory.createLineBorder(Color.gray,1);
	    borderLayout = new BorderLayout();
	    this.setBorder(border1);
	    this.setLayout(borderLayout);
	    this.setToolTipText("");
	    this.add(cal, BorderLayout.CENTER);
	    
	    cal.addComponentListener(this);
	    cal.addSelectionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	CurrentDate.set(cal.get());
            	cal.set(CurrentDate.get());
            	parentPanel.dailyItemsPanel.currentDateChanged(CurrentDate.get());
            }
        });
	  }
	  
	  //private method for refreshing calendar size and font size
	  private void refresh() {
		  int height = parentPanel.getBounds().height - 97;
		  int tableSize = height / 6;
		  int font = (height / 39) + 15;

		  cal.jnCalendar.setRowHeight(tableSize);
		  cal.jnCalendar.setFont(new java.awt.Font("Dialog", 0, font));
	  }
	  
	/* (non-Javadoc)
	 * @see java.awt.event.ComponentListener#componentResized(java.awt.event.ComponentEvent)
	 */
	@Override
	public void componentResized(ComponentEvent e) {
		// TODO Auto-generated method stub
		refresh();
		Util.debug("Calendar Resized.");
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ComponentListener#componentMoved(java.awt.event.ComponentEvent)
	 */
	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ComponentListener#componentShown(java.awt.event.ComponentEvent)
	 */
	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ComponentListener#componentHidden(java.awt.event.ComponentEvent)
	 */
	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}
}
