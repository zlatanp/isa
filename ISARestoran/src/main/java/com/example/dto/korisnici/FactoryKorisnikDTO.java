package com.example.dto.korisnici;

import com.example.beans.korisnici.Gost;
import com.example.beans.korisnici.Korisnik;
import com.example.beans.korisnici.MenadzerRestorana;
import com.example.beans.korisnici.MenadzerSistema;
import com.example.enums.TipKorisnika;

public class FactoryKorisnikDTO {

	public static KorisnikDTO makeKorisnikDTO(Korisnik k){
		if(k == null){
			return null;
		}
		if(k.getTipKorisnika().equals(TipKorisnika.GOST)){
			return new GostDTO((Gost)k);
		}
		if(k.getTipKorisnika().equals(TipKorisnika.MENADZERSISTEMA)){
			return new MenadzerSistemaDTO((MenadzerSistema)k);
		}
		if(k.getTipKorisnika().equals(TipKorisnika.MENADZERRESTORANA)){
			return new MenadzerDTO((MenadzerRestorana)k);
		}
		return null;
	}
}
