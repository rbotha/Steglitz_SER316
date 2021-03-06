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

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;

import java.net.URI;

import com.opencsv.CSVReader;

import net.sf.memoranda.util.Util;
import net.sf.memoranda.util.Local;

import javax.swing.*;
import java.awt.event.*;

import java.util.*;

public class Contacts extends JPanel{
	
	DailyItemsPanel parentPanel = null;
	
	DefaultListModel<String> listModel = new DefaultListModel<String>();
	JList<String> list = new JList<String>(listModel);
	ContactsAddDialog dialog;
	JScrollPane scrollPane = new JScrollPane();
	
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
		
		list.setBounds(71, 150, 525, 437);
		this.add(list);
		
		Border border = BorderFactory.createLineBorder(Color.BLACK,1);
		
		JLabel lblColFirstName = new JLabel(Local.getString("F. Name"));
		lblColFirstName.setBounds(71,125,55,16);
		lblColFirstName.setBorder(border);
		this.add(lblColFirstName);
		
		JLabel lblColLastName = new JLabel(Local.getString("L. Name"));
		lblColLastName.setBounds(133,125,55,16);
		lblColLastName.setBorder(border);
		this.add(lblColLastName);
		
		JLabel lblColEmail = new JLabel(Local.getString("Email Address"));
		lblColEmail.setBounds(287,125,85,16);
		lblColEmail.setBorder(border);
		this.add(lblColEmail);
		
		JLabel lblColPhone = new JLabel(Local.getString("Phone No."));
		lblColPhone.setBounds(200,125,75,16);
		lblColPhone.setBorder(border);
		this.add(lblColPhone);
		
		JButton btnAddContact = new JButton(Local.getString("Add Contact"));
		btnAddContact.setBounds(71, 90, 117, 29);
		btnAddContact.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        dialog.Invoke(listModel);
		    }
		});
		this.add(btnAddContact);
		
		JButton btnDeleteContact = new JButton(Local.getString("Delete Contact"));
		btnDeleteContact.setBounds(200, 90, 130, 29);
		btnDeleteContact.addActionListener(new ActionListener() {		
			public void actionPerformed(ActionEvent e) {
				dialog.deleteContact(list, listModel);
			}
		});
		this.add(btnDeleteContact);
		loadContacts();
		
		//Added by Greg Schoberth
		//Sprint 2
		//Mouse Listener for opening right-click menu. Default options only currently show email option for contacts. 
		list.addMouseListener(new MouseAdapter(){
		    @Override
		    public void mousePressed(MouseEvent e){
		        if(e.getButton() == MouseEvent.BUTTON3){
                      if(list.getSelectedIndex() != -1){
                          ContactsMenu menu = new ContactsMenu(list.getSelectedIndex());
                          menu.show(e.getComponent(),e.getX(),e.getY());
                      }
		        }
		    }
		});
		
		loadContacts();
		

		
	}
	//get List
	DefaultListModel<String> getList(){
		return listModel;
	}
	
}
