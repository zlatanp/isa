package com.example.beans.restoran;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.example.beans.korisnici.Gost;

@Entity
@Table
public class Poziv implements Serializable{

	private static final long serialVersionUID = 3740454736611246281L;

	@Id
	@GeneratedValue
	@Column(name="id", nullable=false, unique= true)
	private int id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="gost", nullable=false, referencedColumnName="id")
	private Gost gost;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="rezervacija", nullable=false, referencedColumnName="id")
	private Rezervacija rezervacija;
	
	@Column(name="prihvacen", nullable=false)
	private boolean prihvacen;
	
	
	public Poziv(){}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Gost getGost() {
		return gost;
	}

	public void setGost(Gost gost) {
		this.gost = gost;
	}

	public Rezervacija getRezervacija() {
		return rezervacija;
	}

	public void setRezervacija(Rezervacija rezervacija) {
		this.rezervacija = rezervacija;
	}

	public boolean isPrihvacen() {
		return prihvacen;
	}

	public void setPrihvacen(boolean prihvacen) {
		this.prihvacen = prihvacen;
	}
}
