package com.example.beans.restoran;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.example.beans.korisnici.Ponudjac;

@Entity
@Table(name="ponuda")
public class Ponuda implements Serializable {

	
	private static final long serialVersionUID = 2494611454273806107L;

	@Id
	@GeneratedValue
	@Column(name="id", nullable=false, unique=true)
	protected int id;
	
	@ManyToOne
	@JoinColumn(name="ponudjac", referencedColumnName="id", nullable=false)
	protected Ponudjac ponudjac;
	
	@Column(name="cena", nullable=false)
	protected double cena;
	
	@Column(name="poruka", nullable=true)
	protected String poruka;
	
	@Column(name="prihvacena", nullable=false)
	protected boolean prihvacena;
	
	// neki zahtev za ponude dodaj "KONKURS"
	
	public Ponuda() {
	}

	public Ponuda(Ponudjac ponudjac, double cena, String poruka, boolean prihvacena) {
		super();
		this.ponudjac = ponudjac;
		this.cena = cena;
		this.poruka = poruka;
		this.prihvacena = prihvacena;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Ponudjac getPonudjac() {
		return ponudjac;
	}

	public void setPonudjac(Ponudjac ponudjac) {
		this.ponudjac = ponudjac;
	}

	public double getCena() {
		return cena;
	}

	public void setCena(double cena) {
		this.cena = cena;
	}

	public String getPoruka() {
		return poruka;
	}

	public void setPoruka(String poruka) {
		this.poruka = poruka;
	}

	public boolean isPrihvacena() {
		return prihvacena;
	}

	public void setPrihvacena(boolean prihvacena) {
		this.prihvacena = prihvacena;
	}	
}
