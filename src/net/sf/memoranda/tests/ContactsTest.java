package net.sf.memoranda.tests;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import net.sf.memoranda.ui.Contact;
import net.sf.memoranda.ui.ContactsAddDialog;

public class ContactsTest {

	Contact contact = new Contact("Chuck","Norris", null, "2885446776", null, null);
	ContactsAddDialog contactDiag = new ContactsAddDialog();
	@Test
	public void testContactAttr() {
		assertEquals("Chuck", contact.getFirstName());	
	}
	@Test
	public void testContactDatabaseExists(){
		File f = new File(Contact.LibraryFile());
		assertTrue(f.exists());
	}
}
