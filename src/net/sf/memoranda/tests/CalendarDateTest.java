/*
  File:       CalendarDateTest.java
  Author:     wsamuels
  Date:       04/09/2017
  Description:    test Utilities
*/

package net.sf.memoranda.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import net.sf.memoranda.util.Util;
import java.util.Calendar;

public class CalendarDateTest {

private Util U;
private Calendar C;


	@Before
	public void setUp() throws Exception {
		U = new Util();
		
	
	}


	@Test
	public void equalsTest() {
		assertNotNull(U.getDateStamp(C));

	
	}

	@Test
	public void beforeTest() {

	}

	@Test
	public void afterTest() {
		
	}

}