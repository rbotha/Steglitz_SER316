package net.sf.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.opencsv.CSVReader;

import net.sf.memoranda.util.Util;
import net.sf.memoranda.util.Local;

import javax.swing.*;
import java.awt.event.*;

public class Contacts extends JPanel{
	
	DailyItemsPanel parentPanel = null;
	
	DefaultListModel<String> listModel = new DefaultListModel<String>();
	JList<String> list = new JList<String>(listModel);
	ContactsAddDialog dialog;
	JScrollPane scrollPane = new JScrollPane();
	
	JMenuItem contactMenu;
	
	public Contacts(DailyItemsPanel _parentPanel) {
        try {
            parentPanel = _parentPanel;
            jbInit();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
	
	public void RefreshContacts(){
		try {
			CSVReader reader = new CSVReader(new FileReader(Contact.LibraryFile()));
			String [] nextLine;
			while ((nextLine = reader.readNext()) != null) {
		        // nextLine[] is an array of values from the line
				Contact contact = new Contact(nextLine[0], nextLine[1], nextLine[2], nextLine[3], nextLine[4], nextLine[5]);
		        listModel.addElement(contact.toString());
		     }
			
			} catch (FileNotFoundException e) {
				new ExceptionDialog(e);
			} catch (IOException e) {
				new ExceptionDialog(e);
			}
	}
	
	void loadContacts(){
		listModel.clear();
		File f = new File(Contact.LibraryFile());
		if(f.exists() && !f.isDirectory()) { 
			Util.debug("Open contacts:" + Contact.LibraryFile());
			RefreshContacts();
		}else{
			try {
				BufferedWriter write = new BufferedWriter(new FileWriter(Contact.LibraryFile()));
				Util.debug("Contacts library does not exist.");
				Util.debug("Creating contacts library:" + Contact.LibraryFile());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				new ExceptionDialog(e);
			}
		}
	}
	
	void jbInit() throws Exception {
		
		dialog = new ContactsAddDialog();

		setLayout(null);
		
		list.setBounds(71, 225, 496, 437);
		this.add(list);
		
		JButton btnAddContact = new JButton(Local.getString("Add Contact"));
		btnAddContact.setBounds(71, 90, 117, 29);
		btnAddContact.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dialog.Invoke(listModel);
			}
		});
		this.add(btnAddContact);
		
		JButton btnDeleteContact = new JButton(Local.getString("Delete Contact"));
		btnDeleteContact.setBounds(450, 90, 130, 29);
		btnDeleteContact.addActionListener(new ActionListener() {		
			public void actionPerformed(ActionEvent e) {
				dialog.deleteContact(list, listModel);
			}
		});
		this.add(btnDeleteContact);
		
		//Menu Items For Right Click Menu
		contactMenu = new JMenuItem("Email Contact");
		add(contactMenu);
		
		list.addMouseListener(new MouseAdapter(){
			@Override
			public void mousePressed(MouseEvent e){
				if(e.getButton() == MouseEvent.BUTTON3){
					contactsMenu menu = new contactsMenu();
					menu.show(e.getComponent(),e.getX(),e.getY());
				}
			}
			
			//JPopupMenu Class
			class contactsMenu extends JPopupMenu{
				JMenuItem emailMenu;
				
				//Constructor
				public contactsMenu(){
					emailMenu = new JMenuItem("Email Contact");
					
					//Add all items to menu
					add(emailMenu);
					
					//Menu Events
					emailMenu.addActionListener(new java.awt.event.ActionListener(){
						public void actionPerformed(ActionEvent e){
							System.out.println("Email Contact"); //Debug Output
							
							Desktop desktop = Desktop.getDesktop();
							
							try{
								desktop.mail();
							}catch(IOException ex){
								ex.printStackTrace();
							}
							
							try{
								String message = "mailto:gschober@asu.edu?subject=Test%20Email";
								URI uri = URI.create(message);
								desktop.mail(uri);
							}catch(IOException ex){
								ex.printStackTrace();
							}
						}
					});
				}
			}
		
		});
		
		loadContacts();
		

		
	}
	
}
