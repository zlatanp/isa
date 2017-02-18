package com.example.dto.korisnici;

import com.example.beans.korisnici.Gost;
import com.example.enums.TipKorisnika;

public class GostDTO extends KorisnikDTO{

	private String hashCode;

	public GostDTO() {
		super();
	}

	public GostDTO(Gost g) {
		super(g);
		this.hashCode = g.getHashCode();
	}

	public GostDTO(String ime, String prezime, String email, String password, TipKorisnika tip) {
		super(ime, prezime, email, password, tip);
	}

	public String getHashCode() {
		return hashCode;
	}

	public void setHashCode(String hashCode) {
		this.hashCode = hashCode;
	}
}
