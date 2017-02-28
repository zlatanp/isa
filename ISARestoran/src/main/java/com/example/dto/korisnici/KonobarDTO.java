package com.example.dto.korisnici;



import java.util.Date;

import com.example.beans.korisnici.Konobar;
import com.example.beans.restoran.Ocena;
import com.example.enums.TipKorisnika;

public class KonobarDTO extends ZaposleniDTO{

	private double ocena;

	public KonobarDTO() {
		super();
	}

	public KonobarDTO(String ime, String prezime, String email, String password, TipKorisnika tip, String konfBr,
			String velOb, Date datumRodj, int radiU) {
		super(ime, prezime, email, password, tip, konfBr, velOb, datumRodj, radiU);
	}

	public KonobarDTO(Konobar k) {
		super(k);
		racunajOcenu(k);
	}

	private void racunajOcenu(Konobar k){
		// ovde dodaj ocenu;
		int brOcena = 0;
		for(Ocena o : k.getRadi_u().getOcene()){
			if(o.getKonobar().equals(k)){
				this.ocena += o.getOcenaUsluge();
				brOcena++;
			}
		}
		if(brOcena!=0){
			this.ocena = this.ocena/brOcena; // prosek;
		}
	}
	public double getOcena() {
		return ocena;
	}

	public void setOcena(double ocena) {
		this.ocena = ocena;
	}
}
