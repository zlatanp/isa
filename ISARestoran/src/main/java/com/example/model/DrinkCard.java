package com.example.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "drink_cards")
public class DrinkCard implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6373415296066395638L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "dc_id", unique = true, nullable = false)
	private Long id;

	@Column(name = "name", unique = false, nullable = false)
	private String name;

	@Column(name = "description", unique = false, nullable = false)
	private String description;

	@Column(name = "price", unique = false, nullable = false)
	private int price;

	public DrinkCard() {

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
