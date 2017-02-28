package com.example.beans.restoran;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name="poseta")
public class Poseta implements Serializable {

	private static final long serialVersionUID = -101815491497840048L;

	@Id
	@GeneratedValue
	@Column(name="id", nullable=false, unique=true)
	protected int id;
	
	@OneToOne
	@JoinColumn(name="rezervacija", referencedColumnName="id", nullable=true)
	protected Rezervacija rezervacija;
	
	@Column(name="vremeDolaska", nullable=false)
	protected Date vremeDolaska;
	
	@Column(name="vremeOdlaska")
	protected Date vremeOdlaska;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinColumn(name="sto_poseta")
	protected Set<Sto> stolovi;
	
	@ManyToOne
	@JoinColumn(name="restoran",referencedColumnName="id", nullable=false)
	protected Restoran restoran;
	
	@OneToOne
	@JoinColumn(name="racun")
	protected Racun racun;
	
	public Poseta() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Rezervacija getRezervacija() {
		return rezervacija;
	}

	public void setRezervacija(Rezervacija rezervacija) {
		this.rezervacija = rezervacija;
	}

	public Date getVremeDolaska() {
		return vremeDolaska;
	}

	public void setVremeDolaska(Date vremeDolaska) {
		this.vremeDolaska = vremeDolaska;
	}

	public Date getVremeOdlaska() {
		return vremeOdlaska;
	}

	public void setVremeOdlaska(Date vremeOdlaska) {
		this.vremeOdlaska = vremeOdlaska;
	}

	public Set<Sto> getStolovi() {
		return stolovi;
	}

	public void setStolovi(Set<Sto> stolovi) {
		this.stolovi = stolovi;
	}

	public Restoran getRestoran() {
		return restoran;
	}

	public void setRestoran(Restoran restoran) {
		this.restoran = restoran;
	}

	public Racun getRacun() {
		return racun;
	}

	public void setRacun(Racun racun) {
		this.racun = racun;
	}
}
