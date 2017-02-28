package com.example.beans.restoran;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="namirnica")
public class Namirnica implements Serializable{


	private static final long serialVersionUID = -3524463999383669393L;

	@Id
	@GeneratedValue
	@Column(name="id", nullable = false, unique = true)
	protected int id;
	
	@Column(name="naziv_namirnice", nullable = false)
	protected String naziv;
	
	@Column(name="kolicina_namirnice", nullable = false)
	protected String kolicina;
	
	public Namirnica() {}

	public Namirnica(String naziv, String kolicina) {
		super();
		this.naziv = naziv;
		this.kolicina = kolicina;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getKolicina() {
		return kolicina;
	}

	public void setKolicina(String kolicina) {
		this.kolicina = kolicina;
	}
}
