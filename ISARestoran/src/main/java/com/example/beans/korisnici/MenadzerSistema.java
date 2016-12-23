package com.example.beans.korisnici;


import javax.persistence.Entity;

import javax.persistence.Table;

import com.example.enums.TipKorisnika;

@Entity
@Table(name = "menadzer_sistema")
public class MenadzerSistema extends Korisnik {

	private static final long serialVersionUID = -886299858996492042L;

	
	public MenadzerSistema() {

	}


	public MenadzerSistema(String ime, String prezime, String email, String password, TipKorisnika tipKorisnika) {
		super(ime, prezime, email, password, tipKorisnika);
	}


	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
	
	

}
