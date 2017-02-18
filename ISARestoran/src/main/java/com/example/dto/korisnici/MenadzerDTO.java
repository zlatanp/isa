package com.example.dto.korisnici;

import com.example.beans.korisnici.MenadzerRestorana;
import com.example.enums.TipKorisnika;

public class MenadzerDTO extends KorisnikDTO{

	public int radi_u; // id-restorana gde radi

	public MenadzerDTO() {
		super();
	}

	public MenadzerDTO(MenadzerRestorana m) {
		super(m);
		this.radi_u = m.getRadi_u().getId();
	}

	public MenadzerDTO(String ime, String prezime, String email, String password, TipKorisnika tip) {
		super(ime, prezime, email, password, tip);	
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	public int getRadi_u() {
		return radi_u;
	}

	public void setRadi_u(int radi_u) {
		this.radi_u = radi_u;
	}

}
