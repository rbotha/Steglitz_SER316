/**
 * 
 */
package net.sf.memoranda.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;

import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import net.sf.memoranda.CurrentProject;
import net.sf.memoranda.NoteList;
import net.sf.memoranda.ProjectManager;
import net.sf.memoranda.TaskList;
import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.ui.ActivityFeedPanel;

/**
 * Testing Activity Panel.
 * Apr 9, 2017
 * @author Jesus Rodriguez Jr
 *
 */
public class ActivityFeedPanelTest {
	private ActivityFeedPanel activityPanel = null;
	private CurrentProject project = null;
	private TaskList taskList = null;
	private NoteList noteList = null;
	private long pastHr, hrAhead, minAhead, hrBehind;
	private Calendar zone = null;

	/**Method
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		ProjectManager.createProject("Test", "J-Unit-Testing", null, null);
		project = new CurrentProject();
		project.set(ProjectManager.getProject("Test"));
		
		activityPanel = new ActivityFeedPanel();
		
		taskList = project.getTaskList();
		noteList = project.getNoteList();
		
		pastHr = Long.parseLong("9000000")+zone.getInstance().getTimeInMillis();//Past 2 hours
		minAhead = Long.parseLong("800000")+zone.getInstance().getTimeInMillis();//couple min ahead
		hrAhead = Long.parseLong("4000000")+zone.getInstance().getTimeInMillis();//hour ahead
		hrBehind = Long.parseLong("-4000000")+zone.getInstance().getTimeInMillis();//hour behind
		zone = Calendar.getInstance();//Current time
		
		taskList.createTask(CalendarDate.today(), CalendarDate.yesterday(), "Current time", 0, pastHr, pastHr, pastHr, "True",null,0,0,0,0, new Timestamp(zone.getTimeInMillis()));
		taskList.createTask(CalendarDate.today(), CalendarDate.yesterday(), "Couple minutes ahead of current time", 0, pastHr, pastHr, pastHr, "True",null,0,0,0,0, new Timestamp(minAhead));
		taskList.createTask(CalendarDate.today(), CalendarDate.yesterday(), "On next hr but not an actual hour", 0, pastHr, pastHr, pastHr, "True",null,0,0,0,0, new Timestamp(hrAhead));
		taskList.createTask(CalendarDate.today(), CalendarDate.yesterday(), "Past 2 hours", 0, pastHr, pastHr, pastHr, "True",null,0,0,0,0, new Timestamp(pastHr));
		taskList.createTask(CalendarDate.today(), CalendarDate.yesterday(), "behing an hour", 0, pastHr, pastHr, pastHr, "True",null,0,0,0,0, new Timestamp(hrBehind));
		
		noteList.createNoteForDate(CalendarDate.yesterday()).setEdit(new Timestamp(minAhead));
		noteList.createNoteForDate(CalendarDate.yesterday()).setEdit(new Timestamp(pastHr));
		noteList.createNoteForDate(CalendarDate.yesterday()).setEdit(new Timestamp(hrAhead));
		noteList.createNoteForDate(CalendarDate.yesterday()).setEdit(new Timestamp(hrBehind));
		
	}

	/**Method
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		activityPanel.removeAll();
		project.free();
		pastHr = 0;
		hrAhead = 0;
		minAhead = 0;
		hrBehind = 0;
		zone.clear();
	}

	@Test
	public void RefreshTest() {	

		assertTrue(activityPanel.refresh());
		
		taskList.createTask(CalendarDate.today(), CalendarDate.yesterday(), "Null Value: Exception", 0, pastHr, pastHr, pastHr, "False",null,0,0,0,0, null);
		assertFalse(activityPanel.refresh());//return false

	}

}
