/*
  File:       GoogleMailTest.java
  Author:     hqpham
  Date:       04/09/2017

  Description:    Exercises the GoogleMail's IsValidEmail() method.
*/

package net.sf.memoranda.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import net.sf.memoranda.GoogleMail;


/**
  Class:      GoogleMailTest

  Description:    Exercises the GoogleMail's IsValidEmail() method.
*/
public class GoogleMailTest {
  /**
   * Sets up before making the EventTest class
   * @throws Exception problem with setting up the class
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
  }

  /**
   * Tears down the after taking down the EventTest class
   * @throws Exception problem with tearing down the class
   */
  @AfterClass
  public static void tearDownAfterClass() throws Exception {
  }

  /**
   * Sets up the EventTest class
   * @throws Exception if there is a problem setting up
   */
  @Before
  public void setUp() throws Exception {

  }

  /**
   * Tears down the EventTest class
   * @throws Exception if there is a problem tearing down the class
   */
  @After
  public void tearDown() throws Exception {
  }

  /**
   * Tests the IsValidEmail() method of GoogleMail.
   */
  @Test
  public void testValidEmails() {
    assertTrue(GoogleMail.IsValidEmail("me@example.com"));
    assertTrue(GoogleMail.IsValidEmail("this.is.valid@test.net"));
    assertTrue(GoogleMail.IsValidEmail("anony+mous@example.com"));
    assertTrue(GoogleMail.IsValidEmail("another%^$^123@sample.asu.edu"));
    assertTrue(GoogleMail.IsValidEmail("anony@test123.gov"));
    assertTrue(GoogleMail.IsValidEmail("!#$%&'+-/=.?^`{|}~@[1.0.0.127]"));
  }

  /**
   * Tests the IsValidEmail() method of GoogleMail.
   */
  @Test
  public void testInvalidEmails() {
    assertFalse(GoogleMail.IsValidEmail("me@"));
    assertFalse(GoogleMail.IsValidEmail("@test.com"));
    assertFalse(GoogleMail.IsValidEmail("anony@@example.gov"));
    assertFalse(GoogleMail.IsValidEmail("not.valid.com"));
    assertFalse(GoogleMail.IsValidEmail("my.@test.com"));
    assertFalse(GoogleMail.IsValidEmail(".me@example.com"));
    assertFalse(GoogleMail.IsValidEmail("not_valid@test..com"));
    assertFalse(GoogleMail.IsValidEmail("this-isnt\\@valid.net"));
  }
}
