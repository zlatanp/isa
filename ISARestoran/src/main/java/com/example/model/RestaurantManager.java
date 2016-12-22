package com.example.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Klasa koja reprezentuje menadzera restorana.
 */

@Entity
@Table(name = "restaurantManagers")
public class RestaurantManager {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "restaurantManager_id", unique = true, nullable = false)
	private Long id;

	@Column(name = "username", unique = false, nullable = false)
	private String username;

	@Column(name = "password", unique = false, nullable = false)
	private String password;

	@Column(name = "first_name", unique = false, nullable = false)
	private String firstName;

	@Column(name = "last_name", unique = false, nullable = false)
	private String lastname;

	@Column(name = "email", nullable = false)
	private String email;

	public RestaurantManager(Long id, String username, String password, String firstName, String lastname,
			String email) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastname = lastname;
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

}
