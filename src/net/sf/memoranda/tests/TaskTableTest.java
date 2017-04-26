package net.sf.memoranda.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import net.sf.memoranda.ui.TaskTable;
import net.sf.memoranda.util.Context;

public class TaskTableTest {
	private TaskTable table;

	/**
	 * Tests updateColumnWidths() method's interaction with Context.java
	 */
	@Test
	public void testUpdateColumnWidths() {
		table = new TaskTable();
		
		table.updateColumnWidths();
		assertTrue(table.getColumnModel().getColumn(7).getWidth() == 67 
				&& table.getColumnModel().getColumn(8).getWidth() == 67);
		assertTrue(table.getColumnModel().getColumn(9).getWidth() == 67 
				&& table.getColumnModel().getColumn(10).getWidth() == 67);
		assertTrue(table.getColumnModel().getColumn(11).getWidth() == 67 
				&& table.getColumnModel().getColumn(12).getWidth() == 67);	
		Context.put("HIDE_TASK_EFFORT_COLUMNS", true);
		table.updateColumnWidths();
		assertTrue(table.getColumnModel().getColumn(7).getWidth() == 0 
				&& table.getColumnModel().getColumn(8).getWidth() == 0);
		assertTrue(table.getColumnModel().getColumn(9).getWidth() == 67 
				&& table.getColumnModel().getColumn(10).getWidth() == 67);
		assertTrue(table.getColumnModel().getColumn(11).getWidth() == 67 
				&& table.getColumnModel().getColumn(12).getWidth() == 67);
		Context.put("HIDE_TASK_EFFORT_COLUMNS", false);
		Context.put("HIDE_TASK_ERRORS_COLUMNS", true);	
		table.updateColumnWidths();
		assertTrue(table.getColumnModel().getColumn(7).getWidth() == 67 
				&& table.getColumnModel().getColumn(8).getWidth() == 67);
		assertTrue(table.getColumnModel().getColumn(9).getWidth() == 0 
				&& table.getColumnModel().getColumn(10).getWidth() == 0);
		assertTrue(table.getColumnModel().getColumn(11).getWidth() == 67 
				&& table.getColumnModel().getColumn(12).getWidth() == 67);
		Context.put("HIDE_TASK_ERRORS_COLUMNS", false);
		Context.put("HIDE_TASK_LOC_COLUMNS", true);		
		table.updateColumnWidths();
		assertTrue(table.getColumnModel().getColumn(7).getWidth() == 67 
				&& table.getColumnModel().getColumn(8).getWidth() == 67);
		assertTrue(table.getColumnModel().getColumn(9).getWidth() == 67 
				&& table.getColumnModel().getColumn(10).getWidth() == 67);
		assertTrue(table.getColumnModel().getColumn(11).getWidth() == 0 
				&& table.getColumnModel().getColumn(12).getWidth() == 0);
		Context.put("HIDE_TASK_LOC_COLUMNS", false);
		table.updateColumnWidths();
		assertTrue(table.getColumnModel().getColumn(7).getWidth() == 67 
				&& table.getColumnModel().getColumn(8).getWidth() == 67);
		assertTrue(table.getColumnModel().getColumn(9).getWidth() == 67 
				&& table.getColumnModel().getColumn(10).getWidth() == 67);
		assertTrue(table.getColumnModel().getColumn(11).getWidth() == 67 
				&& table.getColumnModel().getColumn(12).getWidth() == 67);	
	}

}
