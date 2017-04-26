package net.sf.memoranda.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import net.sf.memoranda.EventImpl;
import net.sf.memoranda.util.Util;
import nu.xom.Attribute;
import nu.xom.Element;


// TODO: Auto-generated Javadoc
/**
 * The Class EmailTest.
 */
public class EmailTest {
	
	/** The el. */
	Element el = null;
	
	/** The impl. */
	private EventImpl impl = null;
	
	/**
	 * Setup.
	 */
	@Before
	public void setup(){
		int hh = 12;
		int mm = 23;
		
		
		el = new Element("event");
		el.addAttribute(new Attribute("id", Util.generateId()));
		el.addAttribute(new Attribute("hour", String.valueOf(hh)));
		el.addAttribute(new Attribute("min", String.valueOf(mm)));
		el.addAttribute(new Attribute("note", "Nothing"));
		el.addAttribute(new Attribute("email", "dummyser316@gmail.com"));
   
		impl = new EventImpl(el);
	}
	
	/**
	 * Test sending email.
	 */
	@Test
	public void testSendingEmail() {
		assertTrue(impl.sendEmail());
	}

}
