/*
  File:       CalendarDateTest.java
  Author:     hqpham
  Date:       04/09/2017

  Description:    Tests CalendarDate class
*/

package net.sf.memoranda.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import net.sf.memoranda.date.CalendarDate;

public class CalendarDateTest {

	private CalendarDate cd1;
	private CalendarDate cd2;
	private CalendarDate cd3;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		cd1 = new CalendarDate(04,04,2017);
		cd2 = new CalendarDate(05,04,2017);
		cd3 = new CalendarDate(04,04,2017);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void equalsTest() {
		assertTrue(cd1.equals(cd3));
		assertFalse(cd1.equals(cd2));
	}

	@Test
	public void beforeTest() {
		assertTrue(cd1.before(cd2));
		assertFalse(cd2.before(cd1)); //Pass with assertFalse
	}

	@Test
	public void afterTest() {
		assertFalse(cd1.after(cd2));
		assertTrue(cd2.after(cd1));
	}

}
