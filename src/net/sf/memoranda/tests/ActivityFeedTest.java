/**
 * 
 */
package net.sf.memoranda.tests;

import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import net.sf.memoranda.ui.ActivityFeed;

/**
 * Apr 14, 2017
 * @author Jesus Rodriguez Jr
 *
 */
public class ActivityFeedTest {
	private ActivityFeed activityFeed = null;
	private ArrayList<String> names = null;
	private ArrayList<String> times	= null;
	private String name = null, time = null;

	/**Method setting up
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		names = new ArrayList<String>(Arrays.asList("Test","test2","test3","test4"));
		times = new ArrayList<String>(Arrays.asList("2017-04-08 00:18:05.704","2017-04-13 17:30:13.731",
													"2017-04-14 10:02:09.962", "2017-04-10 21:11:18.794"));
		name = "Billy Maze";
		time = "2016-04-10 21:11:18.794";
		
		activityFeed = new ActivityFeed(names, times);
	}

	/**Method for tearing down
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		activityFeed.clear();
		names.clear();
		times.clear();
		name = null;
		time = null;
	}

	@Test
	public void ActivityFeedConstructorTest() {
		
		assertNotNull(activityFeed);
		
		assertEquals("times == getTimes()",activityFeed.getTimes(), times);
		assertEquals("names == getNames()",activityFeed.getNames(), names);
		
		activityFeed.setName(0, null);
		assertTrue(!activityFeed.getNames().contains(null));

		names.add(null);
		activityFeed = new ActivityFeed(names, times);	
		assertTrue(activityFeed.getNames().contains(null));
		
		activityFeed = new ActivityFeed(null,null);
		assertNotNull(activityFeed.getNames());
		assertNotNull(activityFeed.getTimes());
		
		activityFeed = new ActivityFeed();
		
	}
	
	@Test
	public void GetNamesTest(){
		
		assertEquals("names != getNames)", activityFeed.getNames(), names);
		activityFeed.getNames().add("billy");
		activityFeed.getNames().add("maze");
		assertEquals("Names == getNames)", activityFeed.getNames(), names);
		names.add(name);
		assertNotEquals("names != getNames)", activityFeed.getNames(), names);
	}
	
	@Test
	public void GetTimesTest(){
		
		assertEquals("times == getTimes)", activityFeed.getTimes(), times);
		activityFeed.getTimes().add(time);
		activityFeed.getNames().add(time+1);
		assertEquals("times == getTimes)", activityFeed.getTimes(), times);
		times.add("2017-04-08 00:18:05.704");
		assertNotEquals("times != getTimes)", activityFeed.getTimes(), times);
		
	}
	
	@Test
	public void GetNameIndexTest(){
		
		assertFalse(activityFeed.getNames().contains(name));
		activityFeed.add(name, time);
		assertTrue(activityFeed.getNames().contains(name));
		
		assertNull(activityFeed.getName(20));
		assertNotNull(activityFeed.getName(0));
	}
	
	@Test
	public void GetTimeIndexTest(){
		
		assertFalse(activityFeed.getTimes().contains(time));
		activityFeed.add(name, time);
		assertTrue(activityFeed.getTimes().contains(time));
		
		assertNull(activityFeed.getName(20));
		assertNotNull(activityFeed.getName(0));
	}
	
	@Test
	public void SetNameTest(){
		assertNotEquals(activityFeed.getName(0), name);
		activityFeed.setName(0, name);
		assertEquals(activityFeed.getName(0), name);
		activityFeed.setName(-1, name);
		assertNotEquals(activityFeed.getName(-1), name);
		activityFeed.setName(0, null);
		assertNotEquals(activityFeed.getName(0), null);
		
	}
	
	@Test
	public void SetTimeTest(){
		assertNotEquals(activityFeed.getTime(0), time);
		activityFeed.setTime(0, time);
		assertEquals(activityFeed.getTime(0), time);
		activityFeed.setTime(-1, time);
		assertNotEquals(activityFeed.getTime(-1), time);
		activityFeed.setTime(0, null);
		assertNotEquals(activityFeed.getTime(0), null);
		
	}
	
	@Test
	public void AddThreeParamTest(){

		activityFeed.add(20, name, time);
		assertFalse(activityFeed.getNames().contains(name));
		assertFalse(activityFeed.getTimes().contains(time));
		assertTrue(activityFeed.getNames().size() == 4);
		
		activityFeed.add(2, name, time);
		assertTrue(activityFeed.getNames().contains(name));
		assertTrue(activityFeed.getTimes().contains(time));
		assertTrue(activityFeed.getNames().size() == 5);
		
		activityFeed.add(2, null, null);
		assertFalse(activityFeed.getNames().contains(null));
		assertFalse(activityFeed.getTimes().contains(null));
		assertTrue(activityFeed.getNames().size() == 5);
		
	}
	
	@Test
	public void AddTwoParamTest(){

		activityFeed.add(null, time);
		assertFalse(activityFeed.getNames().contains(name));
		assertFalse(activityFeed.getTimes().contains(time));
		assertTrue(activityFeed.getNames().size() == 4);
		
		activityFeed.add(name, null);
		assertFalse(activityFeed.getNames().contains(name));
		assertFalse(activityFeed.getTimes().contains(time));
		assertTrue(activityFeed.getNames().size() == 4);
		
		activityFeed.add(name, time+"HHMM");
		assertFalse(activityFeed.getNames().contains(name));
		assertFalse(activityFeed.getTimes().contains(time));
		assertFalse(activityFeed.getNames().size() == 5);
		
		activityFeed.add(name, time);
		assertTrue(activityFeed.getNames().contains(name));
		assertTrue(activityFeed.getTimes().contains(time));
		assertTrue(activityFeed.getNames().size() == 5);
		
	}
	
	@Test
	public void ClearTest(){

		assertEquals(activityFeed.getNames(), names);
		assertEquals(activityFeed.getTimes(), times);
		activityFeed.clear();
		
		assertNotEquals(activityFeed.getNames(), names);
		assertNotEquals(activityFeed.getTimes(), times);		
	}
	
	@Test
	public void OrderByDateTest(){
		Timestamp currTime = new Timestamp(Calendar.getInstance().getTimeInMillis());
		
		assertFalse(activityFeed.getTime(0).equals("2017-04-14 10:02:09.962"));
		activityFeed.orderByDate();
		assertTrue(activityFeed.getTime(0).equals("2017-04-14 10:02:09.962"));
		activityFeed.add(name,currTime.toString());
		activityFeed.orderByDate();
		assertTrue(activityFeed.getTime(0).equals(currTime.toString()));
	}

	@Test
	public void GetOrderedByDateTest(){
		
		assertEquals(activityFeed.getTimes(), times);
		activityFeed.getOrderedByDate();
		assertNotEquals(activityFeed.getTimes(), times);
		

	}

}
