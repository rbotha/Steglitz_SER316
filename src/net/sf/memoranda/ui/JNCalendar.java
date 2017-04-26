/**
 * JNCalendar.java Created on 13.02.2003, 21:26:38 Alex Package:
 * net.sf.memoranda.ui
 * 
 * @author Alex V. Alishevskikh, alex@openmechanics.net Copyright (c) 2003
 *         Memoranda Team. http://memoranda.sf.net
 */
package net.sf.memoranda.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.date.CurrentDate;
import net.sf.memoranda.util.Local;
import net.sf.memoranda.util.Configuration;

// TODO: Auto-generated Javadoc
/**
 * The Class JNCalendar.
 */
/*$Id: JNCalendar.java,v 1.8 2004/11/05 07:38:10 pbielen Exp $*/
public class JNCalendar extends JTable {

	/** The date. */
	private CalendarDate _date = null;
	
	/** The ignore change. */
	private boolean ignoreChange = false;
	
	/** The selection listeners. */
	private Vector selectionListeners = new Vector();
	
	/** The start period. */
	CalendarDate startPeriod = null;
	
	/** The end period. */
	CalendarDate endPeriod = null;
	
	/** The renderer. */
	public JNCalendarCellRenderer renderer = new JNCalendarCellRenderer();

	/**
	 * Instantiates a new JN calendar.
	 */
	public JNCalendar() {
		this(CurrentDate.get());
	}
	
	/**
	 * Constructor for JNCalendar.
	 *
	 * @param date the date
	 */
	public JNCalendar(CalendarDate date) {
		super();
		/* table properties */
		setCellSelectionEnabled(true);
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		getTableHeader().setReorderingAllowed(false);
		getTableHeader().setResizingAllowed(false);
		set(date);

		/* selection listeners */
		final ListSelectionModel rowSM = getSelectionModel();
		final ListSelectionModel colSM = getColumnModel().getSelectionModel();
		ListSelectionListener lsl = new ListSelectionListener() {
                  public void valueChanged(ListSelectionEvent e) {
                      //Ignore extra messages.
                      if (e.getValueIsAdjusting())
                          return;
                      if (ignoreChange)
                          return;
                      int row = getSelRow();
                      int col = getSelCol();
                      Object val = getModel().getValueAt(row, col);
                      if (val != null) {
                          if (val.toString().equals(new Integer(_date.getDay()).toString()))
                              return;
                          _date = new CalendarDate(new Integer(val.toString()).intValue(),
                           _date.getMonth(),_date.getYear());
                          notifyListeners();
                      } else {
                          //getSelectionModel().clearSelection();
                          doSelection();
                      }
                  }

		};
		rowSM.addListSelectionListener(lsl);
		colSM.addListSelectionListener(lsl);
	}

	/**
	 * Gets the sel row.
	 *
	 * @return the sel row
	 */
	int getSelRow() {
		return this.getSelectedRow();
	}

	/**
	 * Gets the sel col.
	 *
	 * @return the sel col
	 */
	int getSelCol() {
		return this.getSelectedColumn();
	}

	/**
	 * Instantiates a new JN calendar.
	 *
	 * @param date the date
	 * @param sd the sd
	 * @param ed the ed
	 */
	public JNCalendar(CalendarDate date, CalendarDate sd, CalendarDate ed) {
		this(date);
		setSelectablePeriod(sd, ed);
	}

	/**
	 * Sets the.
	 *
	 * @param date the date
	 */
	public void set(CalendarDate date) {
		_date = date;
		setCalendarParameters();
		ignoreChange = true;
		this.setModel(new JNCalendarModel());
		ignoreChange = false;
		doSelection();
	}

	/**
	 * Gets the.
	 *
	 * @return the calendar date
	 */
	public CalendarDate get() {
		return _date;
	}

	/**
	 * Adds the selection listener.
	 *
	 * @param al the al
	 */
	public void addSelectionListener(ActionListener al) {
		selectionListeners.add(al);
	}

	/**
	 * Sets the selectable period.
	 *
	 * @param sd the sd
	 * @param ed the ed
	 */
	public void setSelectablePeriod(CalendarDate sd, CalendarDate ed) {
		startPeriod = sd;
		endPeriod = ed;
	}

	/**
	 * Notify listeners.
	 */
	private void notifyListeners() {
		for(int i=0;i<selectionListeners.size();i++) {
			((ActionListener) selectionListeners.get(i)).actionPerformed(
				new ActionEvent(this, 0, "Calendar event"));
		}
	}

	/* (non-Javadoc)
	 * @see javax.swing.JTable#getCellRenderer(int, int)
	 */
	public TableCellRenderer getCellRenderer(int row, int column) {
		Object d = this.getModel().getValueAt(row, column);
		/*
		 * if (d != null) return new JNCalendarCellRenderer( new
		 * CalendarDate(new Integer(d.toString()).intValue(), _date.getMonth(),
		 * _date.getYear()));
		 */
		if (d != null)
			renderer.setDate(
				new CalendarDate(
					new Integer(d.toString()).intValue(),
					_date.getMonth(),
					_date.getYear()));
		else
			renderer.setDate(null);
		return renderer;
	}

	/**
	 * Do selection.
	 */
	void doSelection() {
		ignoreChange = true;
		int selRow = getRow(_date.getDay());
		int selCol = getCol(_date.getDay());
		this.setRowSelectionInterval(selRow, selRow);
		this.setColumnSelectionInterval(selCol, selCol);
		ignoreChange = false;
	}

	/**
	 * Gets the row.
	 *
	 * @param day the day
	 * @return the row
	 */
	int getRow(int day) {
		return ((day - 1) + firstDay) / 7;
	}

	/**
	 * Gets the col.
	 *
	 * @param day the day
	 * @return the col
	 */
	int getCol(int day) {
		return ((day - 1) + firstDay) % 7;
	}

	/** The first day. */
	int firstDay;
	
	/** The days in month. */
	int daysInMonth;

	/**
	 * Sets the calendar parameters.
	 */
	void setCalendarParameters() {
		int d = 1;

		Calendar cal = _date.getCalendar();

		if (Configuration.get("FIRST_DAY_OF_WEEK").equals("mon")) {
			cal.setFirstDayOfWeek(Calendar.MONDAY);
			d = 2;
		} else
			cal.setFirstDayOfWeek(Calendar.SUNDAY);

		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.getTime();
		firstDay = cal.get(Calendar.DAY_OF_WEEK) - d;
		if (firstDay == -1)
			firstDay = 6;
		daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * The Class JNCalendarModel.
	 */
	/*$Id: JNCalendar.java,v 1.8 2004/11/05 07:38:10 pbielen Exp $*/
public class JNCalendarModel extends AbstractTableModel {

		/** The day names. */
		private String[] dayNames = Local.getWeekdayNames();

		/**
		 * Instantiates a new JN calendar model.
		 */
		public JNCalendarModel() {
			super();
		}

		/* (non-Javadoc)
		 * @see javax.swing.table.TableModel#getColumnCount()
		 */
		public int getColumnCount() {
			return 7;
		}

		/* (non-Javadoc)
		 * @see javax.swing.table.TableModel#getValueAt(int, int)
		 */
		public Object getValueAt(int row, int col) {
			//int pos = (row * 7 + col) - firstDay + 1;
			int pos = (row * 7 + (col + 1)) - firstDay;
			if ((pos > 0) && (pos <= daysInMonth))
				return new Integer(pos);
			else
				return null;

		}

		/* (non-Javadoc)
		 * @see javax.swing.table.TableModel#getRowCount()
		 */
		public int getRowCount() {
			return 6;
		}

		/* (non-Javadoc)
		 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
		 */
		public String getColumnName(int col) {
			return dayNames[col];
		}

	}

}
