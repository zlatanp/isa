package com.example.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "menus")
public class Menu implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4619284858502679187L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "menu_id", unique = true, nullable = false)
	private Long id;

	@Column(name = "name", unique = false, nullable = false)
	private String name;

	@Column(name = "description", unique = false, nullable = false)
	private String description;

	@Column(name = "price", unique = false, nullable = false)
	private int price;

	public Menu() {

	}

	public Menu(Long id, String name, String description, int price) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

}
