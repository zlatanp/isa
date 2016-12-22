package com.example.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "restaurants")
public class Restaurant implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3991673638215578988L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "restaurant_id", unique = true, nullable = false)
	private Long id;

	@Column(name = "name", unique = false, nullable = false)
	private String name;

	@Column(name = "restaurant_type", unique = false, nullable = false)
	private RestaurantType restaurantType;

	// @Column(name = "menu", unique = false, nullable = false)
	@OneToOne(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	private Menu menu;

	// @Column(name = "drink_card", unique = false, nullable = false)
	@OneToOne(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	private DrinkCard drinkCard;

	// konfiguracija sedenja

	public Restaurant() {

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

	public RestaurantType getRestaurantType() {
		return restaurantType;
	}

	public void setRestaurantType(RestaurantType restaurantType) {
		this.restaurantType = restaurantType;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public DrinkCard getDrinkCard() {
		return drinkCard;
	}

	public void setDrinkCard(DrinkCard drinkCard) {
		this.drinkCard = drinkCard;
	}

}
