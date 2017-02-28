package com.example.dto.restoran;

import com.example.beans.restoran.Ponuda;

public class PonudaDTO {

	public int id;
	public String ponudjac;
	public String restoran;
	public String poruka;
	public boolean jestePrihvacena;
	public double cena;
	public int ponudjacId;
	public int konkursId;
	
	public PonudaDTO() {}

	
	public PonudaDTO(int konkursId, double cena, String poruka){
		this.konkursId = konkursId;
		this.cena = cena;
		this.poruka = poruka;
	}
	
	public PonudaDTO(Ponuda p){
		this.id = p.getId();
		this.ponudjac = p.getPonudjac().getIme() + " " + p.getPonudjac().getPrezime();
		this.ponudjacId = p.getPonudjac().getId();
		this.cena = p.getCena();
		this.jestePrihvacena = p.isPrihvacena();
		this.poruka = p.getPoruka();
		this.konkursId = p.getZaKonkurs().getId();
		this.restoran = p.getZaKonkurs().getRestoran().getNaziv();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPonudjac() {
		return ponudjac;
	}

	public void setPonudjac(String ponudjac) {
		this.ponudjac = ponudjac;
	}

	public String getRestoran() {
		return restoran;
	}

	public void setRestoran(String restoran) {
		this.restoran = restoran;
	}

	public String getPoruka() {
		return poruka;
	}

	public void setPoruka(String poruka) {
		this.poruka = poruka;
	}

	public boolean isJestePrihvacena() {
		return jestePrihvacena;
	}

	public void setJestePrihvacena(boolean jestePrihvacena) {
		this.jestePrihvacena = jestePrihvacena;
	}

	public double getCena() {
		return cena;
	}

	public void setCena(double cena) {
		this.cena = cena;
	}

	public int getPonudjacId() {
		return ponudjacId;
	}

	public void setPonudjacId(int ponudjacId) {
		this.ponudjacId = ponudjacId;
	}

	public int getKonkursId() {
		return konkursId;
	}

	public void setKonkursId(int konkursId) {
		this.konkursId = konkursId;
	}
}
