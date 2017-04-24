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
		setBounds(100, 100, 828, 621);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtFirstName = new JTextField();
		txtFirstName.setBounds(238, 78, 130, 26);
		contentPane.add(txtFirstName);
		txtFirstName.setColumns(10);
		
		txtLastName = new JTextField();
		txtLastName.setColumns(10);
		txtLastName.setBounds(238, 116, 130, 26);
		contentPane.add(txtLastName);
		
		JLabel lblFirstName = new JLabel(Local.getString("First Name"));
		lblFirstName.setBounds(142, 83, 84, 16);
		contentPane.add(lblFirstName);
		
		JLabel lblLastName = new JLabel(Local.getString("Last Name"));
		lblLastName.setBounds(142, 121, 84, 16);
		contentPane.add(lblLastName);
		
		txtAddress = new JTextField();
		txtAddress.setColumns(10);
		txtAddress.setBounds(238, 170, 130, 26);
		contentPane.add(txtAddress);
		
		JLabel lblAddress = new JLabel(Local.getString("Address"));
		lblAddress.setBounds(142, 175, 84, 16);
		contentPane.add(lblAddress);
		
		txtPhoneNumber = new JTextField();
		txtPhoneNumber.setColumns(10);
		txtPhoneNumber.setBounds(238, 214, 130, 26);
		contentPane.add(txtPhoneNumber);
		
		JLabel lblPhoneNumber = new JLabel(Local.getString("Phone Number"));
		lblPhoneNumber.setBounds(125, 219, 101, 16);
		contentPane.add(lblPhoneNumber);
		
		txtfaxNumber = new JTextField();
		txtfaxNumber.setColumns(10);
		txtfaxNumber.setBounds(238, 268, 130, 26);
		contentPane.add(txtfaxNumber);
		
		JLabel lblFaxNumber = new JLabel(Local.getString("Fax Number"));
		lblFaxNumber.setBounds(142, 273, 84, 16);
		contentPane.add(lblFaxNumber);
		
		txtOther = new JTextField();
		txtOther.setColumns(10);
		txtOther.setBounds(238, 323, 130, 26);
		contentPane.add(txtOther);
		
		JLabel lblOther = new JLabel(Local.getString("other"));
		lblOther.setBounds(142, 328, 84, 16);
		contentPane.add(lblOther);
		
		JButton btnAdd = new JButton(Local.getString("Add"));
		btnAdd.setBounds(116, 479, 117, 29);
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
		btnClose.setBounds(282, 479, 117, 29);
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
