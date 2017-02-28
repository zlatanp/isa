package com.example.dto.restoran;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.beans.restoran.Narudzbina;
import com.example.beans.restoran.Poziv;
import com.example.beans.restoran.Rezervacija;


public class RezervacijaDTO {

	public int id;
	public Date vreme;
	public int trajanje;
	public String rezervisao;
	public List<PozivDTO> pozvaniPozivi = new ArrayList<PozivDTO>();
	public Set<Integer> stolovi = new HashSet<Integer>();
	public int restoran;
	public int broj_stolica;
	public List<String> pozvani = new ArrayList<String>();
	public Set<NarudzbinaDTO> narudzbine = new HashSet<NarudzbinaDTO>();
	
	public RezervacijaDTO() {};
	
	public RezervacijaDTO(Rezervacija r){
		this.id = r.getId();
		this.vreme = r.getDatum();
		this.trajanje = r.getTrajanje();
		this.rezervisao = r.getGost().getEmail();
		this.restoran = r.getRestoran().getId();
		this.broj_stolica = r.getBroj_stolica();
		if(r.getPozvani() != null){
			for(Poziv p : r.getPozvani()){
				this.pozvani.add(p.getGost().getEmail());
				this.pozvaniPozivi.add(new PozivDTO(p));
			}
		}
		if(r.getNarudzbine() != null){
			for(Narudzbina n : r.getNarudzbine()){
				this.narudzbine.add(new NarudzbinaDTO(n, null));
			}
		}
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getVreme() {
		return vreme;
	}
	public void setVreme(Date vreme) {
		this.vreme = vreme;
	}
	public int getTrajanje() {
		return trajanje;
	}
	public void setTrajanje(int trajanje) {
		this.trajanje = trajanje;
	}
	public String getRezervisao() {
		return rezervisao;
	}
	public void setRezervisao(String rezervisao) {
		this.rezervisao = rezervisao;
	}
	public List<PozivDTO> getPozvaniPozivi() {
		return pozvaniPozivi;
	}
	public void setPozvaniPozivi(List<PozivDTO> pozvaniPozivi) {
		this.pozvaniPozivi = pozvaniPozivi;
	}
	public Set<Integer> getStolovi() {
		return stolovi;
	}
	public void setStolovi(Set<Integer> stolovi) {
		this.stolovi = stolovi;
	}
	public int getRestoran() {
		return restoran;
	}
	public void setRestoran(int restoran) {
		this.restoran = restoran;
	}
	public int getBroj_stolica() {
		return broj_stolica;
	}
	public void setBroj_stolica(int broj_stolica) {
		this.broj_stolica = broj_stolica;
	}
	public List<String> getPozvani() {
		return pozvani;
	}
	public void setPozvani(List<String> pozvani) {
		this.pozvani = pozvani;
	}
	public Set<NarudzbinaDTO> getNarudzbine() {
		return narudzbine;
	}
	public void setNarudzbine(Set<NarudzbinaDTO> narudzbine) {
		this.narudzbine = narudzbine;
	}	
}
