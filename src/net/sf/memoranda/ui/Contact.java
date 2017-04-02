package net.sf.memoranda.ui;

import java.util.UUID;

import net.sf.memoranda.util.Util;

public class Contact {
	
	private String firstName;
	private String lastName;
	private String address;
	private String phoneNumber;
	private String faxNumber;
	private String otherNumber;
	
	public Contact(String firstName, String lastName, String address, String phoneNumber, String faxNumber, String otherNumber){
		setFirstName(firstName);
		setLastName(lastName);
		setAddress(address);
		setPhoneNumber(phoneNumber);
		setFaxNumber(faxNumber);
		setOtherNumber(otherNumber);
	}
	
	public static String LibraryFile(){
		return Util.getEnvDir() + "contacts.csv";
	}
	
	public String toString(){
		return getFirstName() + "    "  + getLastName() + "    " + getPhoneNumber() + "    " + getAddress();
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getFaxNumber() {
		return faxNumber;
	}

	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	public String getOtherNumber() {
		return otherNumber;
	}

	public void setOtherNumber(String otherNumber) {
		this.otherNumber = otherNumber;
	}
}
