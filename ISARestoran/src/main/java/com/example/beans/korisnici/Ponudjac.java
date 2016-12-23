package com.example.beans.korisnici;


import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.example.beans.restoran.Ponuda;
import com.example.enums.TipKorisnika;


@Entity
@Table(name = "ponudjac")
public class Ponudjac extends Korisnik{

	private static final long serialVersionUID = -1102935553098958449L;

	@OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.LAZY, mappedBy="ponudjac")
	protected Set<Ponuda> ponude;
	

	public Ponudjac() {
	}


	public Ponudjac(String ime, String prezime, String email, String password, TipKorisnika tipKorisnika) {
		super(ime, prezime, email, password, tipKorisnika);
	}


	public Set<Ponuda> getPonude() {
		return ponude;
	}


	public void setPonude(Set<Ponuda> ponude) {
		this.ponude = ponude;
	}


	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
	
	
}
