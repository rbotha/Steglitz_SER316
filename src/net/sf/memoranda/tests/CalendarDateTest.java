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

// TODO: Auto-generated Javadoc
/**
 * The Class CalendarDateTest.
 */
public class CalendarDateTest {

	/** The cd 1. */
	private CalendarDate cd1;
	
	/** The cd 2. */
	private CalendarDate cd2;
	
	/** The cd 3. */
	private CalendarDate cd3;

	/**
	 * Sets the up before class.
	 *
	 * @throws Exception the exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * Tear down after class.
	 *
	 * @throws Exception the exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@Before
	public void setUp() throws Exception {
		cd1 = new CalendarDate(04,04,2017);
		cd2 = new CalendarDate(05,04,2017);
		cd3 = new CalendarDate(04,04,2017);
	}

	/**
	 * Tear down.
	 *
	 * @throws Exception the exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Equals test.
	 */
	@Test
	public void equalsTest() {
		assertTrue(cd1.equals(cd3));
		assertFalse(cd1.equals(cd2));
	}

	/**
	 * Before test.
	 */
	@Test
	public void beforeTest() {
		assertTrue(cd1.before(cd2));
		assertFalse(cd2.before(cd1)); //Pass with assertFalse
	}

	/**
	 * After test.
	 */
	@Test
	public void afterTest() {
		assertFalse(cd1.after(cd2));
		assertTrue(cd2.after(cd1));
	}

}
