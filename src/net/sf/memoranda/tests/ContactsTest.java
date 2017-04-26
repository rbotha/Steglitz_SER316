package net.sf.memoranda.tests;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import net.sf.memoranda.ui.Contact;
import net.sf.memoranda.ui.ContactsAddDialog;

// TODO: Auto-generated Javadoc
/**
 * The Class ContactsTest.
 */
public class ContactsTest {

	/** The contact. */
	Contact contact = new Contact("Chuck","Norris", null, "2885446776", null, null);
	
	/** The contact diag. */
	ContactsAddDialog contactDiag = new ContactsAddDialog();
	
	/**
	 * Test contact attr.
	 */
	@Test
	public void testContactAttr() {
		assertEquals("Chuck", contact.getFirstName());	
	}
	
	/**
	 * Test contact database exists.
	 */
	@Test
	public void testContactDatabaseExists(){
		File f = new File(Contact.LibraryFile());
		assertTrue(f.exists());
	}
}
