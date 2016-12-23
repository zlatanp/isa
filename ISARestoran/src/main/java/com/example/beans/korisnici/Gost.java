package com.example.beans.korisnici;


import javax.persistence.Entity;
import javax.persistence.Table;

import com.example.enums.TipKorisnika;


@Entity
@Table(name= "gost")
public class Gost extends Korisnik{

	private static final long serialVersionUID = -7179926090706377976L;

	public Gost() {
	}
	
	public Gost(String ime, String prezime, String email, String password, TipKorisnika tipKorisnika) {
		super(ime, prezime, email, password, tipKorisnika);
	}


}
