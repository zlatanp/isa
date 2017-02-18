package com.example.dto.korisnici;

import com.example.beans.korisnici.MenadzerSistema;
import com.example.enums.TipKorisnika;

public class MenadzerSistemaDTO extends KorisnikDTO{

	public MenadzerSistemaDTO() {
		super();
	}

	public MenadzerSistemaDTO(String ime, String prezime, String email, String password, TipKorisnika tip) {
		super(ime, prezime, email, password, tip);
	}

	public MenadzerSistemaDTO(MenadzerSistema m) {
		super(m);
	}

	
}
