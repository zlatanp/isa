package com.example.dto.restoran;

import java.util.Date;

import com.example.beans.restoran.StavkaJela;
import com.example.dto.korisnici.KuvarDTO;

public class StavkaJelaDTO {

	public int id;
	public JeloDTO jelo;
	public int kolicina;
	public KuvarDTO skuvao;
	public boolean spremljeno;
	public boolean rezervacija;
	public int verzija;
	
	public StavkaJelaDTO(){}
	
	public StavkaJelaDTO(StavkaJela j, Date datum){
		this.id = j.getId();
		this.verzija = j.getVersion();
		this.spremljeno = j.isSpremljeno();
		this.kolicina = j.getKolicina();
		if(j.getJelo() != null){
			this.jelo = new JeloDTO(j.getJelo(), datum);
		}
		if(j.getSkuvao() != null){
			this.skuvao = new KuvarDTO(j.getSkuvao());
		}
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public JeloDTO getJelo() {
		return jelo;
	}
	public void setJelo(JeloDTO jelo) {
		this.jelo = jelo;
	}
	public int getKolicina() {
		return kolicina;
	}
	public void setKolicina(int kolicina) {
		this.kolicina = kolicina;
	}
	public KuvarDTO getSkuvao() {
		return skuvao;
	}
	public void setSkuvao(KuvarDTO skuvao) {
		this.skuvao = skuvao;
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
