package net.sf.memoranda.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import net.sf.memoranda.util.Util;
import net.sf.memoranda.util.Local;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

public class ContactsAddDialog extends JFrame {
	
	private JPanel contentPane;
	private JTextField txtFirstName;
	private JTextField txtLastName;
	private JTextField txtAddress;
	private JTextField txtPhoneNumber;
	private JTextField txtfaxNumber;
	private JTextField txtOther;
	
	DefaultListModel<String> newList;

	/**
	 * Launch the application.
	 */
	static ContactsAddDialog frame;
	
	/**
	 * Create the frame.
	 */
	public void Invoke(DefaultListModel<String> list){
		frame = new ContactsAddDialog(list);
		frame.setTitle("Add Contact");
	}
	public void deleteContact(JList<String> list, DefaultListModel<String> listModel){
		int count = 0;
		if(list.getSelectedIndex() != -1){
			CSVReader reader2;
			try {
				reader2 = new CSVReader(new FileReader(Contact.LibraryFile()));
				List<String[]> allElements = reader2.readAll();
            	allElements.remove(list.getSelectedIndex());
            	reader2.close();
            	FileWriter sw = new FileWriter(Contact.LibraryFile());
            	CSVWriter writer = new CSVWriter(sw);
            	writer.writeAll(allElements);
            	writer.close();
            	listModel.remove(list.getSelectedIndex());
			}catch(Exception e){
				new ExceptionDialog(e);
			}
			
		}else{
			JOptionPane.showMessageDialog(this, Local.getString("No contact selected"));
		}
	}
	private void addContact(String firstName, String lastName, String address, String phoneNumber, String faxNumber, String otherNumber){
		CSVWriter writer;
		try {
			Contact contact = new Contact(firstName, lastName, address, phoneNumber, faxNumber, otherNumber);
			Util.debug("Added Contact: " + contact.toString());	
			writer = new CSVWriter(new FileWriter(new File(Contact.LibraryFile()),true));
			String[] entries = {firstName,lastName,address,phoneNumber,faxNumber,otherNumber};
			List<String[]> rows = new ArrayList<String[]>();
			rows.add(entries);
		     writer.writeAll(rows);
			 writer.close();
			 newList.addElement(contact.toString());
			 
			 txtFirstName.setText("");
			 txtLastName.setText("");
			 txtAddress.setText("");
			 txtPhoneNumber.setText("");
			 txtfaxNumber.setText("");
			 txtOther.setText("");
			 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			new ExceptionDialog(e);
		}
	}
	public ContactsAddDialog() {
		
	}
	 public ContactsAddDialog(DefaultListModel<String> list) {
		 
		 newList = list;
		 
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(500, 100, 300, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtFirstName = new JTextField();
		txtFirstName.setBounds(130, 25, 130, 26);
		contentPane.add(txtFirstName);
		txtFirstName.setColumns(10);
		
		txtLastName = new JTextField();
		txtLastName.setColumns(10);
		txtLastName.setBounds(130, 75, 130, 26);
		contentPane.add(txtLastName);
		
		txtAddress = new JTextField();
		txtAddress.setColumns(10);
		txtAddress.setBounds(130, 125, 130, 26);
		contentPane.add(txtAddress);
		
		txtPhoneNumber = new JTextField();
		txtPhoneNumber.setColumns(10);
		txtPhoneNumber.setBounds(130, 175, 130, 26);
		contentPane.add(txtPhoneNumber);
		
		txtfaxNumber = new JTextField();
		txtfaxNumber.setColumns(10);
		txtfaxNumber.setBounds(130, 225, 130, 26);
		contentPane.add(txtfaxNumber);
		
		txtOther = new JTextField();
		txtOther.setColumns(10);
		txtOther.setBounds(130, 275, 130, 26);
		contentPane.add(txtOther);
		
		JLabel lblFirstName = new JLabel(Local.getString("First Name:"));
		lblFirstName.setBounds(15, 25, 85, 32);
		contentPane.add(lblFirstName);
		
		JLabel lblLastName = new JLabel(Local.getString("Last Name:"));
		lblLastName.setBounds(15, 75, 84, 16);
		contentPane.add(lblLastName);
		
		JLabel lblAddress = new JLabel(Local.getString("Email Address:"));
		lblAddress.setBounds(15, 125, 130, 26);
		contentPane.add(lblAddress);
				
		JLabel lblPhoneNumber = new JLabel(Local.getString("Phone Number:"));
		lblPhoneNumber.setBounds(15, 175, 130, 26);
		contentPane.add(lblPhoneNumber);
				
		JLabel lblFaxNumber = new JLabel(Local.getString("Fax Number:"));
		lblFaxNumber.setBounds(15, 225, 84, 16);
		contentPane.add(lblFaxNumber);
				
		JLabel lblOther = new JLabel(Local.getString("Other:"));
		lblOther.setBounds(15, 275, 84, 16);
		contentPane.add(lblOther);
		
		JButton btnAdd = new JButton(Local.getString("Add Contact"));
		btnAdd.setBounds(15, 325, 117, 29);
		btnAdd.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
				addContact(txtFirstName.getText(), txtLastName.getText(), txtAddress.getText(), 
						txtPhoneNumber.getText(), txtfaxNumber.getText(), txtOther.getText());
		    }
		});
		contentPane.add(btnAdd);
		
		JButton btnClose = new JButton(Local.getString("Close"));
		btnClose.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
				frame.dispose();
		    }
		});
		
		btnClose.setBounds(145, 325, 117, 29);
		contentPane.add(btnClose);

		EventQueue.invokeLater(new Runnable() {
		    public void run() {
		        try {
					frame.setVisible(true);
		        }catch(Exception e){
					new ExceptionDialog(e);
		        }
		    }
		});
		
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
	            KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Cancel"); //$NON-NLS-1$
	        getRootPane().getActionMap().put("Cancel", new AbstractAction(){ //$NON-NLS-1$
	            public void actionPerformed(ActionEvent e)
	            {
	                dispose();
	            }
	        });
	}
}
