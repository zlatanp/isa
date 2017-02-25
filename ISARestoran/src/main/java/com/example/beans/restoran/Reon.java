package com.example.beans.restoran;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.example.beans.korisnici.Konobar;

public class Reon implements Serializable{


	private static final long serialVersionUID = 208409717257631528L;

	@Id
	@GeneratedValue
	@Column(name="id", nullable=false, unique=true)
	protected int id;
	
	
	@ManyToOne
	@JoinColumn(name="restoran", referencedColumnName="id", nullable=false)
	protected Restoran restoran;
	
	@ManyToOne
	@JoinColumn(name="konobar", referencedColumnName="id")
	protected Konobar konobar;
	
	@ManyToMany
	@JoinColumn(name="stoReon")
	protected Set<Sto> stolovi = new HashSet<Sto>();

	
	public Reon() {}
	
	
	public Reon(Restoran restoran, Konobar konobar, Set<Sto> stolovi) {
		super();
		this.restoran = restoran;
		this.konobar = konobar;
		this.stolovi = stolovi;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Restoran getRestoran() {
		return restoran;
	}

	public void setRestoran(Restoran restoran) {
		this.restoran = restoran;
	}

	public Konobar getKonobar() {
		return konobar;
	}

	public void setKonobar(Konobar konobar) {
		this.konobar = konobar;
	}

	public Set<Sto> getStolovi() {
		return stolovi;
	}

	public void setStolovi(Set<Sto> stolovi) {
		this.stolovi = stolovi;
	}
}
