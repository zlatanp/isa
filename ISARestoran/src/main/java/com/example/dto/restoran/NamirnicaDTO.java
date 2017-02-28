package com.example.dto.restoran;

import com.example.beans.restoran.Namirnica;

public class NamirnicaDTO {

	public int id;
	public String naziv;
	public String kolicina;
	
	public NamirnicaDTO() {}

	public NamirnicaDTO(String naziv, String kolicina){
		super();
		this.naziv = naziv;
		this.kolicina = kolicina;
	}
	
	
	public NamirnicaDTO(Namirnica n){
		this.id = n.getId();
		this.naziv = n.getNaziv();
		this.kolicina = n.getKolicina();
	}
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getKolicina() {
		return kolicina;
	}

	public void setKolicina(String kolicina) {
		this.kolicina = kolicina;
	}
	
	
}
