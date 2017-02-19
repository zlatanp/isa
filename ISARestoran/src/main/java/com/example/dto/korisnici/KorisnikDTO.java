package com.example.dto.korisnici;

import com.example.beans.korisnici.Korisnik;
import com.example.enums.TipKorisnika;

public abstract class KorisnikDTO {

	public int id;
	public String ime;
	public String prezime;
	public String email;
	public String password;
	public TipKorisnika tip;
	public byte[] slika;
	public boolean promenioLozinku = false;
	
	public KorisnikDTO(){}

	public KorisnikDTO(String ime, String prezime, String email, String password, TipKorisnika tip) {
		super();
		this.ime = ime;
		this.prezime = prezime;
		this.email = email;
		this.password = password;
		this.tip = tip;
	}
	
	public KorisnikDTO(Korisnik k){
		this.id = k.getId();
		this.email = k.getEmail();
		this.password = k.getPassword();
		this.ime = k.getIme();
		this.prezime = k.getPrezime();
		this.slika = k.getSlika();
		this.tip = k.getTipKorisnika();
		this.promenioLozinku = k.isPromenioLozinku();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public TipKorisnika getTip() {
		return tip;
	}

	public void setTip(TipKorisnika tip) {
		this.tip = tip;
	}

	public byte[] getSlika() {
		return slika;
	}

	public void setSlika(byte[] slika) {
		this.slika = slika;
	}

	public boolean isPromenioLozinku() {
		return promenioLozinku;
	}

	public void setPromenioLozinku(boolean promenioLozinku) {
		this.promenioLozinku = promenioLozinku;
	}

}
