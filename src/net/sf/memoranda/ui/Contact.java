package net.sf.memoranda.ui;

import java.util.UUID;

import net.sf.memoranda.util.Util;

// TODO: Auto-generated Javadoc
/**
 * The Class Contact.
 */
public class Contact {
	
	/** The first name. */
	private String firstName;
	
	/** The last name. */
	private String lastName;
	
	/** The address. */
	private String address;
	
	/** The phone number. */
	private String phoneNumber;
	
	/** The fax number. */
	private String faxNumber;
	
	/** The other number. */
	private String otherNumber;
	
	/**
	 * Instantiates a new contact.
	 *
	 * @param firstName the first name
	 * @param lastName the last name
	 * @param address the address
	 * @param phoneNumber the phone number
	 * @param faxNumber the fax number
	 * @param otherNumber the other number
	 */
	public Contact(String firstName, String lastName, String address, String phoneNumber, String faxNumber, String otherNumber){
		setFirstName(firstName);
		setLastName(lastName);
		setAddress(address);
		setPhoneNumber(phoneNumber);
		setFaxNumber(faxNumber);
		setOtherNumber(otherNumber);
	}
	
	/**
	 * Library file.
	 *
	 * @return the string
	 */
	public static String LibraryFile(){
		return Util.getEnvDir() + "contacts.csv";
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		return getFirstName() + "    "  + getLastName() + "    " + getPhoneNumber() + "    " + getAddress();
	}
	
	/**
	 * Gets the first name.
	 *
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name.
	 *
	 * @param firstName the new first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the last name.
	 *
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the last name.
	 *
	 * @param lastName the new last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets the address.
	 *
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Sets the address.
	 *
	 * @param address the new address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Gets the phone number.
	 *
	 * @return the phone number
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * Sets the phone number.
	 *
	 * @param phoneNumber the new phone number
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * Gets the fax number.
	 *
	 * @return the fax number
	 */
	public String getFaxNumber() {
		return faxNumber;
	}

	/**
	 * Sets the fax number.
	 *
	 * @param faxNumber the new fax number
	 */
	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	/**
	 * Gets the other number.
	 *
	 * @return the other number
	 */
	public String getOtherNumber() {
		return otherNumber;
	}

	/**
	 * Sets the other number.
	 *
	 * @param otherNumber the new other number
	 */
	public void setOtherNumber(String otherNumber) {
		this.otherNumber = otherNumber;
	}
}
