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

public class ContactsMenu extends JPopupMenu{
	
	JMenuItem emailMenu;
	ContactsEmailDialog emailDialog;
	
	
	//Constructor
	public ContactsMenu(int contactIndex){
		
		emailMenu = new JMenuItem("Email Contact");
		
		//Add all items to menu
		add(emailMenu);
		
		//Menu Events
		emailMenu.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(ActionEvent e){
				String emailAddress = getContactEmail(contactIndex);
				openEmailClient(emailAddress);
			}
		});		
	}
	
	private String getContactEmail(int index){
		System.out.println("Email Contact Selected"); //Debug Output
		
		String emailAddress = "";		
		
		try{
			CSVReader reader = new CSVReader(new FileReader(Contact.LibraryFile()));
			String [] currentContact;
			List<String[]> allElements = reader.readAll();
			currentContact = allElements.get(index);
			emailAddress = currentContact[2];
		}catch(FileNotFoundException e){
			new ExceptionDialog(e);
		}catch(IOException e){
			new ExceptionDialog(e);
		}
		
		return emailAddress;
	}
	
	private boolean openEmailClient(String emailAddress){
		
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

