package classes;

import java.util.ArrayList;

/*This class Person contains attribute for person object. It has two constructors since some person
 * may or may not have an email*/
public class Person {
	
	private int personID;
	private String personCode;
	private String firstName;
	private String lastName;
	private Address address;
	private ArrayList<String> email;
	

	

	public Person(int personID, String personCode, String firstName, String lastName, Address address) {
		super();
		this.personID = personID;
		this.personCode = personCode;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
	}


	public Person(int personID, String personCode, String firstName, String lastName, Address address,
			ArrayList<String> email) {
		super();
		this.personID = personID;
		this.personCode = personCode;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.email = email;
	}

	public int getPersonID() {
		return personID;
	}


	public void setPersonID(int personID) {
		this.personID = personID;
	}


	public void setEmail(ArrayList<String> email) {
		this.email = email;
	}


	public String getPersonCode() {
		return personCode;
	}

	public void setPersonCode(String personCode) {
		this.personCode = personCode;
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

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}


	public ArrayList<String> getEmail() {
		return email;
	}


	public String getName() {
		return this.lastName+", "+this.firstName;
	}


	@Override
	public String toString() {
		return "Person [personID=" + personID + ", personCode=" + personCode + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", address=" + address.toString() + ", email=" + email + "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((personCode == null) ? 0 : personCode.hashCode());
		result = prime * result + personID;
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (personCode == null) {
			if (other.personCode != null)
				return false;
		} else if (!personCode.equals(other.personCode))
			return false;
		if (personID != other.personID)
			return false;
		return true;
	}


	
	
	
	
}
