package com.example.dto.restoran;

import java.util.Date;

import com.example.beans.restoran.StavkaPica;
import com.example.dto.korisnici.SankerDTO;

public class StavkaPicaDTO {

	public int id;
	public PiceDTO pice;
	public int kolicina;
	public SankerDTO spremio;
	public boolean spremljeno;
	public boolean rezervacija;
	public int verzija;
	
	public StavkaPicaDTO() {}

	public StavkaPicaDTO(StavkaPica s, Date datum){
		this.id = s.getId();
		if(s.getPice() != null){
			this.pice = new PiceDTO(s.getPice(), datum);
		}
		if(s.getSpremio() != null){
			this.spremio = new SankerDTO(s.getSpremio());
		}
		this.kolicina = s.getKolicina();
		this.spremljeno = s.isSpremljeno();
		this.verzija = s.getVersion();
	}
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public PiceDTO getPice() {
		return pice;
	}

	public void setPice(PiceDTO pice) {
		this.pice = pice;
	}

	public int getKolicina() {
		return kolicina;
	}

	public void setKolicina(int kolicina) {
		this.kolicina = kolicina;
	}

	public SankerDTO getSpremio() {
		return spremio;
	}

	public void setSpremio(SankerDTO spremio) {
		this.spremio = spremio;
	}

	public boolean isSpremljeno() {
		return spremljeno;
	}

	public void setSpremljeno(boolean spremljeno) {
		this.spremljeno = spremljeno;
	}

	public boolean isRezervacija() {
		return rezervacija;
	}

	public void setRezervacija(boolean rezervacija) {
		this.rezervacija = rezervacija;
	}

	public int getVerzija() {
		return verzija;
	}

	public void setVerzija(int verzija) {
		this.verzija = verzija;
	}
}
