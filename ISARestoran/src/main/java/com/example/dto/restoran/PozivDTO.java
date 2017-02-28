package com.example.dto.restoran;

import com.example.beans.restoran.Poziv;

public class PozivDTO {

	public int id;
	public String gost;
	public int rezervacija;
	public boolean prihvacen;
	
	public PozivDTO(){};
	
	public PozivDTO(Poziv p){
		this.id = p.getId();
		this.gost = p.getGost().getEmail();
		this.rezervacija = p.getRezervacija().getId();
		this.prihvacen = p.isPrihvacen();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGost() {
		return gost;
	}

	public void setGost(String gost) {
		this.gost = gost;
	}

	public int getRezervacija() {
		return rezervacija;
	}

	public void setRezervacija(int rezervacija) {
		this.rezervacija = rezervacija;
	}

	public boolean isPrihvacen() {
		return prihvacen;
	}

	public void setPrihvacen(boolean prihvacen) {
		this.prihvacen = prihvacen;
	}	
}
