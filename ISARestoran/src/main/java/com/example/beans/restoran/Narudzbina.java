package com.example.beans.restoran;

import java.io.Serializable;
import java.util.HashSet;
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
import javax.persistence.Table;

import com.example.beans.korisnici.Gost;

@Entity
@Table(name="narudzbina")
public class Narudzbina implements Serializable{

	private static final long serialVersionUID = -1277484736132783402L;

	@Id
	@GeneratedValue
	@Column(name="id",nullable=false,unique=true)
	protected int id;
	
	@ManyToOne
	@JoinColumn(name="rezervacija", nullable=true)
	protected Rezervacija rezervacija;
	
	@ManyToOne
	@JoinColumn(name="gost", nullable=true)
	protected Gost gost;
	
	@OneToMany(mappedBy="narudzbinaStavkePica", fetch=FetchType.EAGER, cascade={CascadeType.ALL})
	protected Set<StavkaPica> pica = new HashSet<StavkaPica>();
	
	@OneToMany(mappedBy="narudzbinaStavkeJela", fetch=FetchType.EAGER, cascade={CascadeType.ALL})
	protected Set<StavkaJela> jela = new HashSet<StavkaJela>();
	
	public Narudzbina() {}

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

	public Gost getGost() {
		return gost;
	}

	public void setGost(Gost gost) {
		this.gost = gost;
	}

	public Set<StavkaPica> getPica() {
		return pica;
	}

	public void setPica(Set<StavkaPica> pica) {
		this.pica = pica;
	}

	public Set<StavkaJela> getJela() {
		return jela;
	}

	public void setJela(Set<StavkaJela> jela) {
		this.jela = jela;
	}
}
