package net.sf.memoranda.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ CalendarDateTest.class, ContactsTest.class, EmailTest.class, EventDialogTest.class, 
	GoogleMailTest.class, ProjectImplTest.class, ResourcesImplTest.class, TaskImplTest.class })
public class AllTests {

}
