package net.sf.memoranda.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * The Class AllTests.
 */
@RunWith(Suite.class)
@SuiteClasses({ ActivityFeedPanelTest.class, ActivityFeedTest.class, CalendarDateTest.class, 
	ContactsTest.class, EmailTest.class, EventDialogTest.class, GoogleMailTest.class, 
	LocalTest.class, ProjectImplTest.class, ResourcesImplTest.class, TaskImplTest.class })
public class AllTests {

}
