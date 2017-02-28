package com.example.dto.restoran;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.example.beans.restoran.Narudzbina;
import com.example.beans.restoran.StavkaJela;
import com.example.beans.restoran.StavkaPica;


public class NarudzbinaDTO {

	public int id;
	public int rezervacija;
	public String gost; 
	public Set<StavkaJelaDTO> jela = new HashSet<StavkaJelaDTO>();
	public Set<StavkaPicaDTO> pica = new HashSet<StavkaPicaDTO>();
	
	public NarudzbinaDTO() {}
	
	public NarudzbinaDTO(Narudzbina n, Date datum){
		this.id = n.getId();
		this.rezervacija = n.getRezervacija().getId();
		for(StavkaPica s : n.getPica()){
			this.pica.add(new StavkaPicaDTO(s, datum));
		}
		for(StavkaJela s : n.getJela()){
			this.jela.add(new StavkaJelaDTO(s, datum));
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRezervacija() {
		return rezervacija;
	}

	public void setRezervacija(int rezervacija) {
		this.rezervacija = rezervacija;
	}

	public String getGost() {
		return gost;
	}

	public void setGost(String gost) {
		this.gost = gost;
	}

	public Set<StavkaJelaDTO> getJela() {
		return jela;
	}

	public void setJela(Set<StavkaJelaDTO> jela) {
		this.jela = jela;
	}

	public Set<StavkaPicaDTO> getPica() {
		return pica;
	}

	public void setPica(Set<StavkaPicaDTO> pica) {
		this.pica = pica;
	}
}
