package com.example.dto.restoran;

import com.example.beans.restoran.Smena;

public class SmenaDTO {

	public int id;
	public String naziv;
	public String vremeOD;
	public String vremeDO;
	
	public SmenaDTO() {};
	
	public SmenaDTO(Smena s){
		this.id = s.getId();
		this.naziv = s.getNaziv();
		this.vremeDO = s.getDo_vreme();
		this.vremeOD = s.getOd_vreme();
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

	public String getVremeOD() {
		return vremeOD;
	}

	public void setVremeOD(String vremeOD) {
		this.vremeOD = vremeOD;
	}

	public String getVremeDO() {
		return vremeDO;
	}

	public void setVremeDO(String vremeDO) {
		this.vremeDO = vremeDO;
	}
}
