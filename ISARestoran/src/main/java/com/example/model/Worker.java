package com.example.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Klasa koja reprezentuje radnika.
 */

@Entity
@Table(name = "workers")
public class Worker {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "worker_id", unique = true, nullable = false)
	private Long id;

	@Column(name = "username", unique = false, nullable = false)
	private String username;

	@Column(name = "password", unique = false, nullable = false)
	private String password;

	@Column(name = "first_name", unique = false, nullable = false)
	private String firstName;

	@Column(name = "last_name", unique = false, nullable = false)
	private String lastname;

	@Column(name = "date_birth", unique = false, nullable = false)
	private Date dateBirth;

	@Column(name = "dress_size", unique = false, nullable = false)
	private int dressSize;

	@Column(name = "shoe_size", unique = false, nullable = false)
	private int shoeSize;

	@Enumerated(EnumType.STRING)
	private WorkerType type;

	public Worker(Long id, String username, String password, String firstName, String lastname, Date dateBirth,
			int dressSize, int shoeSize, WorkerType type) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastname = lastname;
		this.dateBirth = dateBirth;
		this.dressSize = dressSize;
		this.shoeSize = shoeSize;
		this.type = type;
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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Date getDateBirth() {
		return dateBirth;
	}

	public void setDateBirth(Date dateBirth) {
		this.dateBirth = dateBirth;
	}

	public int getDressSize() {
		return dressSize;
	}

	public void setDressSize(int dressSize) {
		this.dressSize = dressSize;
	}

	public int getShoeSize() {
		return shoeSize;
	}

	public void setShoeSize(int shoeSize) {
		this.shoeSize = shoeSize;
	}

	public WorkerType getType() {
		return type;
	}

	public void setType(WorkerType type) {
		this.type = type;
	}

}
