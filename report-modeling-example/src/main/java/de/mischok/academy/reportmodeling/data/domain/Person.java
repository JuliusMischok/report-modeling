package de.mischok.academy.reportmodeling.data.domain;

import static java.util.Objects.requireNonNull;

/**
 * Represents a person.
 */
public class Person {
	private final String firstname;
	private final String lastname;
	private final String email;
	
	private Person(String firstname, String lastname, String email) {
		requireNonNull(firstname);
		requireNonNull(lastname);
		requireNonNull(email);

		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
	}
	
	/**
	 * Generator for a person.
	 * @param firstname the firstname
	 * @param lastname the lastname
	 * @param email the email address
	 * @return the generated person
	 */
	public static Person of(String firstname, String lastname, String email) {
		return new Person(firstname, lastname, email);
	}

	/**
	 * @return the firstname
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * @return the lastname
	 */
	public String getLastname() {
		return lastname;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
}
