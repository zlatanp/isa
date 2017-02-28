package com.example.beans.restoran;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.example.beans.korisnici.Gost;

@Entity
@Table(name="rezervacija")
public class Rezervacija implements Serializable{

	private static final long serialVersionUID = -8652828532535925862L;

	@Id
	@GeneratedValue
	@Column(name="id", nullable=false, unique=false)
	protected int id;
	
	@Column(name="vreme", nullable=false)
	protected Date datum;
	
	@Column(name="trajanje", nullable=false)
	protected int trajanje;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="rezervisao", nullable=false, referencedColumnName="id")
	protected Gost gost;
	
	@OneToMany(mappedBy="rezervacija", fetch=FetchType.EAGER, cascade={CascadeType.ALL})
	protected Set<Poziv> pozvani;
	
	@OneToOne(mappedBy="rezervacija")
	protected Poseta poseta;
	
	@ManyToMany
	@JoinColumn(name="sto_rezervacija")
	protected Set<Sto> stolovi;
	
	@ManyToOne
	@JoinColumn(name="restoran")
	protected Restoran restoran;
	
	@Column(name="broj_stolica")
	protected int broj_stolica;
	
	@OneToMany(cascade={CascadeType.ALL},fetch=FetchType.LAZY,mappedBy="rezervacija")
	protected Set<Narudzbina> narudzbine;
	
	public Rezervacija() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public int getTrajanje() {
		return trajanje;
	}

	public void setTrajanje(int trajanje) {
		this.trajanje = trajanje;
	}

	public Gost getGost() {
		return gost;
	}

	public void setGost(Gost gost) {
		this.gost = gost;
	}

	public Set<Poziv> getPozvani() {
		return pozvani;
	}

	public void setPozvani(Set<Poziv> pozvani) {
		this.pozvani = pozvani;
	}

	public Poseta getPoseta() {
		return poseta;
	}

	public void setPoseta(Poseta poseta) {
		this.poseta = poseta;
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

	public int getBroj_stolica() {
		return broj_stolica;
	}

	public void setBroj_stolica(int broj_stolica) {
		this.broj_stolica = broj_stolica;
	}

	public Set<Narudzbina> getNarudzbine() {
		return narudzbine;
	}

	public void setNarudzbine(Set<Narudzbina> narudzbine) {
		this.narudzbine = narudzbine;
	}
}
