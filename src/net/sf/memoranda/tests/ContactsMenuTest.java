package net.sf.memoranda.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import net.sf.memoranda.ui.ContactsMenu;

public class ContactsMenuTest {

	ContactsMenu contactsMenu;
	
	@BeforeClass
	public static void setupBeforeClass() throws Exception{
		
	}
	
	@Before
	public void setup() throws Exception{
		contactsMenu = new ContactsMenu();
	}
	
	//Tests basic email retrieval 
	@Test
	public void testGetContactEmail(){

		assertTrue(contactsMenu.getContactEmail(0));
	}
	
	//Tests that the email client was successfully open
	@Test
	public void testOpenEmailClient(){
		assertTrue(contactsMenu.openEmailClient(""));
	}
}
