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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.example.beans.korisnici.MenadzerRestorana;

@Entity
@Table(name="konkurs")
public class Konkurs implements Serializable{

	private static final long serialVersionUID = 6404265265116366605L;

	@Id
	@GeneratedValue
	@Column(name="id", nullable = false, unique=true)
	protected int id;
	
	@ManyToOne
	@JoinColumn(name="restoran", nullable=false)
	protected Restoran restoran;
	
	@ManyToOne
	@JoinColumn(name= "raspisao", nullable=false)
	protected MenadzerRestorana koJeRaspisao;
	
	@Column(name="datum_objave", nullable=false)
	protected Date datumObjave;
	
	@Column(name="datum_zavrsetka", nullable=false)
	protected Date datumZavrsetka;
	
	@OneToMany(cascade={CascadeType.ALL}, fetch= FetchType.LAZY, mappedBy="za_konkurs")
	protected Set<Ponuda> ponude;
	
	@OneToOne
	@JoinColumn(name="prihvacena_ponuda", nullable=true)
	protected Ponuda prihvacenaPonuda;
	
	@OneToMany(fetch=FetchType.LAZY)
	@JoinColumn(name="namirnica_konkurs")
	protected Set<Namirnica> namirnice;
	
	public Konkurs(){}

	public Konkurs(Restoran restoran, MenadzerRestorana koJeRaspisao, 
			Set<Namirnica> namirnice, Date datumObjave, Date datumZavrsetka) {
		super();
		this.restoran = restoran;
		this.koJeRaspisao = koJeRaspisao;
		this.datumObjave = datumObjave;
		this.datumZavrsetka = datumZavrsetka;
		this.namirnice = namirnice;
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

	public MenadzerRestorana getKoJeRaspisao() {
		return koJeRaspisao;
	}

	public void setKoJeRaspisao(MenadzerRestorana koJeRaspisao) {
		this.koJeRaspisao = koJeRaspisao;
	}

	public Date getDatumObjave() {
		return datumObjave;
	}

	public void setDatumObjave(Date datumObjave) {
		this.datumObjave = datumObjave;
	}

	public Date getDatumZavrsetka() {
		return datumZavrsetka;
	}

	public void setDatumZavrsetka(Date datumZavrsetka) {
		this.datumZavrsetka = datumZavrsetka;
	}

	public Set<Ponuda> getPonude() {
		return ponude;
	}

	public void setPonude(Set<Ponuda> ponude) {
		this.ponude = ponude;
	}

	public Ponuda getPrihvacenaPonuda() {
		return prihvacenaPonuda;
	}

	public void setPrihvacenaPonuda(Ponuda prihvacenaPonuda) {
		this.prihvacenaPonuda = prihvacenaPonuda;
	}

	public Set<Namirnica> getNamirnice() {
		return namirnice;
	}

	public void setNamirnice(Set<Namirnica> namirnice) {
		this.namirnice = namirnice;
	}
	
}
