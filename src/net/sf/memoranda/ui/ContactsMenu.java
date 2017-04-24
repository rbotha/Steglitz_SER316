package net.sf.memoranda.ui;

import javax.swing.*;

import com.opencsv.CSVReader;

import net.sf.memoranda.util.Local;

import java.awt.Desktop;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.List;


/**
 * 
 * April 15, 2017
 * @author Gregory Schoberth
 *
 */

public class ContactsMenu extends JPopupMenu{
	
	JMenuItem emailMenu;
	private String emailAddress;
	
	
	//Default Constructor
	public ContactsMenu(){
		
	}
	
	//Single Parameter Constructor
	public ContactsMenu(int contactIndex){
		
		emailMenu = new JMenuItem("Email Contact");
		
		//Add all items to menu
		add(emailMenu);
		
		//Menu Events
		emailMenu.addActionListener(new java.awt.event.ActionListener(){
                    public void actionPerformed(ActionEvent e){
		                getContactEmail(contactIndex);
		                openEmailClient(emailAddress);
                    }
		});		
	}
	
	//Attempts to get the email address from the indicated index of the contacts CSV file.
	//Will return true if the email address was recovered.
	public boolean getContactEmail(int index){
		System.out.println("Email Contact Selected"); //Debug Output
		
		try{
			CSVReader reader = new CSVReader(new FileReader(Contact.LibraryFile()));
			String [] currentContact;
			List<String[]> allElements = reader.readAll();
			currentContact = allElements.get(index);
			emailAddress = currentContact[2];
		}catch(FileNotFoundException e){
			new ExceptionDialog(e);
			return false;
		}catch(IOException e){
			new ExceptionDialog(e);
			return false;
		}
		
		return true;
	}
	
	//Attempts to open the user's default email client. 
	//Will return a value of true when the email client is successfully open.
	public boolean openEmailClient(String emailAddress){
		
		Desktop desktop = Desktop.getDesktop();
	
		try{
			String message = "mailto:" + emailAddress + "?subject=";
			URI uri = URI.create(message);
			desktop.mail(uri);
		}catch(IOException e){
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

}

