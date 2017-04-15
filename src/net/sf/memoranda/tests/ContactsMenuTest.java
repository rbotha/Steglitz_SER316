package net.sf.memoranda.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import net.sf.memoranda.ui.ContactsMenu;

public class ContactsMenuTest {

	@BeforeClass
	public static void setupBeforeClass() throws Exception{
		
	}
	
	@Before
	public void setup() throws Exception{
		
	}
	
	//Tests basic email retrieval 
	@Test
	public void testGetContactEmail(){
		ContactsMenu contactsMenu = new ContactsMenu();
		
		assertTrue(contactsMenu.getContactEmail(0));
	}
	
	//Tests that the email client was successfully open
	@Test
	public void testOpenEmailClient(){
		ContactsMenu contactsMenu = new ContactsMenu();
		assertTrue(contactsMenu.openEmailClient(""));
	}
}
