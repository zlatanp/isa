package com.example.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Klasa koja reprezentuje jednog ponudjaca.
 */
@Entity
@Table(name = "bidders")
public class Bidder {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bidder_id", unique = true, nullable = false)
	private Long id;

	@Column(name = "username", unique = false, nullable = false)
	private String username;

	@Column(name = "password", unique = false, nullable = false)
	private String password;

	@Column(name = "first_name", unique = false, nullable = false)
	private String firstName;

	@Column(name = "last_name", unique = false, nullable = false)
	private String lastname;

}
