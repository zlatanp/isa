package com.example.beans.korisnici;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.example.beans.restoran.Restoran;
import com.example.enums.TipKorisnika;

@Entity
@Table(name="konobar")
public class Konobar extends Zaposleni {


	private static final long serialVersionUID = -2205722963544485944L;

	public Konobar() {	
	}

	public Konobar(String ime, String prezime, String email, String password, TipKorisnika tipKorisnika,
			Date datumRodjenja, String konfBr, String velObuce, Restoran radiU) {
		super(ime, prezime, email, password, tipKorisnika, datumRodjenja, konfBr, velObuce, radiU);
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
	
}
