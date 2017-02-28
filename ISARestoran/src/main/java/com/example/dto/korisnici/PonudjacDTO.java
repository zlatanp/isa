package com.example.dto.korisnici;

import com.example.beans.korisnici.Korisnik;
import com.example.enums.TipKorisnika;

public class PonudjacDTO extends KorisnikDTO{

	public PonudjacDTO() {
		super();
	}

	public PonudjacDTO(Korisnik k) {
		super(k);
	}

	public PonudjacDTO(String ime, String prezime, String email, String password, TipKorisnika tip) {
		super(ime, prezime, email, password, tip);
	}
}
