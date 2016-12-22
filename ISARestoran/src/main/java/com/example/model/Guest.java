package com.example.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

//za bazu
@Entity
public class Guest {

	// surogat kljuc
	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "lastname", nullable = false)
	private String lastname;
	
	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "email", unique = true, nullable = false)
	private String email;

	@Column(name = "active", nullable = false)
	private boolean active;

	// @OneToMany(mappedBy = "guest", fetch = FetchType.LAZY, cascade =
	// CascadeType.REFRESH)
	private ArrayList<Guest> friends = new ArrayList<Guest>();
	
	

	public Guest() {
	}

	public Guest(String name, String lastname, String password, String email) {
		super();
		this.name = name;
		this.lastname = lastname;
		this.password = password;
		this.email = email;
		this.setFriends(new ArrayList<Guest>());
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
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

	public ArrayList<Guest> getFriends() {
		return friends;
	}

	public void setFriends(ArrayList<Guest> friends) {
		this.friends = friends;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

}
