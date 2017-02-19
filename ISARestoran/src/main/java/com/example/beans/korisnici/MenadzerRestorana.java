package com.example.beans.korisnici;


import javax.persistence.Entity;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.example.beans.restoran.Restoran;
import com.example.enums.TipKorisnika;


@Entity
@Table(name = "menadzer_restorana")
public class MenadzerRestorana extends Korisnik {


	private static final long serialVersionUID = 8527098881974515581L;
	
	@ManyToOne
	@JoinColumn(name="radi_u", referencedColumnName= "id")
	protected Restoran radi_u;
	

	public MenadzerRestorana() {
	}

	public MenadzerRestorana(String ime, String prezime, String email, String password, TipKorisnika tipKorisnika) {
		super(ime, prezime, email, password, tipKorisnika);
	}

	public MenadzerRestorana(String ime, String prezime, String email, String password, TipKorisnika tipKorisnika, Restoran restoran) {
		super(ime, prezime, email, password, tipKorisnika);
		this.radi_u = restoran;
	}
	
	public Restoran getRadi_u() {
		return radi_u;
	}

	public void setRadi_u(Restoran radi_u) {
		this.radi_u = radi_u;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}
}
