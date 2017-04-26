/*
  File:	EventDialogTest.java
  Author:	WSamuels	
  Date:	4/15/2017
  
  Description:A test case scenario for closing EventDialog.java with press of esc
*/
package net.sf.memoranda.tests;
import javax.swing.JFrame;
import static org.junit.Assert.*;
import net.sf.memoranda.ui.EventDialog;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
Class:	EventDialogTest

Description:  JUnit test class for EventDialog.java
*/
public class EventDialogTest {
	private JFrame EFrame;
	private EventDialog EventDiag;
	private Robot Rob;              //rob the robot, he terminates with the esc key.
	/**
	  Method: setUP
	  Inputs:
	  Returns:

	  Description:  Creates JFrame, EventDialog, and Robot instances
	*/
	@Before
	public void setUp() throws Exception {
		EFrame = new JFrame("eventDialogue");
		EventDiag = new EventDialog(EFrame, "eventDialogue");
		Rob = new Robot();		
	}

	@After
	public void tearDown() throws Exception {
	}
	/**
	  Method:test
	  Inputs:
	  Returns:

	  Description:  checks to see if the Frame is active after esc is pressed.
	*/
	@Test
	public void test() {
		Rob.keyPress(KeyEvent.VK_ESCAPE);
		assertFalse(EFrame.isActive());  //after esc is pressed, the frame is no longer active
	}

}
